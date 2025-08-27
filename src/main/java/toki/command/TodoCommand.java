package toki.command;

import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.TaskList;
import toki.task.Todo;


/**
 * Adds a new {@link toki.task.Todo} to the list.
 * <p>
 * Syntax: {@code todo DESCRIPTION}
 */

public class TodoCommand extends Command{

    private final String desc;

    public TodoCommand(String desc) {
        this.desc = desc;
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
        //operation
        Todo todo = new Todo(desc);
        tasks.add(todo);
        storage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + todo.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
