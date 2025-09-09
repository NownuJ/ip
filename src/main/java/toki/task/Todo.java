package toki.task;

import java.time.LocalDate;

/**
 * A task with only a textual description and no date/time component.
 * <p>
 * Example command: {@code todo read book}
 */

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, LocalDate reminderTime) {
        super(description, reminderTime);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + super.toStringReminderTime();
    }
}
