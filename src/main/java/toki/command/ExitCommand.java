package toki.command;

import toki.Ui;
import toki.Storage;
import toki.task.TaskList;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
