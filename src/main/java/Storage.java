import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    //deals with loading tasks from the file and saving tasks in the file

    private final Path path;

    public Storage(String path) {
        this.path = Path.of(path);
    }

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

    private static Task fromLine(String line) {
        String[] p = line.split("\\s*\\|\\s*");
        // p[0]=type, p[1]=done, others=fields
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

    private static String toLine(Task t) {
        String done = t.isDone ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.description);
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, d.description, d.by.toString());
        } else { // Event
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.description, e.from.toString(), e.to.toString());
        }
    }
}