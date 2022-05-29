package Service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class constructorStandingsService {

    private final JSONArray standings;

    public constructorStandingsService(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("MRData");

        this.standings = (((data.getJSONObject("StandingsTable")).getJSONArray("StandingsLists")).getJSONObject(0)).getJSONArray("ConstructorStandings");
    }


    public ArrayList<String> getConstructorPoints(JSONObject response) throws JSONException {
        ArrayList<String> points = new ArrayList<String>();

        for( int i = 0 ; i<standings.length();i++){
            JSONObject constructor = (JSONObject) standings.get(i);
            points.add(constructor.get("points").toString());
        }
        Log.d("Constructor service", points.toString());
        return points;
    }

    public ArrayList<String> getConstructorName(JSONObject response) throws JSONException {
        ArrayList<String> name = new ArrayList<String>();

        for( int i = 0 ; i<standings.length();i++){
            JSONObject constructor = (JSONObject) standings.get(i);
            JSONObject constructorInfo = constructor.getJSONObject("Constructor");
            name.add((String) constructorInfo.get("name"));
        }
        Log.d("Constructor service", name.toString());
        return name;
    }

}
