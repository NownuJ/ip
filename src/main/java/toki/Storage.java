package toki;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import toki.task.Deadline;
import toki.task.Event;
import toki.task.Task;
import toki.task.TaskList;
import toki.task.Todo;

/**
 * Handles persistence of tasks to and from disk.
 * <p>
 * Reads tasks from the save file on startup and writes the current {@link TaskList}
 * after mutating commands. The storage format is a simple line-based representation.
 */

public class Storage {
    //deals with loading tasks from the file and saving tasks in the file

    private final Path path;

    public Storage(String path) {
        this.path = Path.of(path);
    }

    /**
     * Saves the given tasks to the backing file.
     *
     * @param tasks the tasks to persist
     * @throws TokiException if writing to disk fails
     */
    public void save(List<Task> tasks) throws TokiException {
        try {
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(toLine(t));
            }
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TokiException("Could not save tasks to file: " + path);
        }
    }

    /**
     * Loads tasks from the backing file.
     *
     * @return a {@code List<Task>} containing all persisted tasks
     * @throws TokiException if the file is missing or malformed
     */
    public List<Task> load() throws TokiException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task t = fromLine(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new TokiException("Could not load tasks from file: " + path);
        }
    }

    /**
     * Converts a single line of text from the save file into a {@link Task} object.
     * <p>
     * The line is expected to be in the format:
     * <ul>
     *   <li>{@code T | done | description}</li>
     *   <li>{@code D | done | description | yyyy-MM-dd}</li>
     *   <li>{@code E | done | description | yyyy-MM-dd | yyyy-MM-dd}</li>
     * </ul>
     * where {@code done} is {@code "1"} for completed tasks or {@code "0"} otherwise.
     *
     * @param line the raw line from the data file
     * @return the corresponding {@link Task}, or {@code null} if the type tag is unrecognised
     */
    private static Task fromLine(String line) {
        String[] p = line.split("\\s*\\|\\s*");
        Task t;
        switch (p[0]) {
        case "T":
            t = new Todo(p[2]);
            break;
        case "D":
            t = new Deadline(p[2], LocalDate.parse(p[3]));
            break;
        case "E":
            t = new Event(p[2], LocalDate.parse(p[3]), LocalDate.parse(p[4]));
            break;
        default:
            return null;
        }
        if ("1".equals(p[1])) {
            t.markAsDone();
        }
        return t;
    }

    /**
     * Serializes a {@link Task} object into its line representation for storage.
     * <p>
     * The returned string uses the same pipe-delimited format expected by
     * {@link #fromLine(String)}:
     * <ul>
     *   <li>{@code T | done | description}</li>
     *   <li>{@code D | done | description | yyyy-MM-dd}</li>
     *   <li>{@code E | done | description | yyyy-MM-dd | yyyy-MM-dd}</li>
     * </ul>
     * where {@code done} is {@code "1"} if the task is completed or {@code "0"} otherwise.
     *
     * @param t the task to convert
     * @return the string representation to be written into the data file
     */
    private static String toLine(Task t) {
        String done = t.getIsDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, d.getDescription(), d.getBy().toString());
        } else { // toki.task.Event
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.getDescription(), e.getFrom().toString(), e.getTo().toString());
        }
    }
}
