package toki.command;

import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.TaskList;

public class MarkCommand extends Command{
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        //operation
        tasks.mark(index);
        storage.save(tasks.asList());

        ui.show("Nice! I've marked this task as done:");
        ui.show("  " + tasks.get(index).toString());
    }
}
