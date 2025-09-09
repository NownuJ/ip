package toki.command;

import toki.Storage;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;

/**
 * Finds all tasks with valid reminderTime and displays them in a list.
 *
 * Syntax: {@code reminders}
 */
public class RemindersCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        String response = "Here are the tasks in your list with reminders:\n";
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getReminderTime() != null) {
                response = response.concat((i + 1) + "." + t.toString() + "\n");
            }
        }
        return response;
    }
}
