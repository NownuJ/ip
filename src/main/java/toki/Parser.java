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
import toki.command.RemindCommand;
import toki.command.RemindersCommand;
import toki.command.TodoCommand;
import toki.command.UnmarkCommand;
import toki.command.UnremindCommand;

/**
 * Parses raw user input into executable {@link toki.command.Command} objects.
 * <p>
 * Performs tokenization/validation of arguments and constructs the appropriate command
 * (e.g., {@code todo}, {@code deadline}, {@code event}, {@code list}, {@code mark}, etc.).
 * Throws {@link TokiException} on invalid syntax.
 */

public class Parser {

    /**
     * Parses a raw date string into a {@link LocalDate}.
     * <p>
     * Expects input in ISO-8601 format ({@code yyyy-MM-dd}).
     *
     * @param s the raw string containing a date
     * @return the parsed {@link LocalDate}
     * @throws TokiException if the input cannot be parsed as a valid date
     */
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

            boolean isDeadlineArgIncomplete = d.length < 2;
            if (isDeadlineArgIncomplete) {
                throw new TokiException("Format of the Command is: deadline <desc> /by yyyy-MM-dd");
            }
            boolean isDeadlineDescEmpty = d[0].isBlank();
            boolean isDeadlineByEmpty = d[1].isBlank();
            if (isDeadlineDescEmpty || isDeadlineByEmpty) {
                throw new TokiException("Format of the Command is: deadline <desc> /by yyyy-MM-dd");
            }
            return new DeadlineCommand(d[0], parseDate(d[1]));

        case "event":
            String[] a = arg.split("/from", 2);
            String[] b = (a.length > 1) ? a[1].split("/to", 2) : new String[]{"", ""};
            boolean isEventArgIncomplete = b.length < 2;
            if (isEventArgIncomplete) {
                throw new TokiException("Format of the Command is: event <desc> /from yyyy-MM-dd /to yyyy-MM-dd");
            }
            boolean isEventDescEmpty = a[0].isBlank();
            boolean isEventFromEmpty = b[0].isBlank();
            boolean isEventToEmpty = b[1].isBlank();
            if (isEventDescEmpty || isEventFromEmpty || isEventToEmpty) {
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

        case "remind":
            String[] r = arg.split(" at ", 2);
            boolean isRemindArgEmpty = arg.isBlank();
            if (r.length != 2) {
                throw new TokiException("Format of the Command is: remind <index> at yyyy-MM-dd");
            }
            boolean isRemindIdxEmpty = r[0].isBlank();
            boolean isRemindDateInvalid = !isValidDateTime(r[1]);
            if (isRemindArgEmpty || isRemindIdxEmpty || isRemindDateInvalid) {
                throw new TokiException("Format of the Command is: remind <index> at yyyy-MM-dd");
            }
            int idxr = Integer.parseInt(r[0].trim());
            return new RemindCommand(idxr, parseDate(r[1]));

        case "unremind":
            if (arg.isBlank()) {
                throw new TokiException("Format of the Command is: unremind <index>");
            }
            int idxur = Integer.parseInt(arg.trim());
            if (idxur <= 0) {
                throw new TokiException("Index must be positive.");
            }
            return new UnremindCommand(idxur);
        case "reminders":
            return new RemindersCommand();

        default: throw new TokiException("This is an unknown command.");
        }
    }

    /**
     * Checks whether the given input string can be parsed as a valid ISO date.
     *
     * @param input the string to validate
     * @return {@code true} if the input is a valid ISO-8601 date ({@code yyyy-MM-dd}),
     *         {@code false} otherwise
     */
    private static boolean isValidDateTime(String input) {
        try {
            LocalDate.parse(input.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

