package toki.command;

import toki.*;
import toki.task.*;

/**
 * Represents an executable user command.
 * <p>
 * Concrete subclasses implement {@code execute(...)} to perform actions against the
 * {@link toki.task.TaskList} and produce output via {@link toki.Ui}. Commands may throw
 * {@link TokiException} for user-correctable errors.
 */

public abstract class Command {

    /**
     * Executes this command.
     *
     * @param tasks   the task list to mutate/query
     * @param ui      the UI for showing messages
     * @param storage the storage used to persist changes when necessary
     * @throws TokiException if the command cannot be executed due to user error
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException;

    public boolean isExit() { return false; }
}
