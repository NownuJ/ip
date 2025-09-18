package toki.command;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;

/**
 * Deletes a task at the specified 1-based index from the current list.
 * <p>
 * Syntax: {@code delete INDEX}
 */

public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a {@code DeleteCommand} with index.
     *
     * @param index 1-based index of the list that will be deleted.
     */
    public DeleteCommand(int index) {
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
    @Override public String execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        Task deletedTask = tasks.get(index);
        tasks.delete(index);
        storage.save(tasks.asList());
        String response = "Okay, I've removed this task: \n"
                + "  " + deletedTask.toString() + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        return response;
    }
}
