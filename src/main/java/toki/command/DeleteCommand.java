package toki.command;

import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.Task;
import toki.task.TaskList;

public class DeleteCommand extends Command{
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        Task deletedTask = tasks.get(index);
        tasks.delete(index);
        storage.save(tasks.asList());

        ui.show("Okay, I've removed this task:");
        ui.show("  " + deletedTask.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
