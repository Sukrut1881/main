package duke.api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import duke.api_req.LocationSearchUrlReq;
import duke.commons.DukeException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to handle all API requests.
 */
public class ApiParser {

    /**
     * Return names and coordinates of location search.
     * @param param The query
     * @return result The locations found
     */
    public static ArrayList<String> GetLocationSearch(String param) throws JSONException, IOException, DukeException {
        ArrayList<String> result = new ArrayList<>();

        LocationSearchUrlReq req = new LocationSearchUrlReq("https://developers.onemap.sg/commonapi/search?",
                param);
        JSONObject jsonRes = req.execute();

        JSONArray jsonResult = (JSONArray) jsonRes.get("results");

        for (int i = 0; i < (int) jsonRes.get("found") && i < 5; i++) {
            result.add(jsonResult.getJSONObject(i).get("BUILDING") + ", " + jsonResult.getJSONObject(i).get("LATITUDE")
                    + ", " + jsonResult.getJSONObject(i).get("LONGITUDE"));
        }

        return result;
    }
}
