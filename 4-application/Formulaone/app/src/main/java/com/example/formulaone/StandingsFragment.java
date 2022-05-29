package com.example.formulaone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Repository.apiRepository;
import Service.standingsAdapter;
import Service.driverStandingsService;


public class StandingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private final apiRepository repository = new apiRepository();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        RequestQueue rq = Volley.newRequestQueue(view.getContext());

        SharedPreferences standingsPreferences = getActivity().getSharedPreferences("formulaOneStandings", Context.MODE_PRIVATE);
        SharedPreferences.Editor standingsEditor = standingsPreferences.edit();

        SimpleDateFormat formatCompare = new SimpleDateFormat("dd:MM:yyyy, HH:mm");
        Date nextSunday = getNextSunday();
        String nextSundayStr = formatCompare.format(nextSunday);
        Date timeNow = getTimeNow();

        Date updateOnDriverStandings = new Date();
        try {
            updateOnDriverStandings = formatCompare.parse(standingsPreferences.getString("updateOn",nextSundayStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(timeNow.after(updateOnDriverStandings)| !(standingsPreferences.contains("updateOn"))){
            repository.callAPIJSON("https://ergast.com/api/f1/current/driverStandings.json", rq, response -> {

                standingsEditor.putString("updateOn",nextSundayStr);

                Log.d("Standings Activity","API REQUEST Complete");

                standingsEditor.putString("standings", String.valueOf(response));

                driverStandingsService parser = new driverStandingsService(response);

                ArrayList<String> drivers = parser.getDriverName(response);
                standingsEditor.putString("drivers", String.valueOf(drivers));

                ArrayList<String> constructor = parser.getConstructor(response);
                standingsEditor.putString("constructor",String.valueOf(constructor));

                ArrayList<String> points = parser.getDriverPoints(response);
                standingsEditor.putString("points",String.valueOf(points));

                standingsEditor.apply();


                recyclerView = view.findViewById(R.id.testRecycler);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new standingsAdapter(getContext(),drivers,constructor,points));

            });
        } else {
            String driversStr = standingsPreferences.getString("drivers","");
            driversStr = driversStr.substring(0,driversStr.length()-1);
            String[] strSplit = driversStr.split(",");
            for(int i =0; i<strSplit.length;i++){
                strSplit[i] = strSplit[i].substring(1,strSplit[i].length());
            }
            ArrayList<String> drivers = new ArrayList<String>(
                    Arrays.asList(strSplit));

            String constructorStr = standingsPreferences.getString("constructor","");
            constructorStr = constructorStr.substring(0,constructorStr.length()-1);
            String[] strSplit2 = constructorStr.split(",");
            for(int i =0; i<strSplit2.length;i++){
                strSplit2[i] = strSplit2[i].substring(1,strSplit2[i].length());
            }
            ArrayList<String> constructor = new ArrayList<String>(
                    Arrays.asList(strSplit2));

            String pointsStr = standingsPreferences.getString("points","");
            pointsStr = pointsStr.replace(" ","");
            pointsStr = pointsStr.substring(1,pointsStr.length()-1);
            String[] strSplit3 = pointsStr.split(",");
            ArrayList<String> points = new ArrayList<String>(
                    Arrays.asList(strSplit3));


            recyclerView = view.findViewById(R.id.testRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new standingsAdapter(getContext(),drivers,constructor,points));
        }


        return view;
    }

    private Date getNextSunday(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        // get start of this week in milliseconds
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        Date nextSunday = calendar.getTime();

        Log.d("date to update",nextSunday.toString());

        return nextSunday;
    };

    private Date getTimeNow(){
        return new Date();
    };
}