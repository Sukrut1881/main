package duke.logic.parsers.commandParsers;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteGenerateCommand;

/**
 * Parses the user inputs into suitable format for RouteGenerateCommand.
 */
public class RouteGenerateParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * Constructs the RouteGenerateParser.
     */
    public RouteGenerateParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteGenerateCommand object.
     * @return RouteGenerateCommand object.
     * @throws ParseException If RouteGenerateCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        String[] details = input.split(" to | by ", THREE);
        if (details.length == THREE) {
            try {
                return new RouteGenerateCommand(details[ZERO], details[ONE], Constraint.valueOf(details[TWO].toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ParseException(Messages.ERROR_CONSTRAINT_UNKNOWN);
            }
        }
        throw new ParseException(Messages.ERROR_FIELDS_EMPTY);
    }
}
