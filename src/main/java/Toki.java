import java.util.Scanner;

public class Toki {
    private static final String LINE =
            "   ____________________________________________________________";
    private static final String UNMARKED = "[ ]";
    private static final String MARKED = "[X]";

    public static void main(String[] args) {
        Task[] list = new Task[100];
        int index = 0;

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
                        for (int i =0; i < index; i++) {
                            Task t = list[i];
                            System.out.println("     "+ (i+1) + "." + t.toString());
                        }
                        System.out.println(LINE);
                        break;
                    case "mark":
                        int markInt = Integer.parseInt(arg);
                        Task markTask = list[markInt - 1];
                        markTask.markAsDone();
                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + markTask.toString());
                        System.out.println(LINE);
                        break;
                    case "unmark":
                        int unmarkInt = Integer.parseInt(arg);
                        Task unmarkTask = list[unmarkInt - 1];
                        unmarkTask.markAsUndone();
                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as not done yet:");
                        System.out.println("  " + unmarkTask.toString());
                        System.out.println(LINE);
                        break;
                    default:
                        Task task = new Task(input);
                        list[index] = task;
                        index++;

                        System.out.println(LINE);
                        System.out.println("    added: " + input);
                        System.out.println(LINE);
                        break;
                }

                /*if (input.equals("bye")) {
                    System.out.println(LINE);
                    System.out.println("     Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(LINE);
                    for (int i =0; i < index; i++) {
                        Task t = list[i];
                        System.out.println("     "+ (i+1) + "." + t.getStatusIcon() + " " + t.description);
                    }
                    System.out.println(LINE);
                } else {  // adding new task to the list
                    Task task = new Task(input);
                    list[index] = task;
                    index++;

                    System.out.println(LINE);
                    System.out.println("    added: " + input);
                    System.out.println(LINE);
                }*/


            }
        }
    }


}
