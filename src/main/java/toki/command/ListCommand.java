package toki.command;

import toki.Storage;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;

/**
 * Displays all tasks in the current {@link toki.task.TaskList}.
 * <p>
 * Syntax: {@code list}
 */

public class ListCommand extends Command {

    /**
     * Executes this command.
     *
     * @param tasks   the task list to mutate/query
     * @param ui      the UI for showing messages
     * @param storage the storage used to persist changes when necessary
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        String response = "Here are the tasks in your list:\n";
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            response = response.concat((i + 1) + "." + t.toString() + "\n");
        }
        return response;
    }
}
