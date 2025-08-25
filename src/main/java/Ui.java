public class Ui {

    private static final String LINE = "____________________________________________________________";

    public void show(String s) { System.out.println("    " + s); }
    public void showLine() { System.out.println(LINE); }
    public void showWelcome() {
        showLine();
        show("Hello! I'm Toki");
        show("What can I do for you?");
        showLine();
    }
    public void showBye() {
        showLine();
        show("Bye. Hope to see you again soon!");
        showLine();
    }
    public void showMessage(String s) {
        showLine();
        show(s);
        showLine();
    }
    public void showMessages(String[] s) {
        showLine();
        for (int i = 0; i < s.length; i++) {
            show(s[i] + "\n");
        }
        showLine();
    }

}
