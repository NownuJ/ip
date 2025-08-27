package toki.task;

/**
 * A task with only a textual description and no date/time component.
 * <p>
 * Example command: {@code todo read book}
 */

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
