package toki.command;

import toki.*;
import toki.task.*;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException;

    public boolean isExit() { return false; }
}
