package toki.command;

import java.util.List;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;

/**
 * Finds tasks whose descriptions contain a given keyword (case-insensitive)
 * and displays the matches.
 *
 * Syntax: {@code find KEYWORD}
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a {@code FindCommand} with keyword.
     *
     * @param keyword String that will be searched in the tasks' descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes this command.
     *
     * @param tasks   the task list to mutate/query
     * @param ui      the UI for showing messages
     * @param storage the storage used to persist changes when necessary
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        String response;
        List<Task> matches = tasks.find(keyword);
        ui.show("Here are the matching tasks in your list:");

        if (matches.isEmpty()) {
            response = "Here are the matching tasks in your list:\n"
                        + "There are no matching tasks in your list.";
        } else {
            response = "Here are the matching tasks in your list:\n";
            for (int i = 0; i < matches.size(); i++) {
                response = response.concat((i + 1) + "." + matches.get(i) + "\n");
            }
        }
        return response;
    }
}
