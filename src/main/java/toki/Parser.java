package toki;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import toki.command.Command;
import toki.command.DeadlineCommand;
import toki.command.DeleteCommand;
import toki.command.EventCommand;
import toki.command.ExitCommand;
import toki.command.FindCommand;
import toki.command.ListCommand;
import toki.command.MarkCommand;
import toki.command.TodoCommand;
import toki.command.UnmarkCommand;

/**
 * Parses raw user input into executable {@link toki.command.Command} objects.
 * <p>
 * Performs tokenization/validation of arguments and constructs the appropriate command
 * (e.g., {@code todo}, {@code deadline}, {@code event}, {@code list}, {@code mark}, etc.).
 * Throws {@link TokiException} on invalid syntax.
 */

public class Parser {

    private static LocalDate parseDate(String s) throws TokiException {
        try {
            return LocalDate.parse(s.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new TokiException("Use date format yyyy-MM-dd.");
        }
    }

    /**
     * Parses a raw input line into a {@link toki.command.Command}.
     *
     * @param full the raw user input
     * @return the command to execute
     * @throws TokiException if the input is invalid or incomplete
     */
    public static Command parse(String full) throws TokiException {
        String[] parts = full.trim().split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "todo":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: todo <desc>");
            }
            return new TodoCommand(arg);

        case "deadline":
            String[] d = arg.split("/by", 2);
            if (d.length < 2 || d[0].isBlank() || d[1].isBlank()) {
                throw new TokiException("Format of the Command is: deadline <desc> /by yyyy-MM-dd");
            }
            return new DeadlineCommand(d[0], parseDate(d[1]));

        case "event":
            String[] a = arg.split("/from", 2);
            String[] b = (a.length > 1) ? a[1].split("/to", 2) : new String[]{"", ""};
            if (a[0].isBlank() || b.length < 2 || b[0].isBlank() || b[1].isBlank()) {
                throw new TokiException("Format of the Command is: event <desc> /from yyyy-MM-dd /to yyyy-MM-dd");
            }
            return new EventCommand(a[0], parseDate(b[0]), parseDate(b[1]));


        case "mark":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: mark <index>");
            }
            int idxm = Integer.parseInt(arg.trim());
            if (idxm <= 0) {
                throw new TokiException("Index must be positive.");
            }
            return new MarkCommand(idxm);


        case "unmark":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: unmark <index>");
            }
            int idxu = Integer.parseInt(arg.trim());
            if (idxu <= 0) {
                throw new TokiException("Index must be positive.");
            }
            return new UnmarkCommand(idxu);


        case "delete":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: delete <index>");
            }
            int idxd = Integer.parseInt(arg.trim());
            if (idxd <= 0) {
                throw new TokiException("Index must be positive.");
            }
            return new DeleteCommand(idxd);

        case "find":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: find <text>");
            }
            return new FindCommand(arg);

        default: throw new TokiException("This is an unknown command.");
        }
    }
}

