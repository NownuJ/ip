package toki;

import java.util.Scanner;

/**
 * Handles all user interaction.
 * <p>
 * Responsible for displaying messages, prompts, and errors to the user,
 * and reading input lines from the console.
 */

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

    /**
     * Shows the welcome message for the application.
     */
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

    /**
     * Shows that an error has occurred, with respective error message.
     *
     * @param s Error message that will be displayed
     */
    public void showError(String s) {
        show("Oh no! We detected an error.");
        show("Error Message: " + s);
    }

    public void showLoadingError() {
        System.out.println("(warn) Could not load saved data. Starting with an empty list.");
    }

}
