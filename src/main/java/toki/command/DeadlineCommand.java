package toki.command;

import java.time.LocalDate;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Deadline;
import toki.task.TaskList;

/**
 * Adds a new {@link toki.task.Deadline} to the list.
 * <p>
 * Syntax: {@code deadline DESCRIPTION /by DATE}
 */

public class DeadlineCommand extends Command {

    private final String desc;
    private final LocalDate by;

    /**
     * Creates a {@code DeadlineCommand} with description, and date due by.
     *
     * @param desc description of the deadline
     * @param by LocalDate that the deadline is due by
     */
    public DeadlineCommand(String desc, LocalDate by) {
        assert by != null : "Deadline date should not be null";
        this.desc = desc;
        this.by = by;
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
        Deadline deadline = new Deadline(desc, by);
        tasks.add(deadline);
        storage.save(tasks.asList());
        String response = "Got it. I've added this task:\n"
                    + "  " + deadline.toString() + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.";
        return response;
    }
}
