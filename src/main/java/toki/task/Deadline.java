package toki.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that must be completed by a specific date/time.
 * <p>
 * Example command: {@code deadline return book /by 2025-09-01}
 */

public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Creates a {@code Deadline} with description, and date due by.
     *
     * @param description description of the deadline
     * @param by LocalDate that the deadline is due by
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a {@code Deadline} with description, date due by, and time to be reminded on.
     *
     * @param description description of the deadline
     * @param by LocalDate that the deadline is due by
     * @param reminderTime LocalDate that the task will be reminded on
     */
    public Deadline(String description, LocalDate by, LocalDate reminderTime) {
        super(description, reminderTime);
        this.by = by;
    }

    /**
     * Returns the string representation of this {@code Deadline} task.
     *
     * @return a formatted string representation of this deadline task
     */
    @Override
    public String toString() {
        String date = by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[D]" + super.toString() + " (by: " + date + ")" + super.toStringReminderTime();
    }

    /**
     * Returns the by LocalDate of this {@code Deadline} task.
     *
     * @return LocalDate 'by' of this task.
     */
    public LocalDate getBy() {
        return this.by;
    }

}
