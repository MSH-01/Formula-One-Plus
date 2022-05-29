package com.example.formulaone;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Repository.apiRepository;
import Service.driverStandingsService;
import Service.standingsAdapter;

/**
 * This shows how to create a simple activity with a raw MapView and add a marker to it. This
 * requires forwarding all the important lifecycle methods onto MapView.
 */
public class Locations extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;


    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Locations);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.Standings:
                    startActivity(new Intent(getApplicationContext(),Standings.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Locations:
                    startActivity(new Intent(getApplicationContext(), Locations.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });





        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {


        map.addMarker(new MarkerOptions().position(new LatLng(-37.8497,144.968)).title("Melbourne Grand Prix"));
        map.addMarker(new MarkerOptions().position(new LatLng(30.1328,-97.6411)).title("Circuit of the Americas"));
        map.addMarker(new MarkerOptions().position(new LatLng(26.0325,50.5106)).title("Bahrain International Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(40.3725,49.8533)).title("Baku City Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(41.57,2.26111)).title("Circuit de Barcelona-Catalunya"));
        map.addMarker(new MarkerOptions().position(new LatLng(47.5789,19.2486)).title("Hungaroring"));
        map.addMarker(new MarkerOptions().position(new LatLng(44.3439,11.7167)).title("Autodromo Enzo e Dino Ferrari"));
        map.addMarker(new MarkerOptions().position(new LatLng(-23.7036,-46.6997)).title("Autódromo José Carlos Pace"));
        map.addMarker(new MarkerOptions().position(new LatLng(21.6319,39.1044)).title("Jeddah Corniche Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(1.2914,103.864)).title("Marina Bay Street Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(25.9581,-80.2389)).title("Miami International Autodrome"));
        map.addMarker(new MarkerOptions().position(new LatLng(43.7347,7.42056)).title("Circuit de Monaco"));
        map.addMarker(new MarkerOptions().position(new LatLng(45.6156,9.28111)).title("Autodromo Nazionale di Monza"));
        map.addMarker(new MarkerOptions().position(new LatLng(47.2197,14.7647)).title("Red Bull Ring"));
        map.addMarker(new MarkerOptions().position(new LatLng(43.2506,5.79167)).title("Circuit Paul Ricard"));
        map.addMarker(new MarkerOptions().position(new LatLng(19.4042,-99.0907)).title("Autódromo Hermanos Rodríguez"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.0786,-1.01694)).title("Silverstone Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(50.4372,5.97139)).title("Circuit de Spa-Francorchamps"));
        map.addMarker(new MarkerOptions().position(new LatLng(34.8431,136.541)).title("Suzuka Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(45.5,-73.5228)).title("Circuit Gilles Villeneuve"));
        map.addMarker(new MarkerOptions().position(new LatLng(24.4672,54.6031)).title("Yas Marina Circuit"));
        map.addMarker(new MarkerOptions().position(new LatLng(52.3888,4.54092)).title("Circuit Park Zandvoort"));


    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}