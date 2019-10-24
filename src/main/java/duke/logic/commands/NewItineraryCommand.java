package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Class representing a command to create a new itinerary from scratch.
 */
public class NewItineraryCommand extends Command {

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        return new CommandResultText("New Itinerary Created");
    }
}
