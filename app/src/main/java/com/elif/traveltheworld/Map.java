package com.elif.traveltheworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.Edits;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nullable;

public class Map extends AppCompatActivity implements OnMapReadyCallback {


    static public GoogleMap mMap;
    public static Button wentbutton;
    public Button wantbutton;

    final static HashMap<String, GeoJsonLayer> countries_and_names = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        wentbutton = findViewById(R.id.wentbutton);
        wantbutton = findViewById(R.id.wantbutton);


        new MyThread().execute();


    }

    public void openWantPage(String country){
        Intent intent = new Intent(this, WantBtnActivity.class );
        intent.putExtra("Key", country);
        startActivity(intent);
    }





    public  class MyThread extends AsyncTask<Void, String, HashMap<String, GeoJsonLayer>>
    {

        @Override
        protected HashMap<String, GeoJsonLayer> doInBackground(Void... voids) {

            try {

                for (int item = 0; item < Country.getLayers().length; item++) {
                    countries_and_names.put(Country.getCountry()[item],
                            new GeoJsonLayer(mMap, Country.getLayers()[item], getApplicationContext()));
                }
            }
            catch (Exception e) {
                e.getStackTrace();
            }



            return  countries_and_names;




        }


        @Override
        protected void onPostExecute(final HashMap<String, GeoJsonLayer> result) {

            try {

                final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());



                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        try {


                            final List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                            Toast.makeText(getApplicationContext(), "Do you want to go  " + addresses.get(0).getCountryName()
                                    + "?", Toast.LENGTH_LONG).show();

                            wentbutton.setOnClickListener(new View.OnClickListener() {
                                @Override

                                public void onClick(View v) {
                                    if (result.keySet().contains(addresses.get(0).getCountryName())) {

                                        final GeoJsonLayer layer = result.get(addresses.get(0).getCountryName());
                                        colorMap(layer);
                                        ClickMap(layer);

                                        Country.putVisited(addresses.get(0).getCountryName());


                                    }

                                }
                            });

                            wantbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openWantPage(addresses.get(0).getCountryName());

                                }
                            });

                        } catch (Exception e) {
                            //
                        }
                    }



                });

            }

            catch (Exception ex) {
                //
            }



        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.item1){
            Intent intent= new Intent(Map.this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.item2){
            Intent intent= new Intent(Map.this, Map.class);
            startActivity(intent);
        }
        if(id==R.id.item3){
            Intent intent= new Intent(Map.this, Total.class);
            startActivity(intent);
        }
        if(id==R.id.item4){
            Intent intent= new Intent(Map.this, Where2Go.class);
            startActivity(intent);
        }
        if(id==R.id.item5){
            Intent intent= new Intent(Map.this, Profile.class);
            startActivity(intent);
        }

        if(id==R.id.item6) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();


        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));





    }


    static public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }


    public void colorMap( GeoJsonLayer layer) {

        GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
        polygonStyle.setPolygonFillColor(getRandomColor());
        polygonStyle.setStrokeWidth(0);
        layer.addLayerToMap();
    }

    public void ClickMap(GeoJsonLayer layer) {
        GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
        polygonStyle.setClickable(false);


    }
}


