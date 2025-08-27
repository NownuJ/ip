package toki;

import toki.task.*;
import toki.command.*;

/**
 * Entry point of the Toki application.
 * <p>
 * Wires together the {@link Storage}, {@link Ui}, {@link Parser}, and {@link TaskList},
 * loads previously saved tasks (if any), and runs the read&ndash;eval&ndash;print loop that
 * accepts user commands and executes them until exit.
 */

public class Toki {

    /** Deals with loading tasks from the file and saving tasks to the file. */
    private Storage storage;

    /** The in-memory task list containing all user tasks. */
    private TaskList tasks;

    /** Handles interactions with the user (input and output). */
    private Ui ui;

    /**
     * Constructs a new {@code Toki} application instance.
     * <p>
     * Loads tasks from the given file path (if available). If loading fails,
     * initializes an empty task list and shows a loading error via the {@link Ui}.
     *
     * @param filePath path to the data file used for persistence
     */
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

    /**
     * Runs the main loop of the application.
     * <p>
     * Continuously reads user commands, parses them into {@link Command} objects,
     * executes them against the {@link TaskList}, and persists changes.
     * Terminates when an exit command is issued.
     */
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

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Toki("data/toki.txt").run();
    }
}




