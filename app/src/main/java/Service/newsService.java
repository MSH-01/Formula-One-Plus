package Service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newsService {

    private final JSONObject parsedNews;

    public newsService(JSONObject news) throws JSONException{
        parsedNews= (news.getJSONArray("articles")).getJSONObject(0);
        Log.d("news service", news.toString());
    }

    public String getHeadline() throws JSONException {
        String headline = parsedNews.getString("title");
        int indexOfSource = headline.indexOf("|");
        return headline.substring(0,indexOfSource);
    }

    public String getAuthor() throws JSONException{
        JSONObject categoryArray = parsedNews.getJSONObject("source");
        return categoryArray.getString("name");
    }

    public String getBio() throws JSONException{

        String bio = parsedNews.getString("content");

        int indexOfEnd = bio.indexOf("[");


        return bio.substring(0,indexOfEnd);

    }

    public String getDate() throws JSONException{

        String date = parsedNews.getString("publishedAt");

        int indexOfEnd = date.indexOf("T");

        return date.substring(0,indexOfEnd);
    }

    public String getImageURL() throws JSONException{
        Log.d("news service image url",parsedNews.getString("urlToImage"));
        return parsedNews.getString("urlToImage");
    }

}
