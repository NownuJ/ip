import java.util.Objects;

public class UnmarkCommand extends Command{
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index - 1;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        //operation
        tasks.unmark(index);
        storage.save(tasks.asList());
        ui.show("Nice! I've marked this task as not done yet:");
        ui.show("  " + tasks.get(index).toString());
    }
}
