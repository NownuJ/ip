import java.util.ArrayList;
import java.util.List;

public class TaskList {
    //contains the task list e.g., it has operations to add/delete tasks in the list
    private final List<Task> tasks = new ArrayList<>();

    public TaskList() {}

    public TaskList(List<Task> loaded) {
        tasks.addAll(loaded);
    }

    public int size() {
        return tasks.size();
    }
    public Task get(int i) {
        return tasks.get(i);
    }
    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int idx1) {
        return tasks.remove(idx1 - 1);
    }
    public void mark(int idx1) {
        tasks.get(idx1 - 1).markAsDone();
    }
    public void unmark(int idx1) {
        tasks.get(idx1 - 1).markAsUndone();
    }

    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
