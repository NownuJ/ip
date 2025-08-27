package toki.command;

import toki.TokiException;
import toki.Ui;
import toki.Storage;
import toki.task.TaskList;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException;

    public boolean isExit() {
        return false;
    }
}
