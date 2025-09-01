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

    @Override
    public String toString() {
        String date = by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[D]" + super.toString() + " (by: " + date + ")";
    }

    public LocalDate getBy() {
        return this.by;
    }

}
