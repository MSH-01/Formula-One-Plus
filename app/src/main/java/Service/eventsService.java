package Service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class eventsService {

    private JSONObject nextEvent;

    private Date nextDate;

    private String nextRace;

    public eventsService(JSONObject events) throws JSONException, ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateLong = new Date();

        JSONArray eventsParsed = events.getJSONArray("events");

        for(int i=0;i<eventsParsed.length();i++){
            JSONObject currentEvent = (JSONObject) eventsParsed.get(i);
            Date date = formatter.parse(formatter.format(dateLong));
            Date eventDate = formatter.parse(currentEvent.getString("dateEvent"));
            if(date.before(eventDate)){
                nextEvent = (JSONObject) eventsParsed.get(i);
                Log.d("next event",nextEvent.toString());
                break;
            }

        }


        nextDate = formatter.parse(nextEvent.getString("dateEvent"));
        nextRace=nextEvent.getString("strEvent");

    }

    public String getNextDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy, HH:mm");

        return simpleDateFormat.format(nextDate);
    }

    public String getNextRace(){
        return nextRace;
    }
}
