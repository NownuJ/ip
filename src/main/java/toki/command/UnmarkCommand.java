package toki.command;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.TaskList;

/**
 * Marks the task at the specified 1-based index as not done.
 * <p>
 * Syntax: {@code unmark INDEX}
 */

public class UnmarkCommand extends Command {
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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        //operation
        tasks.unmark(index);
        storage.save(tasks.asList());
        String response = "Nice! I've marked this task as not done yet:\n"
                    + "  " + tasks.get(index).toString();
        return response;
    }
}
