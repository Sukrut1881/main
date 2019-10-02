package duke.commands;

import duke.api_req.LocationSearchUrlReq;
import duke.commons.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.api.ApiParser;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class representing a command to send the test URL connection.
 */
public class LocationSearchCommand extends Command {
    private String param;

    public LocationSearchCommand(String param) {
        this.param = param;
    }

    /**
     * Executes this command with given param.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws IOException, DukeException, JSONException {
        ArrayList<String> result = ApiParser.GetLocationSearch(param);

        ui.show("These are the coordinates of your search:");
        for (String output: result) {
            ui.show(output);
        }
    }
}
