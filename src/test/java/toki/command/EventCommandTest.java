package toki.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import toki.Storage;
import toki.TokiException;
import toki.Ui;
import toki.task.Task;
import toki.task.TaskList;

class EventCommandTest {

    /**
     * Fake UI that collects any shown lines
     * */
    static class FakeUi extends Ui {
        final List<String> lines = new ArrayList<>();
        @Override public void show(String s) { lines.add(s); }
    }

    /**
     * Fake Storage that captures the last saved snapshot.
     * */
    static class FakeStorage extends Storage {
        private List<Task> lastSaved;
        FakeStorage() { super("build/test-tmp/event.txt"); }
        @Override public void save(List<Task> list) { this.lastSaved = new ArrayList<>(list); }
    }

    @Test
    @DisplayName("execute(): happy path adds Event, saves snapshot, returns response")
    void execute_happyPath_addsSavesReturnsResponse() throws Exception {
        TaskList tasks = new TaskList();
        FakeUi ui = new FakeUi();
        FakeStorage storage = new FakeStorage();

        LocalDate from = LocalDate.of(2025, 10, 1);
        LocalDate to = LocalDate.of(2025, 10, 2);

        EventCommand cmd = new EventCommand("project demo", from, to);
        String response = cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size(), "Event should be added");
        assertNotNull(storage.lastSaved, "Storage.save should be called");
        assertEquals(1, storage.lastSaved.size(), "Snapshot should include the new task");
        assertTrue(response.startsWith("Got it. I've added this task:"), "Should acknowledge add");
        assertTrue(response.contains("Now you have 1 tasks"), "Should report count");
    }

    @Test
    @DisplayName("execute(): allows same-day event (from == to)")
    void execute_sameDay_ok() throws Exception {
        TaskList tasks = new TaskList();
        FakeUi ui = new FakeUi();
        FakeStorage storage = new FakeStorage();

        LocalDate d = LocalDate.of(2025, 10, 1);
        EventCommand cmd = new EventCommand("one-day", d, d);

        String response = cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertNotNull(response);
    }

    @Test
    @DisplayName("execute(): blank description rejected with TokiException")
    void execute_blankDescription_rejected() {
        TaskList tasks = new TaskList();
        FakeUi ui = new FakeUi();
        FakeStorage storage = new FakeStorage();

        LocalDate from = LocalDate.of(2025, 10, 2);
        LocalDate to = LocalDate.of(2025, 10, 3);

        EventCommand cmd = new EventCommand("   ", from, to);
        assertThrows(TokiException.class, () -> cmd.execute(tasks, ui, storage));
        assertEquals(0, tasks.size());
        assertNull(storage.lastSaved, "Should not save on failure");
    }

    @Test
    @DisplayName("execute(): from > to rejected with TokiException")
    void execute_fromAfterTo_rejected() {
        TaskList tasks = new TaskList();
        FakeUi ui = new FakeUi();
        FakeStorage storage = new FakeStorage();

        LocalDate from = LocalDate.of(2025, 10, 5);
        LocalDate to = LocalDate.of(2025, 10, 1);

        EventCommand cmd = new EventCommand("bad range", from, to);
        assertThrows(TokiException.class, () -> cmd.execute(tasks, ui, storage));
        assertEquals(0, tasks.size());
        assertNull(storage.lastSaved);
    }
}

