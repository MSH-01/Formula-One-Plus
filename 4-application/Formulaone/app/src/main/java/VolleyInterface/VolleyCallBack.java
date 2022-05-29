package VolleyInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public interface VolleyCallBack {
    void onSuccess(JSONObject response) throws JSONException, ParseException;
}
