package toki.task;

import java.time.LocalDate;

/**
 * Abstract base type for a task.
 * <p>
 * Encapsulates common state such as description and completion status,
 * and provides APIs used by concrete task types ({@link Todo}, {@link Deadline}, {@link Event}).
 */

public class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDate reminderTime = null;

    /**
     * Creates a {@code Task} with description.
     *
     * @param description description of the Task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a {@code Task} with description and reminderTime
     *
     * @param description description of the Task
     * @param reminderTime time for the task to be reminded on
     */
    public Task(String description, LocalDate reminderTime) {
        this.description = description;
        this.isDone = false;
        this.reminderTime = reminderTime;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setReminderTime(LocalDate reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setReminderTimeAsEmpty() {
        this.reminderTime = null;
    }

    public String toStringReminderTime() {
        if (this.reminderTime == null) {
            return "";
        } else {
            return " remind on " + this.reminderTime.toString();
        }
    }

    public LocalDate getReminderTime() {
        return reminderTime;
    }
}
