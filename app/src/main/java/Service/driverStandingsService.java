package Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class driverStandingsService {

    private final JSONArray standings;

    public driverStandingsService(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("MRData");

        this.standings = (((data.getJSONObject("StandingsTable")).getJSONArray("StandingsLists")).getJSONObject(0)).getJSONArray("DriverStandings");
    }


    public ArrayList<String> getDriverPoints(JSONObject response) throws JSONException {
        ArrayList<String> points = new ArrayList<String>();

        for( int i = 0 ; i<standings.length();i++){
            JSONObject driver = (JSONObject) standings.get(i);
            points.add(driver.get("points").toString());
        }
        return points;
    }

    public ArrayList<String> getDriverName(JSONObject response) throws JSONException {
        ArrayList<String> name = new ArrayList<String>();

        for( int i = 0 ; i<standings.length();i++){
            JSONObject driver = (JSONObject) standings.get(i);
            JSONObject driverInfo = driver.getJSONObject("Driver");
            name.add(driverInfo.get("givenName").toString() + " " + driverInfo.get("familyName").toString());
        }

        return name;
    }

    public ArrayList<String> getConstructor(JSONObject response) throws JSONException {
        ArrayList<String> constructor = new ArrayList<String>();

        for( int i = 0 ; i<standings.length();i++){
            JSONObject driver = (JSONObject) standings.get(i);
            JSONObject constructorInfo = (driver.getJSONArray("Constructors")).getJSONObject(0);
            constructor.add(constructorInfo.get("name").toString());
        }

        return constructor;
    }

}
