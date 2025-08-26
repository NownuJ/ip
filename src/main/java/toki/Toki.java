package toki;

import toki.task.*;
import toki.command.*;

public class Toki {

    private Storage storage; //deals with loading tasks from the file and saving tasks in the file
    private TaskList tasks; //contains the task list e.g., it has operations to add/delete tasks in the list
    private Ui ui; //deals with interactions with the user

    //will move to parser later
    private static final String UNMARKED = "[ ]";
    private static final String MARKED = "[X]";

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
        boolean isExit = false;
        while (!isExit) {
            try {
                String input  = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(input);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (TokiException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Toki("data/toki.txt").run();
    }
}




