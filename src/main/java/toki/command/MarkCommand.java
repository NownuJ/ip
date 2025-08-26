package toki.command;

import toki.*;
import toki.task.*;

public class MarkCommand extends Command{
    private final int index;

    public MarkCommand(int index) {
        this.index = index - 1;
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
