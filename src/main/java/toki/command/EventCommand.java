package toki.command;

import java.time.LocalDate;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Event;
import toki.task.TaskList;

/**
 * Adds a new {@link toki.task.Event} to the list.
 * <p>
 * Syntax: {@code event DESCRIPTION /from DATE /to DATE}
 */

public class EventCommand extends Command {

    private final String desc;
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates a {@code EventCommand} with description, start date and end date of the event.
     *
     * @param desc description of the task
     * @param from LocalDate that the event starts from
     * @param to LocalDate that the event ends on
     */
    public EventCommand(String desc, LocalDate from, LocalDate to) {
        this.desc = desc;
        this.from = from;
        this.to = to;
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
        Event event = new Event(desc, from, to);
        tasks.add(event);
        storage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + event.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
