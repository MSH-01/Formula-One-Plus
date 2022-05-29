package Repository;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import VolleyInterface.VolleyCallBack;

public class apiRepository {

    public void callAPIJSON(String url, RequestQueue requestQueue,final VolleyCallBack callBack) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            Log.d("API REQUEST", "Success");
                        } catch (Error e) {
                            Log.e("API REQUEST",e.toString());
                        }
                        try {
                            callBack.onSuccess(res);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API REQUEST", error.toString());
                    }
                }

        ){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String, String> customHeaders = new HashMap<String,String>();
                customHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0");
                return customHeaders;
            }
        };

        requestQueue.add(jsonRequest);
    };


}
