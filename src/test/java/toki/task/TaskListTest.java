package toki.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void add_increasesSize() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    void get_taskAdded_returnsSameObject() {
        TaskList tasks = new TaskList();
        Todo t1 = new Todo("read book");
        tasks.add(t1);
        assertSame(t1, tasks.get(0));
    }

    @Test
    void delete_taskDeleted_removesAndReturnsItem() {
        TaskList tasks = new TaskList();
        Todo t1 = new Todo("a");
        Todo t2 = new Todo("b");
        tasks.add(t1);
        tasks.add(t2);
        Task removed = tasks.delete(1);
        assertSame(t1, removed);
        assertEquals(1, tasks.size());
        assertSame(t2, tasks.get(0));
    }

    @Test
    void mark_usesOneBasedIndex_setsDone() {
        TaskList tasks = new TaskList();
        Todo t = new Todo("task");
        tasks.add(t);
        tasks.mark(1);
        assertTrue(t.isDone);
    }

    @Test
    void unmark_usesOneBasedIndex_clearsDone() {
        TaskList tasks = new TaskList();
        Todo t = new Todo("task");
        t.markAsDone();
        tasks.add(t);
        tasks.unmark(1);
        assertFalse(t.isDone);
    }

    @Test
    void asList_returnsDefensiveCopy() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("a"));
        List<Task> copy = tasks.asList();
        copy.add(new Todo("b"));
        assertEquals(1, tasks.size());
    }

    @Test
    void constructor_withLoadedList_copiesElements() {
        Todo t1 = new Todo("a");
        List<Task> loaded = List.of(t1);
        TaskList tasks = new TaskList(loaded);
        assertEquals(1, tasks.size());
        assertSame(t1, tasks.get(0));
    }
}
