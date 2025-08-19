import java.util.Scanner;

public class Toki {
    private static final String LINE =
            "   ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("    Hello! I'm Toki");
        System.out.println("    What can I do for you?");
        System.out.println(LINE);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {

                String input  = sc.nextLine();

                if (input.equals("bye")) {
                    System.out.println(LINE);
                    System.out.println("    Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                }

                System.out.println(LINE);
                System.out.println("    " + input);
                System.out.println(LINE);
            }
        }
    }


}
