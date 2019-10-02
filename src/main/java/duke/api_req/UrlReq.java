package duke.api_req;

import duke.commons.DukeException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Abstract class representing individual URL requests.
 */
public abstract class UrlReq {
    protected String url;
    protected String param;
    public UrlReq(String url, String param) {
    }

    /**
     * Executes and sends the given URL request.
     */
    public abstract JSONObject execute() throws DukeException, IOException, JSONException;
}
