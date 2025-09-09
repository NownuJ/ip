package toki.command;

import java.time.LocalDate;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.TaskList;

/**
 * Sets reminder date for the task at the specified 1-based index.
 * <p>
 * Syntax: {@code remind INDEX at REMINDERDATE}
 */
public class RemindCommand extends Command {
    private final int index;
    private final LocalDate reminderDate;

    /**
     * Creates remind command.
     *
     * @param index   index of the task to be edited
     * @param reminderDate      LocalDate to be set as the new reminderDate
     */
    public RemindCommand(int index, LocalDate reminderDate) {
        this.index = index;
        this.reminderDate = reminderDate;
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
        tasks.remind(index, reminderDate);
        storage.save(tasks.asList());

        String response = "Nice! I've set reminder date for this task as:\n"
                + "  " + tasks.get(index - 1).toString();
        return response;
    }
}
