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

    /**
     * Returns the status icon of this task for display purposes.
     *
     * @return {@code "[X]"} if the task is done, or {@code "[ ]"} if not done
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of this task.
     * <p>
     * The string includes the status icon and the description,
     * but does not include any reminder or subclass-specific fields.
     *
     * @return formatted string for this task
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + this.description;
    }

    /**
     * Returns the description of this task.
     *
     * @return the description text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return {@code true} if this task is done, {@code false} otherwise
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Sets a reminder date for this task.
     *
     * @param reminderTime the date to remind for this task, not {@code null}
     */
    public void setReminderTime(LocalDate reminderTime) {
        this.reminderTime = reminderTime;
    }

    /**
     * Clears the reminder date for this task.
     * After calling this, {@link #getReminderTime()} will return {@code null}.
     */
    public void setReminderTimeAsEmpty() {
        this.reminderTime = null;
    }

    /**
     * Returns a string representation of the reminder time for this task.
     * <p>
     * If no reminder is set, returns the empty string.
     *
     * @return formatted reminder string, e.g. {@code " remind on 2025-10-01"},
     *         or {@code ""} if no reminder time is set
     */
    public String toStringReminderTime() {
        if (this.reminderTime == null) {
            return "";
        } else {
            return " remind on " + this.reminderTime.toString();
        }
    }

    /**
     * Returns the reminder date of this task, if set.
     *
     * @return the reminder date, or {@code null} if none
     */
    public LocalDate getReminderTime() {
        return reminderTime;
    }
}
