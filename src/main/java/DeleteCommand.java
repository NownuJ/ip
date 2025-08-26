public class DeleteCommand extends Command{
    private final int index;

    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        Task deletedTask = tasks.get(index);
        tasks.delete(index);
        storage.save(tasks.asList());

        ui.show("Okay, I've removed this task:");
        ui.show("  " + deletedTask.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
