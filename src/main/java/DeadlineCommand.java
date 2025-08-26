import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DeadlineCommand extends Command{

    private final String desc;
    private final LocalDate by;

    public DeadlineCommand(String desc, LocalDate by) {
        this.desc = desc;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TokiException {
        Deadline deadline = new Deadline(desc, by);
        tasks.add(deadline);
        storage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + deadline.toString());
        ui.show("Now you have " + tasks.size() + " tasks in the list.");
    }
}
