package com.example.hazardcrowdsourcing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.Vector;
import java.util.concurrent.Executors;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng centerlocation;

    Information[] informations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment )  getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        centerlocation = new LatLng(3.0, 101);

        // Load locations from api
        Executors.newSingleThreadExecutor().execute(() -> {
            final String jsonText = RequestHandler.sendGetRequest("http://192.168.0.8/ict602gp/news.php");
            NewsFragment.Response response = new Gson().fromJson(jsonText, NewsFragment.Response.class);
            if(!response.success)
                runOnUiThread(()-> Toast.makeText(this, response.message, Toast.LENGTH_LONG).show());
            else
                runOnUiThread(()-> {
                    for (NewsFragment.News i : response.news)
                        map.addMarker(
                                new MarkerOptions()
                                .position(new LatLng(i.lat, i.lng))
                                        .title(i.name)
                                        .snippet(i.description));
                });
        });

        String perms[] = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            Log.d("Maps", "permission granted");
        } else {
            Log.d("Maps", "permission denied");
            ActivityCompat.requestPermissions(this, perms, 200);
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(centerlocation));
    }
}