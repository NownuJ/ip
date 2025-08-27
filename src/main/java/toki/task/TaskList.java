package toki.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable collection of {@link Task} objects with convenience operations.
 * <p>
 * Provides add, remove, get, size, and listing utilities used by commands,
 * and acts as the in-memory model for the app session.
 */

public class TaskList {
    //contains the task list e.g., it has operations to add/delete tasks in the list
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Creates an empty {@code TaskList}.
     */
    public TaskList() {}

    /**
     * Creates a {@code TaskList} initialized with the given tasks.
     *
     * @param loaded list of tasks to populate this task list with
     */
    public TaskList(List<Task> loaded) {
        tasks.addAll(loaded);
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return the size of this task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param i zero-based index of the task
     * @return the task at the specified index
     */
    public Task get(int i) {
        return tasks.get(i);
    }

    /**
     * Adds a new task to the list.
     *
     * @param t the task to add
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Finds tasks with keyword on their description.
     *
     * @param keyword the keyword to find among the description
     */
    public List<Task> find(String keyword) {
        String needle = keyword.trim().toLowerCase();
        List<Task> out = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(needle)) {
                out.add(t);
            }
        }
        return out;
    }

    /**
     * Deletes and returns the task at the given one-based index.
     *
     * @param idx1 one-based index of the task (1 = first task)
     * @return the removed task
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task delete(int idx1) {
        return tasks.remove(idx1 - 1);
    }

    /**
     * Marks the task at the given one-based index as done.
     *
     * @param idx1 one-based index of the task
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void mark(int idx1) {
        tasks.get(idx1 - 1).markAsDone();
    }

    /**
     * Marks the task at the given one-based index as not done.
     *
     * @param idx1 one-based index of the task
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void unmark(int idx1) {
        tasks.get(idx1 - 1).markAsUndone();
    }

    /**
     * Returns a defensive copy of the tasks as a list.
     * Modifications to the returned list will not affect the task list.
     *
     * @return a new {@code List} containing all tasks
     */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
