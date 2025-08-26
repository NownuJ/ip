import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalDate;



public class Toki {

    private Storage storage; //deals with loading tasks from the file and saving tasks in the file
    private TaskList tasks; //contains the task list e.g., it has operations to add/delete tasks in the list
    private Ui ui; //deals with interactions with the user

    //will move to parser later
    private static final String UNMARKED = "[ ]";
    private static final String MARKED = "[X]";

    // --- parseDateStrict for keeping track of errors when inputting dates

    private static LocalDate parseDateStrict(String s) {
        return LocalDate.parse(s.trim(), DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
    }

    // ----------------------------------------------


    public Toki(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (TokiException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

    }


    private void run() {
        ui.showWelcome();

        while (true) {
            try {
                String input  = ui.readCommand();

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
                        for (int i =0; i < tasks.size(); i++) {
                            Task t = tasks.get(i);
                            ui.show((i+1) + "." + t.toString());
                        }
                        ui.showLine();
                        break;
                    case "mark":
                        if (arg == "") {
                            ui.showMessage("Oh no! You need to specify which task to mark.");
                            break;
                        } else if (Integer.parseInt(arg) > tasks.size() || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }


                        int markInt = Integer.parseInt(arg) - 1;
                        tasks.mark(markInt);
                        storage.save(tasks.asList());
                        ui.showLine();
                        ui.show("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(markInt).toString());
                        ui.showLine();
                        break;
                    case "unmark":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! You need to specify which task to unmark.");
                            break;
                        } else if (Integer.parseInt(arg) > tasks.size() || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }


                        int unmarkInt = Integer.parseInt(arg) - 1;
                        tasks.unmark(unmarkInt);
                        storage.save(tasks.asList());
                        ui.showLine();
                        ui.show("Nice! I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(unmarkInt).toString());
                        ui.showLine();
                        break;
                    case "todo":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! The description of a todo cannot be empty.");
                            break;
                        }

                        Todo todo = new Todo(arg);
                        tasks.add(todo);
                        storage.save(tasks.asList());

                        ui.showLine();
                        ui.show("Got it. I've added this task:");
                        ui.show("  " + todo.toString());
                        ui.show("Now you have " + tasks.size() + " tasks in the list.");
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
                            Deadline deadline = new Deadline(desc, by);
                            tasks.add(deadline);
                            storage.save(tasks.asList());

                            ui.showLine();
                            ui.show("Got it. I've added this task:");
                            ui.show("  " + deadline.toString());
                            ui.show("Now you have " + tasks.size() + " tasks in the list.");
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
                            LocalDate fromEvent = parseDateStrict(fromRaw);
                            LocalDate toEvent   = parseDateStrict(toRaw);
                            if (toEvent.isBefore(fromEvent)) {
                                ui.showMessage("Oh no! 'to' must not be before 'from'.");
                                break;
                            }
                            Event event = new Event(descEvent, fromEvent, toEvent);
                            tasks.add(event);
                            storage.save(tasks.asList());
                            ui.showLine();
                            ui.show("Got it. I've added this task:");
                            ui.show("  " + event.toString());
                            ui.show("Now you have " + tasks.size() + " tasks in the list.");
                            ui.showLine();
                        } catch (DateTimeParseException e) {
                            ui.showMessage("Oh no! Use date format yyyy-MM-dd (e.g., 2025-09-01).");
                        }
                        break;
                    case "delete":
                        if (Objects.equals(arg, "")) {
                            ui.showMessage("Oh no! You need to specify which task to delete.");
                            break;
                        } else if (Integer.parseInt(arg) > tasks.size() || Integer.parseInt(arg) < 1) {
                            ui.showMessage("Oh no! That is an invalid task index.");
                            break;
                        }

                        int deleteInt = Integer.parseInt(arg) - 1;
                        Task deletedTask = tasks.get(deleteInt);
                        tasks.delete(deleteInt);
                        storage.save(tasks.asList());

                        ui.showLine();
                        ui.show("Okay, I've removed this task:");
                        ui.show("  " + deletedTask.toString());
                        ui.show("Now you have " + tasks.size() + " tasks in the list.");
                        ui.showLine();
                        break;
                    default:
                        ui.showMessage("Oh no! This is an invalid input.");
                        break;
                }
            } catch (TokiException e) {
                ui.showError(e.getMessage());
            } finally {

            }


        }

    }

    public static void main(String[] args) {
        new Toki("data/toki.txt").run();
    }

}




