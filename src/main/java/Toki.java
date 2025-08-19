import java.util.Scanner;

public class Toki {
    private static final String LINE =
            "   ____________________________________________________________";


    public static void main(String[] args) {
        String[] list = new String[100];
        int index = 0;

        System.out.println(LINE);
        System.out.println("     Hello! I'm Toki");
        System.out.println("     What can I do for you?");
        System.out.println(LINE);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {

                String input  = sc.nextLine();

                if (input.equals("bye")) {
                    System.out.println(LINE);
                    System.out.println("     Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(LINE);
                    for (int i =0; i < index; i++) {
                        System.out.println("     "+ (i+1) + ". " + list[i]);
                    }
                    System.out.println(LINE);


                } else {
                    list[index] = input;
                    index++;

                    System.out.println(LINE);
                    System.out.println("    added: " + input);
                    System.out.println(LINE);
                }


            }
        }
    }


}
