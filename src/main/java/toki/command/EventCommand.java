package toki.command;

import java.time.LocalDate;
import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.Event;
import toki.task.TaskList;

public class EventCommand extends Command{

    private final String desc;
    private final LocalDate from;
    private final LocalDate to;

    public EventCommand(String desc, LocalDate from, LocalDate to) {
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

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