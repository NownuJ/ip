package toki.command;

import toki.*;
import toki.task.*;

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
     * @throws TokiException if the command cannot be executed due to user error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
