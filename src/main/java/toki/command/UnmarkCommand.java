package toki.command;

import toki.*;
import toki.task.*;

/**
 * Marks the task at the specified 1-based index as not done.
 * <p>
 * Syntax: {@code unmark INDEX}
 */

public class UnmarkCommand extends Command{
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

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
        //operation
        tasks.unmark(index);
        storage.save(tasks.asList());
        ui.show("Nice! I've marked this task as not done yet:");
        ui.show("  " + tasks.get(index).toString());
    }
}
