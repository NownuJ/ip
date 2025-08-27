package toki;

import toki.command.Command;
import toki.task.TaskList;

public class Toki {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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




