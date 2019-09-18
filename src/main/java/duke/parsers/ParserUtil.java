package duke.parsers;

import duke.Duke;
import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Event;
import duke.tasks.RecurringTask;
import duke.tasks.Todo;

import java.time.LocalDateTime;

/**
 * Parser for utility functions.
 */
public class ParserUtil {
    /**
     * Parses the userInput and return a new to-do constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new to-do object.
     */
    protected static Todo createTodo(String userInput) throws DukeException {
        String description = userInput.substring("todo".length()).strip();
        if (description.isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        return new Todo(description);
    }

    /**
     * Parses the userInput and return a new deadline constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new deadline object.
     */
    protected static Deadline createDeadline(String userInput) throws DukeException {
        String[] deadlineDetails = userInput.substring("deadline".length()).strip().split("/by");
        if (deadlineDetails.length != 2 || deadlineDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (deadlineDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Deadline(deadlineDetails[0].strip(),
                    ParserTimeUtil.parseStringToDate(deadlineDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Deadline(deadlineDetails[0].strip(), deadlineDetails[1].strip());
        }
    }

    /**
     * Parses the userInput and return a new event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new event object.
     */
    protected static Event createEvent(String userInput) throws DukeException {
        String[] eventDetails = userInput.substring("event".length()).strip().split("/at");
        if (eventDetails.length != 2 || eventDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (eventDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Event(eventDetails[0].strip(), ParserTimeUtil.parseStringToDate(eventDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Event(eventDetails[0].strip(), eventDetails[1].strip());
        }
    }

    /**
     * Creates a recurring task from a user input.
     *
     * @param userInput The userInput read by the user interface.
     * @return The Recurring task
     */
    public static RecurringTask createRecurringTask(String userInput) throws DukeException {
        String[] eventDetails = userInput.substring("repeat".length()).strip().split("/at");
        String[] dateDetails = eventDetails[1].split("/every");
        if (dateDetails.length != 2 || dateDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (eventDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new RecurringTask(eventDetails[0].strip(), ParserTimeUtil.parseStringToDate(dateDetails[0].strip()),
                    Integer.parseInt(dateDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new RecurringTask(eventDetails[0].strip(), dateDetails[0].strip(),
                    Integer.parseInt(dateDetails[1].strip()));
        }


    }

    /**
     * Parses the userInput and return a new DoWithin constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new DoWithin object.
     */
    protected static DoWithin createWithin(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("within".length()).strip().split("between|and");
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new DoWithin(withinDetails[0].strip(), start, end);
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    protected static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }

    /**
     * Parses the userInput and return an index to snooze.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    static int getIndexUpdate(String userInput) throws DukeException {
        try {
            int index =  Integer.parseInt(userInput.strip().split(" ")[1]);
            return --index;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }

    /**
     * Parses the userInput and return an date to snooze/delay to.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    public static LocalDateTime getDateUpdate(String userInput) throws DukeException {
        try {
            return ParserTimeUtil.parseStringToDate(
                    userInput.substring("snooze ".length() + 2).strip().split("/to")[1].strip());
        } catch (DukeDateTimeParseException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }
}
