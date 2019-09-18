package duke.parsers;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.ExitCommand;
import duke.commands.FetchCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.commands.MarkDoneCommand;
import duke.commands.SnoozeCommand;
import duke.commands.ReminderCommand;
import duke.commons.DukeException;
import duke.commons.MessageUtil;

/**
 * Parser for duke.commands entered by the duke.Duke user. It reads from standard input and
 * returns Command objects.
 */
public class Parser {

    /**
     * Parses the userInput and return a Command object.
     *
     * @param userInput The userInput read by the user interface.
     * @return The corresponding Command object.
     * @throws DukeException If userInput is undefined.
     */
    public static Command parse(String userInput) throws DukeException {
        String commandWord = getCommandWord(userInput);
        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "todo":
            return new AddCommand(ParserUtil.createTodo(userInput));
        case "deadline":
            return new AddCommand(ParserUtil.createDeadline(userInput));
        case "event":
            return new AddCommand(ParserUtil.createEvent(userInput));
        case "within":
            return new AddCommand(ParserUtil.createWithin(userInput));
        case "list":
            return new ListCommand();
        case "done":
            return new MarkDoneCommand(ParserUtil.getIndex(userInput));
        case "delete":
            return new DeleteCommand(ParserUtil.getIndex(userInput));
        case "find":
            return new FindCommand(getWord(userInput));
        case "snooze":
            return new SnoozeCommand(ParserUtil.getIndexUpdate(userInput), ParserUtil.getDateUpdate(userInput));
        case "fetch":
            String[] deadlineDetails = userInput.split(" ", 2);
            return new FetchCommand(ParserTimeUtil.parseStringToDate(deadlineDetails[1].strip()));
        case "reminder":
            return new ReminderCommand();
        case "repeat":
            return new AddCommand(ParserUtil.createRecurringTask(userInput));
        default:
            throw new DukeException(MessageUtil.UNKNOWN_COMMAND);
        }

    }

    /**
     * Gets command word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The command word.
     */
    private static String getCommandWord(String userInput) {
        return userInput.strip().split(" ")[0];
    }

    /**
     * Gets word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The word.
     */
    private static String getWord(String userInput) throws DukeException {
        try {
            return userInput.strip().split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }
}
