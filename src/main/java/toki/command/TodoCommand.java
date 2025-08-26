package toki.command;

import toki.*;
import toki.task.*;

public class TodoCommand extends Command{

    private final String desc;

    public TodoCommand(String desc) {
        this.desc = desc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        //operation
        Todo todo = new Todo(desc);
        tasks.add(todo);
        storage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + todo.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
