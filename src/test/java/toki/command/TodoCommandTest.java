package toki.command;

import toki.*;
import toki.task.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoCommandTest {

    static class FakeUi extends Ui {
        final List<String> lines = new ArrayList<>();
        @Override
        public void show(String s) { lines.add(s); }
    }

    static class FakeStorage extends Storage {
        List<Task> lastSaved;

        public FakeStorage() { super("build/test-tmp/todo.txt"); }

        // ---- IMPORTANT ----
        // Your TodoCommand currently calls: storage.save(tasks.asList())
        // so Storage must have a `save(List<Task>)`. If yours is `save(TaskList)`,
        // change the override accordingly.
        @Override
        public void save(List<Task> list) {
            this.lastSaved = new ArrayList<>(list);
        }
    }

    @Test
    void execute_addsTask_savesSnapshot_andPrintsUi() throws Exception {
        TaskList tasks = new TaskList();
        FakeUi ui = new FakeUi();
        FakeStorage storage = new FakeStorage();

        TodoCommand cmd = new TodoCommand("read book");

        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size(), "size should be 1 after executing TodoCommand");
        assertTrue(tasks.get(0) instanceof Todo, "first task should be a Todo");
        assertTrue(tasks.get(0).toString().contains("read book"));

        // Assert: storage.save(...) was called with a snapshot reflecting the new item
        assertNotNull(storage.lastSaved, "storage.save should have been called");
        assertEquals(1, storage.lastSaved.size(), "saved list should have one task");
        assertTrue(storage.lastSaved.get(0) instanceof Todo);
        assertTrue(storage.lastSaved.get(0).toString().contains("read book"));

        // Assert: UI printed the three expected lines (allowing for your formatting)
        assertEquals("Got it. I've added this task:", ui.lines.get(0));
        assertTrue(ui.lines.get(1).contains("read book")); // e.g. "  [T][ ] read book"
        assertTrue(ui.lines.get(2).contains("Now you have 1 tasks"), "count line should reflect size");
    }

}
