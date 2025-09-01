package toki.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that occurs within a time window.
 * <p>
 * Example command: {@code event project meeting /from 2025-09-01 /to 2025-09-03}
 */

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates a {@code Event} with description, start date and end date of the event.
     *
     * @param description description of the task
     * @param from LocalDate that the event starts from
     * @param to LocalDate that the event ends on
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String fromDate = from.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        String toDate = to.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }

    public LocalDate getFrom() {
        return this.from;
    }

    public LocalDate getTo() {
        return this.to;
    }
}
