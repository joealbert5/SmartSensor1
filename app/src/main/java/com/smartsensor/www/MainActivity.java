package com.smartsensor.www;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//NavigationView.OnNavigationItemSelectedListener
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{

    //TODO: air pressure being integer and not double

    MapFragment mMapFragment;
    Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getLatestData();

        mMapFragment = MapFragment.newInstance();
        android.app.FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        double lat = mLocation.getLat();
        double lng = mLocation.getLng();
        //LatLng loc = new LatLng(34.06358337402344, -118.4521255493164);
        LatLng loc = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title("Current Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }


    public ArrayList<Data> getLatestData(){
        //make a firebase instance
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final ArrayList<Data> datalist = new ArrayList<>();
        final DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
        Query lastQuery = myRef.orderByKey().limitToLast(1);

        // Read from the database
        lastQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Data newData = dataSnapshot.getValue(Data.class);
                String date = dataSnapshot.getKey();
                newData.setDate(date);
                datalist.add(newData);
                fillData(datalist);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                datalist.clear();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        return datalist;

    }

    @SuppressLint("SetTextI18n")
    public void fillData(ArrayList<Data> datalist){
        final TextView PMTV = (TextView) findViewById(R.id.PMTV);
        final TextView AirPressureTV = (TextView) findViewById(R.id.AirPressureTV);
        final TextView AirQualTV = (TextView) findViewById(R.id.AirQualTV);
        final TextView BatVoltTV = (TextView) findViewById(R.id.BatVoltTV);
        final TextView GeoLocTV = (TextView) findViewById(R.id.GeoLocTV);
        final TextView UVTV = (TextView) findViewById(R.id.UVTV);
        final TextView TempTV = (TextView) findViewById(R.id.TempTV);
        final TextView RelHumTV = (TextView) findViewById(R.id.RelHumTV);
        final TextView pm1tv = (TextView) findViewById(R.id.pm1tv);
        final TextView pm10tv = (TextView) findViewById(R.id.pm10tv);
        final TextView rbvtv = (TextView) findViewById(R.id.rbvtv);
        final TextView statetv = (TextView) findViewById(R.id.statetv);
        final TextView tempbmptv = (TextView) findViewById(R.id.tempbmptv);
        final TextView tempnodetv = (TextView) findViewById(R.id.tempnodetv);
        final TextView datetv = (TextView) findViewById(R.id.dateTV);

        PMTV.setText(String.valueOf(datalist.get(0).getPM()) + "µg/m³");
        AirPressureTV.setText(String.valueOf(datalist.get(0).getPressure()) + "hPa");
        AirQualTV.setText(determineAQ(datalist.get(0).getFormaldehyde()));
        BatVoltTV.setText(String.valueOf(datalist.get(0).getBattery_voltage()) + "mV");
        GeoLocTV.setText(String.valueOf(datalist.get(0).getFormaldehyde())+ "µg/m³");
        UVTV.setText(String.valueOf(datalist.get(0).getUltraviolet()) + "mV");
        TempTV.setText(String.valueOf(datalist.get(0).getTemperature()) + "°C");
        RelHumTV.setText(String.valueOf(datalist.get(0).getHumidity()) + "%");
        pm1tv.setText(String.valueOf(datalist.get(0).getPM1()) + "µg/m³");
        pm10tv.setText(String.valueOf(datalist.get(0).getPM10()) + "µg/m³");
        rbvtv.setText(String.valueOf(datalist.get(0).getRaw_battery_voltage()) + "mV");
        statetv.setText(String.valueOf(datalist.get(0).getState()));
        tempbmptv.setText(String.valueOf(datalist.get(0).getTemperature_BMP()) + "°C");
        tempnodetv.setText(String.valueOf(datalist.get(0).getTemperature_DS3231()) + "°C");

        long dv = Long.valueOf(datalist.get(0).getDate())*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        @SuppressLint("SimpleDateFormat") String vv = new SimpleDateFormat("MM/dd/yyyy hh:mma").format(df);
        datetv.setText("Last updated: " + vv);
        for (int k = 0; k < 2; k++) {
            mLocation = datalist.get(0).getLocation();
            mMapFragment.getMapAsync(this);
        }
    }

    public String determineAQ(double i){
        if (i < 15.5)
            return "Healthy";
        else if (i >=15.5 && i <=35.4)
            return "Moderate";
        else if (i >= 35.5 && i <=55.4)
            return "Unhealthy for Sensitive Groups";
        else if (i >= 55.5 && i <=150.4)
            return "Unhealthy";
        else if (i >= 150.5 && i <=250.4)
            return "Very Unhealthy";
        else if (i >= 250.5 && i <=55.4)
            return "Hazardous";
        else
            return "error";
    }
/*
    public String determineLoc(ArrayList<Data> list){
        return list.get(0).getLocation().displayLocation();
    }*/



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    private void displaySelectedScreen(int id){
        //Fragment fragment = null;
        Intent intent;

        switch(id){
            case R.id.nav_overview:
                //fragment = new OverviewFrag();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                //fragment = new HistoryActivity();
                intent = new Intent(this, HistoryActivityReal.class);
                startActivity(intent);
                break;
            case R.id.nav_plots:
                intent = new Intent(this, PlotActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
