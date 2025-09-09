package toki.command;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.TaskList;


/**
 * Sets reminder date as null for the task at the specified 1-based index.
 * <p>
 * Syntax: {@code unremind INDEX}
 */
public class UnremindCommand extends Command {
    private final int index;

    /**
     * Creates unremind command.
     *
     * @param index   index of the task to be edited
     */
    public UnremindCommand(int index) {
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
        tasks.unremind(index);
        storage.save(tasks.asList());

        String response = "Nice! I've cleared the reminder date for this task as:\n"
                + "  " + tasks.get(index - 1).toString();
        return response;
    }
}
