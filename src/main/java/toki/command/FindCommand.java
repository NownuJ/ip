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

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {

        List<Task> matches = tasks.find(keyword);
        ui.show("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            ui.show((i + 1) + "." + matches.get(i));
        }
        if (matches.isEmpty()) {
            ui.show("There are no matching tasks in your list.");
        }
    }
}
