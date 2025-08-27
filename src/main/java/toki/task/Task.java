package toki.task;

/**
 * Abstract base type for a task.
 * <p>
 * Encapsulates common state such as description and completion status,
 * and provides APIs used by concrete task types ({@link Todo}, {@link Deadline}, {@link Event}).
 */

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
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

}
