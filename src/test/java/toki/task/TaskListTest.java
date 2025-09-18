package toki.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskListTest {

    @Test
    @DisplayName("new TaskList(): starts empty")
    void constructor_empty_startsEmpty() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("TaskList(List): copies input list (not backed by it)")
    void constructor_withList_copiesNotBacked() {
        List<Task> src = new ArrayList<>();
        src.add(new Todo("A"));

        TaskList list = new TaskList(src);
        assertEquals(1, list.size());
        assertEquals("A", list.get(0).getDescription());
        src.add(new Todo("B"));
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("add() + get(0-based): stores and preserves order")
    void addAndGet_preservesOrder_correctOrderPreserved() {
        TaskList list = new TaskList();
        Todo a = new Todo("A");
        Todo b = new Todo("B");

        list.add(a);
        list.add(b);

        assertEquals(2, list.size());
        assertSame(a, list.get(0));
        assertSame(b, list.get(1));
    }

    @Test
    @DisplayName("delete(1-based): removes correct task and shifts left")
    void delete_oneBased_removesAndShifts() {
        TaskList list = new TaskList(Arrays.asList(new Todo("A"), new Todo("B"), new Todo("C")));

        Task removed = list.delete(2);
        assertEquals("B", removed.getDescription());
        assertEquals(2, list.size());
        assertEquals("A", list.get(0).getDescription());
        assertEquals("C", list.get(1).getDescription());
    }

    @Test
    @DisplayName("delete(1-based): invalid indices throw IndexOutOfBoundsException")
    void delete_invalidIndices_throw() {
        TaskList list = new TaskList(Arrays.asList(new Todo("A")));
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.delete(-3));
    }

    @Test
    @DisplayName("mark/unmark (1-based): toggle done flag")
    void mark_unmark_toggleDone() {
        TaskList list = new TaskList();
        list.add(new Todo("A"));

        list.mark(1);
        assertTrue(list.get(0).getIsDone());

        list.unmark(1);
        assertFalse(list.get(0).getIsDone());
    }

    @Test
    @DisplayName("mark/unmark (1-based): invalid indices throw IndexOutOfBoundsException")
    void markUnmark_invalidIndices_throw() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.mark(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.unmark(1));
        list.add(new Todo("only"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.mark(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.unmark(2));
    }

    @Test
    @DisplayName("find(): case-insensitive substring; empty keyword returns all")
    void find_caseInsensitive_andEmptyReturnsAll() {
        TaskList list = new TaskList(Arrays.asList(
                new Todo("read book"),
                new Todo("Write Report"),
                new Todo("play")
        ));

        List<Task> hits = list.find("report");
        assertEquals(1, hits.size());
        assertTrue(hits.get(0).getDescription().toLowerCase().contains("report"));
        List<Task> all = list.find("");
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("remind/unremind (1-based): set and clear reminder date")
    void remind_unremind_setAndClear() {
        TaskList list = new TaskList();
        Todo t = new Todo("A");
        list.add(t);

        LocalDate d = LocalDate.of(2025, 10, 1);
        list.remind(1, d);
        assertEquals(d, list.get(0).getReminderTime());

        list.unremind(1);
        assertEquals(null, list.get(0).getReminderTime());
    }

    @Test
    @DisplayName("asList(): returns defensive copy (not same list instance)")
    void asList_returnsDefensiveCopy() {
        TaskList list = new TaskList();
        Todo t = new Todo("A");
        list.add(t);

        List<Task> copy = list.asList();
        assertNotSame(copy, list.asList(), "Should return a fresh copy each call");
        assertEquals(1, copy.size());
        assertSame(t, copy.get(0));
        copy.clear();
        assertEquals(1, list.size());
    }
}

