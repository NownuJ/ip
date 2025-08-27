package toki;

import java.util.Scanner;

public class Ui {

    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void show(String s) {
        System.out.println("    " + s);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        show("Hello! I'm toki.Toki");
        show("What can I do for you?");
        showLine();
    }

    public void showBye() {
        show("Bye. Hope to see you again soon!");
    }

    public void showMessage(String s) {
        show(s);
    }

    public void showMessages(String[] s) {
        showLine();
        for (int i = 0; i < s.length; i++) {
            show(s[i] + "\n");
        }
        showLine();
    }

    public void showError(String s) {
        show("Oh no! We detected an error.");
        show("Error Message: " + s);
    }

    public void showLoadingError() {
        System.out.println("(warn) Could not load saved data. Starting with an empty list.");
    }

}
