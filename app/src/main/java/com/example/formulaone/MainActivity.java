package com.example.formulaone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import Repository.apiRepository;
import Service.eventsService;
import Service.newsService;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.Standings:
                    startActivity(new Intent(getApplicationContext(),Standings.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Home:
                    return true;
                case R.id.Locations:
                    startActivity(new Intent(getApplicationContext(), Locations.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        RequestQueue rq = Volley.newRequestQueue(this);

        apiRepository repository = new apiRepository();

        SharedPreferences newsPreferences = getSharedPreferences("formulaOneNews",MODE_PRIVATE);
        SharedPreferences.Editor newsEditor = newsPreferences.edit();
        SimpleDateFormat formatCompare = new SimpleDateFormat("dd:MM:yyyy, HH:mm");
        Date nextSunday = getNextSunday();
        String nextSundayStr = formatCompare.format(nextSunday);
        Date timeNow = getTimeNow();

        Date prefUpdateOnNews = new Date();
        try {
            prefUpdateOnNews = formatCompare.parse(newsPreferences.getString("updateOn",nextSundayStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(timeNow.after(prefUpdateOnNews) | !(newsPreferences.contains("updateOn"))) {
            repository.callAPIJSON("https://newsapi.org/v2/everything?domains=formula1.com&q=f1&pageSize=1&apiKey=00c7fb8c0607465bb3b90e0c71c19285", rq, news -> {
                Log.d("news card","API CALL MADE");
                SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy, HH:mm");

                newsEditor.putString("updateOn",formatter.format(getNextSunday()));

                newsService newsServiceMain = new newsService(news);
                newsEditor.putString("news",news.toString());

                TextView headline = (TextView) findViewById(R.id.newsHeadline);
                headline.setText(newsServiceMain.getHeadline());
                newsEditor.putString("prefHeadline",newsServiceMain.getHeadline());

                ImageView newsImg = (ImageView) findViewById(R.id.newsImage);
                Picasso.get().load("https://www.formula1.com/content/dam/fom-website/manual/Misc/2022manual/WinterTestingBarcelona/Day2/GettyImages-1372557951.jpg.transform/9col/image.jpg").into(newsImg);
                newsEditor.putString("prefImage","https://www.formula1.com/content/dam/fom-website/manual/Misc/2022manual/WinterTestingBarcelona/Day2/GettyImages-1372557951.jpg.transform/9col/image.jpg");

                TextView bio = (TextView) findViewById(R.id.newsBio);
                bio.setText(newsServiceMain.getBio());
                newsEditor.putString("prefBio",newsServiceMain.getBio());

                TextView source = (TextView) findViewById(R.id.newsSubtitle);
                source.setText(newsServiceMain.getAuthor());
                newsEditor.putString("prefAuthor",newsServiceMain.getAuthor());

                TextView date = (TextView) findViewById(R.id.newsDate);
                date.setText(newsServiceMain.getDate());
                newsEditor.putString("prefDate",newsServiceMain.getDate());

                newsEditor.apply();
            });
        } else{
            Log.d("news card","API CALL NOT MADE");
            TextView headline = (TextView) findViewById(R.id.newsHeadline);
            headline.setText(newsPreferences.getString("prefHeadline",""));

            ImageView newsImg = (ImageView) findViewById(R.id.newsImage);
            Picasso.get().load(newsPreferences.getString("prefImage","")).into(newsImg);

            TextView bio = (TextView) findViewById(R.id.newsBio);
            bio.setText(newsPreferences.getString("prefBio",""));

            TextView source = (TextView) findViewById(R.id.newsSubtitle);
            source.setText(newsPreferences.getString("prefAuthor",""));

            TextView date = (TextView) findViewById(R.id.newsDate);
            date.setText(newsPreferences.getString("prefDate",""));
        }



        SharedPreferences racePreferences = getSharedPreferences("formulaOneRace",MODE_PRIVATE);
        SharedPreferences.Editor raceEditor = racePreferences.edit();
        Date prefUpdateOn = new Date();
        try {
            prefUpdateOn = formatCompare.parse(racePreferences.getString("updateOn",nextSundayStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(timeNow.after(prefUpdateOn) | !(racePreferences.contains("updateOn"))){
            repository.callAPIJSON("https://www.thesportsdb.com/api/v1/json/2/eventsseason.php?id=4370&s=2022",rq,events -> {
                Log.d("race timer","api called");
                eventsService eventsServiceMain = new eventsService(events);
                SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy, HH:mm");

                raceEditor.putString("updateOn",formatter.format(getNextSunday()));

                TextView raceName = (TextView) findViewById(R.id.raceName);
                raceName.setText(eventsServiceMain.getNextRace());
                raceEditor.putString("prefRaceDate",eventsServiceMain.getNextDate());
                raceEditor.putString("prefRaceName",eventsServiceMain.getNextRace());
                raceEditor.apply();

                Date nextRace = formatter.parse(eventsServiceMain.getNextDate());

                Date today = new Date();

                assert nextRace != null;
                long diffInMilliseconds = nextRace.getTime() - today.getTime();

                TextView countdownText = findViewById(R.id.countdown);

                new CountDownTimer(diffInMilliseconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        final long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                        final long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                        countdownText.setText(days+"D "+hours+"H "+minutes+"M "+seconds+"S ");
                    }

                    public void onFinish() {
                        countdownText.setText("It's lights out.");
                    }
                }.start();

            });
        } else{
            Log.d("race timer","api not called");
            TextView raceName = (TextView) findViewById(R.id.raceName);
            raceName.setText(racePreferences.getString("prefRaceName",""));

            SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy, HH:mm");

            Date nextRace = null;
            try {
                nextRace = formatter.parse(racePreferences.getString("prefRaceDate",""));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date today = new Date();

            assert nextRace != null;
            long diffInMilliseconds = nextRace.getTime() - today.getTime();

            TextView countdownText = findViewById(R.id.countdown);

            new CountDownTimer(diffInMilliseconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    final long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                    final long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                    final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    countdownText.setText(days+"D "+hours+"H "+minutes+"M "+seconds+"S ");
                }

                public void onFinish() {
                    countdownText.setText("It's lights out.");
                }
            }.start();

        }
        Button openDialog;
        Dialog dialog;

        openDialog = (Button) findViewById(R.id.readButton);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        dialog = new Dialog(this);
        dialog.setContentView(dialogView);

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Toast.makeText(MainActivity.this,"Tap to close", Toast.LENGTH_SHORT).show();
            }
        });

        ConstraintLayout constraintLayout = dialogView.findViewById(R.id.constraintLayoutMain);

//        constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

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