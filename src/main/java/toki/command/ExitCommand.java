package toki.command;

import toki.Storage;
import toki.Ui;
import toki.task.TaskList;

/**
 * Exits the application gracefully.
 * <p>
 * Syntax: {@code bye}
 */

public class ExitCommand extends Command {

    /**
     * Executes this command.
     *
     * @param tasks   the task list to mutate/query
     * @param ui      the UI for showing messages
     * @param storage the storage used to persist changes when necessary
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
