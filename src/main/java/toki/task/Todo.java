package toki.task;

import java.time.LocalDate;

/**
 * A task with only a textual description and no date/time component.
 * <p>
 * Example command: {@code todo read book}
 */

public class Todo extends Task {

    /**
     * Creates a {@code Todo} task with the given description.
     * The task is initially marked as not done and has no reminder time.
     *
     * @param description textual description of the task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a {@code Todo} task with the given description and reminder time.
     * The task is initially marked as not done.
     *
     * @param description textual description of the task
     * @param reminderTime date on which this task should be reminded,
     *                     may be {@code null} if no reminder is set
     */
    public Todo(String description, LocalDate reminderTime) {
        super(description, reminderTime);
    }

    /**
     * Returns the string representation of this to-do task.
     *
     * @return formatted string representation of this to-do task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString() + super.toStringReminderTime();
    }
}
