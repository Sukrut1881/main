package duke.api_req;

import duke.commons.DukeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * URL request to OneMap API to get coordinates of location.
 */
public class LocationSearchUrlReq extends UrlReq {
    private String paramType = "searchVal";
    private String optionalVariables = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";

    public LocationSearchUrlReq(String url, String param) {
        super(url, param);
        this.url = url;
        this.param = param;
    }

    /**
     * Executes the URL request to OneMap API.
     * @return JSONObject The response from request
     */
    @Override
    public JSONObject execute() throws DukeException, IOException, JSONException {
        URL url = new URL(this.url + paramType + "=" + param + optionalVariables);
        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = in.readLine();
        in.close();

        return new JSONObject(inputLine);
    }
}
