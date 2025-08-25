import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;


public class Toki {



    private static final Path DATA = Paths.get("data", "toki.txt");
    private static final String UNMARKED = "[ ]";
    private static final String MARKED = "[X]";

    // --- Save / Load feature functions -------------

    private static void saveAll(Task[] list, int count) {
        try {
            Files.createDirectories(DATA.getParent());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                Task t = list[i];
                sb.append(toLine(t)).append('\n');
            }
            Files.write(DATA, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("(warn) could not save: " + e.getMessage());
        }
    }

    private static int loadAll(Task[] list) {
        int count = 0;
        if (!Files.exists(DATA)) return 0;
        try (BufferedReader br = Files.newBufferedReader(DATA, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Task t = fromLine(line);
                if (t != null) list[count++] = t;
            }
        } catch (IOException e) {
            System.out.println("(warn) could not load: " + e.getMessage());
        }
        return count;
    }

    private static Task fromLine(String line) {
        String[] p = line.split("\\s*\\|\\s*");
        // p[0]=type, p[1]=done, others=fields
        Task t = null;
        switch (p[0]) {
            case "T":
                t = new Todo(p[2]);
                break;
            case "D":
                t = new Deadline(p[2], LocalDate.parse(p[3]));
                break;
            case "E":
                t = new Event(p[2], LocalDate.parse(p[3]), LocalDate.parse(p[4]));
                break;
            default:
                return null;
        }
        if ("1".equals(p[1])) t.markAsDone();
        return t;
    }

    private static String toLine(Task t) {
        String done = t.isDone ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.description);
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, d.description, d.by.toString());
        } else { // Event
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.description, e.from.toString(), e.to.toString());
        }
    }

    // --- parseDateStrict for keeping track of errors when inputting dates

    private static LocalDate parseDateStrict(String s) {
        return LocalDate.parse(s.trim(), DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
    }

    // ----------------------------------------------

    private Storage storage; //deals with loading tasks from the file and saving tasks in the file
    private TaskList tasks; //contains the task list e.g., it has operations to add/delete tasks in the list
    private Ui ui; //deals with interactions with the user

    private void run() {
        Task[] tasks = new Task[100];
        int index = loadAll(tasks);


        ui.showWelcome();


        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String input  = sc.nextLine();


                String[] parts = input.split("\\s+", 2);
                String cmd = parts[0].toLowerCase();
                String arg = (parts.length > 1) ? parts[1]: "";


                switch (cmd) {
                    case "bye":
                        ui.showBye();
                        return;
                    case "list":
                        ui.showLine();
                        ui.show("Here are the tasks in your list:");
                        for (int i =0; i < index; i++) {
                            Task t = tasks[i];
                            ui.show((i+1) + "." + t.toString());
                        }
                        ui.showLine();
                        break;
                    case "mark":
                        if (arg == "") {
                            ui.showMessage("Oh no! You need to specify which task to mark.");
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }


                        int markInt = Integer.parseInt(arg);
                        Task markTask = tasks[markInt - 1];
                        markTask.markAsDone();
                        saveAll(tasks, index);
                        ui.showLine();
                        ui.show("Nice! I've marked this task as done:");
                        System.out.println("  " + markTask.toString());
                        ui.showLine();
                        break;
                    case "unmark":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! You need to specify which task to unmark.");
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }


                        int unmarkInt = Integer.parseInt(arg);
                        Task unmarkTask = tasks[unmarkInt - 1];
                        unmarkTask.markAsUndone();
                        saveAll(tasks, index);
                        ui.showLine();
                        ui.show("Nice! I've marked this task as not done yet:");
                        System.out.println("  " + unmarkTask.toString());
                        ui.showLine();
                        break;
                    case "todo":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! The description of a todo cannot be empty.");
                            break;
                        }


                        Todo todo = new Todo(arg);
                        tasks[index] = todo;
                        index++;
                        saveAll(tasks, index);


                        ui.showLine();
                        ui.show("Got it. I've added this task:");
                        ui.show("  " + todo.toString());
                        ui.show("Now you have " + index + " tasks in the list.");
                        ui.showLine();
                        break;
                    case "deadline":
                        String[] dParts = arg.split(" /by ", 2);


                        if (Objects.equals(dParts[0], "")) {
                            ui.showMessage("Oh no! The description of a deadline cannot be empty.");
                            break;
                        } else if (dParts.length != 2 || Objects.equals(dParts[1], "")) {
                            ui.showMessage("Oh no! The todo of a deadline cannot be empty.");
                            break;
                        }


                        String desc = dParts[0].trim();
                        String byRaw = dParts.length > 1 ? dParts[1].trim() : "";


                        try {
                            LocalDate by = parseDateStrict(byRaw);
                            tasks[index++] = new Deadline(desc, by);
                            saveAll(tasks, index);
                            ui.showLine();
                            ui.show("Got it. I've added this task:");
                            ui.show("  " + tasks[index - 1].toString());
                            ui.show("Now you have " + index + " tasks in the list.");
                            ui.showLine();
                        } catch (DateTimeParseException e) {
                            ui.showMessage("Oh no! Please use date format yyyy-MM-dd (e.g., 2025-11-15).");
                        }




                        break;
                    case "event":
                        String[] eParts1 = arg.split(" /from ", 2);


                        if (Objects.equals(eParts1[0], "")) {
                            ui.showMessage("Oh no! The description of a event cannot be empty.");
                            break;
                        } else if (eParts1.length != 2 || Objects.equals(eParts1[1], "")) {
                            ui.showMessage("Oh no! The from date of a deadline cannot be empty.");
                            break;
                        }
                        String[] eParts2 = eParts1[1].split(" /to ", 2);
                        if (eParts2.length != 2 || Objects.equals(eParts2[1], "")) {
                            ui.showMessage("Oh no! The to date of a event cannot be empty.");
                            break;
                        }


                        String descEvent = eParts1[0].trim();
                        String fromRaw = eParts2[0].trim();
                        String toRaw   = eParts2[1].trim();


                        try {
                            LocalDate fromEvent = parseDateStrict(fromRaw);  // ← same helper as Deadline
                            LocalDate toEvent   = parseDateStrict(toRaw);
                            if (toEvent.isBefore(fromEvent)) {
                                ui.showMessage("Oh no! 'to' must not be before 'from'.");
                                break;
                            }
                            tasks[index++] = new Event(descEvent, fromEvent, toEvent);
                            saveAll(tasks, index);
                            ui.showLine();
                            ui.show("Got it. I've added this task:");
                            ui.show("  " + tasks[index - 1]);
                            ui.show("Now you have " + index + " tasks in the list.");
                            ui.showLine();
                        } catch (DateTimeParseException e) {
                            ui.showMessage("Oh no! Use date format yyyy-MM-dd (e.g., 2025-09-01).");
                        }
                        break;
                    case "delete":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! You need to specify which task to delete.");
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }


                        int deleteInt = Integer.parseInt(arg);
                        Task deleteTask = tasks[deleteInt - 1];
                        for (int i = deleteInt; i < index; i++){
                            tasks[i - 1] = tasks[i];
                        }
                        index--;
                        saveAll(tasks, index);


                        ui.showLine();
                        ui.show("Okay, I've removed this task:");
                        ui.show("  " + deleteTask.toString());
                        ui.show("Now you have " + index + " tasks in the list.");
                        ui.showLine();
                        break;
                    default:
                        ui.showMessage("Oh no! This is an invalid input.");
                        break;
                }


            }
        }
    }

    public static void main(String[] args) {
        new Toki().run();
    }

}



}
