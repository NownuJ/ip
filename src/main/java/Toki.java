import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Toki {
    private static final String LINE =
            "   ____________________________________________________________";
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
                t = new Deadline(p[2], p[3]);
                break;
            case "E":
                t = new Event(p[2], p[3], p[4]);
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
            return String.join(" | ", "D", done, d.description, d.by);
        } else { // Event
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.description, e.from, e.to);
        }
    }

    // ----------------------------------------------

    public static void main(String[] args) {
        Task[] list = new Task[100];
        int index = loadAll(list);

        System.out.println(LINE);
        System.out.println("     Hello! I'm Toki");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String input  = sc.nextLine();

                String[] parts = input.split("\\s+", 2);
                String cmd = parts[0].toLowerCase();
                String arg = (parts.length > 1) ? parts[1]: "";

                switch (cmd) {
                    case "bye":
                        System.out.println(LINE);
                        System.out.println("     Bye. Hope to see you again soon!");
                        System.out.println(LINE);
                        return;
                    case "list":
                        System.out.println(LINE);
                        System.out.println("     Here are the tasks in your list:");
                        for (int i =0; i < index; i++) {
                            Task t = list[i];
                            System.out.println("     "+ (i+1) + "." + t.toString());
                        }
                        System.out.println(LINE);
                        break;
                    case "mark":
                        if (arg == "") {
                            System.out.println(LINE);
                            System.out.println("     Oh no! You need to specify which task to mark.");
                            System.out.println(LINE);
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! That is an invalid task index.");
                            System.out.println(LINE);
                            break;
                        }

                        int markInt = Integer.parseInt(arg);
                        Task markTask = list[markInt - 1];
                        markTask.markAsDone();
                        saveAll(list, index);
                        System.out.println(LINE);
                        System.out.println("     Nice! I've marked this task as done:");
                        System.out.println("  " + markTask.toString());
                        System.out.println(LINE);
                        break;
                    case "unmark":
                        if (Objects.equals(arg, "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! You need to specify which task to unmark.");
                            System.out.println(LINE);
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! That is an invalid task index.");
                            System.out.println(LINE);
                            break;
                        }

                        int unmarkInt = Integer.parseInt(arg);
                        Task unmarkTask = list[unmarkInt - 1];
                        unmarkTask.markAsUndone();
                        saveAll(list, index);
                        System.out.println(LINE);
                        System.out.println("     Nice! I've marked this task as not done yet:");
                        System.out.println("  " + unmarkTask.toString());
                        System.out.println(LINE);
                        break;
                    case "todo":
                        if (Objects.equals(arg, "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The description of a todo cannot be empty.");
                            System.out.println(LINE);
                            break;
                        }

                        Todo todo = new Todo(arg);
                        list[index] = todo;
                        index++;
                        saveAll(list, index);

                        System.out.println(LINE);
                        System.out.println("     Got it. I've added this task:");
                        System.out.println("       " + todo.toString());
                        System.out.println("     Now you have " + index + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    case "deadline":
                        String[] dParts = arg.split(" /by ", 2);

                        if (Objects.equals(dParts[0], "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The description of a deadline cannot be empty.");
                            System.out.println(LINE);
                            break;
                        } else if (dParts.length != 2 || Objects.equals(dParts[1], "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The todo of a deadline cannot be empty.");
                            System.out.println(LINE);
                            break;
                        }

                        list[index++] = new Deadline(dParts[0], dParts[1]);
                        saveAll(list, index);
                        System.out.println(LINE);
                        System.out.println("     Got it. I've added this task:");
                        System.out.println("       " + list[index - 1].toString());
                        System.out.println("     Now you have " + index + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    case "event":
                        String[] eParts1 = arg.split(" /from ", 2);

                        if (Objects.equals(eParts1[0], "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The description of a event cannot be empty.");
                            System.out.println(LINE);
                            break;
                        } else if (eParts1.length != 2 || Objects.equals(eParts1[1], "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The from date of a deadline cannot be empty.");
                            System.out.println(LINE);
                            break;
                        }
                        String[] eParts2 = eParts1[1].split(" /to ", 2);
                        if (eParts2.length != 2 || Objects.equals(eParts2[1], "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! The to date of a event cannot be empty.");
                            System.out.println(LINE);
                            break;
                        }

                        list[index++] = new Event(eParts1[0], eParts2[0], eParts2[1]);
                        saveAll(list, index);
                        System.out.println(LINE);
                        System.out.println("     Got it. I've added this task:");
                        System.out.println("       " + list[index - 1]);
                        System.out.println("     Now you have " + index + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    case "delete":
                        if (Objects.equals(arg, "")) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! You need to specify which task to delete.");
                            System.out.println(LINE);
                            break;
                        } else if (Integer.parseInt(arg) > index || Integer.parseInt(arg) < 1) {
                            System.out.println(LINE);
                            System.out.println("     Oh no! That is an invalid task index.");
                            System.out.println(LINE);
                            break;
                        }

                        int deleteInt = Integer.parseInt(arg);
                        Task deleteTask = list[deleteInt - 1];
                        for (int i = deleteInt; i < index; i++){
                            list[i - 1] = list[i];
                        }
                        index--;
                        saveAll(list, index);

                        System.out.println(LINE);
                        System.out.println("     Okay, I've removed this task:");
                        System.out.println("       " + deleteTask.toString());
                        System.out.println("     Now you have " + index + " tasks in the list.");
                        System.out.println(LINE);
                        break;
                    default:
                        System.out.println(LINE);
                        System.out.println("     Oh no! This is an invalid input.");
                        System.out.println(LINE);
                        break;
                }

            }
        }
    }


}
