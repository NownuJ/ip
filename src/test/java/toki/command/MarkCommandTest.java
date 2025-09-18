package toki.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;
import toki.task.Todo;

class MarkCommandTest {

    /**
     * Minimal UI stub
     * */
    static class NopUi extends Ui {}

    /**
     * Storage stub to capture last saved snapshot.
     * */
    static class FakeStorage extends Storage {
        private List<Task> lastSaved;
        FakeStorage() { super("build/test-tmp/mark.txt"); }
        @Override public void save(List<Task> list) { this.lastSaved = new ArrayList<>(list); }
    }

    @Test
    @DisplayName("execute(): valid 1-based index marks task and saves snapshot")
    void execute_validIndex_marksTaskAndSavesSnapshot() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("A"));
        NopUi ui = new NopUi();
        FakeStorage storage = new FakeStorage();

        MarkCommand cmd = new MarkCommand(1);
        String response = cmd.execute(tasks, ui, storage);

        assertTrue(tasks.get(0).getIsDone(), "Task at index 1 should be marked done");
        assertNotNull(storage.lastSaved, "Storage.save should be called");
        assertEquals(1, storage.lastSaved.size(), "Saved snapshot should include the task");
        assertNotNull(response, "Command should return a user message");
    }

    @Test
    @DisplayName("execute(): non-positive index rejected with TokiException")
    void execute_nonPositiveIndex_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("A"));
        NopUi ui = new NopUi();
        FakeStorage storage = new FakeStorage();

        assertThrows(TokiException.class, () -> new MarkCommand(0).execute(tasks, ui, storage));
        assertThrows(TokiException.class, () -> new MarkCommand(-1).execute(tasks, ui, storage));
    }

    @Test
    @DisplayName("execute(): out-of-range index rejected with TokiException")
    void execute_outOfRangeIndex_throwsException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("A"));
        NopUi ui = new NopUi();
        FakeStorage storage = new FakeStorage();

        assertThrows(TokiException.class, () -> new MarkCommand(2).execute(tasks, ui, storage));
    }
}

