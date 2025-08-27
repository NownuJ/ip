package toki.command;

import java.time.LocalDate;
import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.Deadline;
import toki.task.TaskList;

/**
 * Adds a new {@link toki.task.Deadline} to the list.
 * <p>
 * Syntax: {@code deadline DESCRIPTION /by DATE}
 */

public class DeadlineCommand extends Command{

    private final String desc;
    private final LocalDate by;

    public DeadlineCommand(String desc, LocalDate by) {
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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        Deadline deadline = new Deadline(desc, by);
        tasks.add(deadline);
        storage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + deadline.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
