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
import Service.constructorAdapter;
import Service.constructorStandingsService;
import Service.standingsAdapter;
import Service.driverStandingsService;

public class ConstructorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private final apiRepository repository = new apiRepository();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_constructors, container, false);

        RequestQueue rq = Volley.newRequestQueue(view.getContext());

        SharedPreferences constructorPreferences = getActivity().getSharedPreferences("formulaOneConstructors", Context.MODE_PRIVATE);
        SharedPreferences.Editor constructorEditor = constructorPreferences.edit();

        SimpleDateFormat formatCompare = new SimpleDateFormat("dd:MM:yyyy, HH:mm");
        Date nextSunday = getNextSunday();
        String nextSundayStr = formatCompare.format(nextSunday);
        Date timeNow = getTimeNow();

        Date updateOnConstructorStandings = new Date();
        try {
            updateOnConstructorStandings = formatCompare.parse(constructorPreferences.getString("updateOn",nextSundayStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(timeNow.after(updateOnConstructorStandings) | !(constructorPreferences.contains("updateOn"))){
            repository.callAPIJSON("https://ergast.com/api/f1/current/constructorStandings.json", rq, response -> {
                Log.d("Standings Activity", "API REQUEST Complete");

                constructorEditor.putString("updateOn",nextSundayStr);

                constructorEditor.putString("standings", String.valueOf(response));

                constructorStandingsService parser = new constructorStandingsService(response);

                ArrayList<String> constructors = parser.getConstructorName(response);
                constructorEditor.putString("constructors", String.valueOf(constructors));

                ArrayList<String> points = parser.getConstructorPoints(response);
                constructorEditor.putString("points", String.valueOf(points));

                constructorEditor.apply();


                recyclerView = view.findViewById(R.id.constructorRecycler);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new constructorAdapter(getContext(), constructors, points));

            });
        }else{

            String constructorsStr = constructorPreferences.getString("constructors","");
            constructorsStr = constructorsStr.substring(0,constructorsStr.length()-1);
            String[] strSplit = constructorsStr.split(",");
            for(int i =0; i<strSplit.length;i++){
                strSplit[i] = strSplit[i].substring(1,strSplit[i].length());
            }
            ArrayList<String> constructors = new ArrayList<String>(
                    Arrays.asList(strSplit));

            String pointsStr = constructorPreferences.getString("points","");
            pointsStr = pointsStr.replace(" ","");
            pointsStr = pointsStr.substring(1,pointsStr.length()-1);
            String[] strSplit3 = pointsStr.split(",");
            ArrayList<String> points = new ArrayList<String>(
                    Arrays.asList(strSplit3));


            recyclerView = view.findViewById(R.id.constructorRecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new constructorAdapter(getContext(),constructors,points));

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