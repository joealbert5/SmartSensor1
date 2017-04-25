package com.fuck.www;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FAB action
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //make a firebase instance
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final ArrayList<Data> datalist = getLatestData(db);     //ordered newest to oldest


    }

    public ArrayList<Data> getData(FirebaseDatabase db){
        final ArrayList<Data> datalist = new ArrayList<Data>();
        DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(f, "Value is: " + value);
                Stack<Data> datastack = new Stack<>();
                int counter = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //long date = child.getValue(Long.class);
                    long date = 0;
                    int PM = child.child("PM").getValue(int.class);
                    int PM1 = child.child("PM1").getValue(int.class);
                    int PM10 = child.child("PM10").getValue(int.class);
                    double battery_voltage = child.child("battery voltage").getValue(double.class);
                    int formaldehyde = child.child("formaldehyde").getValue(int.class);
                    double humidity = child.child("humidity").getValue(double.class);
                    Location location = child.child("location").getValue(Location.class);
                    int pressure = child.child("pressure").getValue(int.class);
                    double rbv = child.child("raw battery voltage").getValue(double.class);
                    int state = child.child("state").getValue(int.class);
                    double temperature = child.child("temperature").getValue(double.class);
                    double temperature_BMP = child.child("temperature_BMP").getValue(double.class);
                    double temperature_DS3231 = child.child("temperature_DS3231").getValue(double.class);
                    int uv = child.child("ultraviolet").getValue(int.class);

                    Data d = new Data(PM, PM1, PM10, battery_voltage, formaldehyde, humidity,
                            location, pressure, rbv, state, temperature, temperature_BMP, temperature_DS3231,
                            uv);
                    d.setDate(date);
                    datastack.push(d);
                    if (counter >= 50){
                        break;
                    }
                    counter++;
                }
                while (!datastack.empty()) {
                    Data d = datastack.pop();
                    datalist.add(d);
                }
                fillData(datalist);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return datalist;

    }

    public ArrayList<Data> getLatestData(FirebaseDatabase db){
        final ArrayList<Data> datalist = new ArrayList<Data>();
        DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
        // Read from the database

        myRef.addChildEventListener(new ChildEventListener() {
            Stack<Data> datastack = new Stack<>();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Data newData = dataSnapshot.getValue(Data.class);
                double i = newData.getPM();
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
            public void onCancelled(DatabaseError databaseError) {}
        });

        return datalist;

    }

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

        PMTV.setText(String.valueOf(datalist.get(0).getPM()));
        AirPressureTV.setText(String.valueOf(datalist.get(0).getPressure()));
        AirQualTV.setText(determineAQ(datalist.get(0).getFormaldehyde()));
        BatVoltTV.setText(String.valueOf(datalist.get(0).getBattery_voltage()));
        GeoLocTV.setText(determineLoc(datalist));
        UVTV.setText(String.valueOf(datalist.get(0).getUltraviolet()));
        TempTV.setText(String.valueOf(datalist.get(0).getTemperature()));
        RelHumTV.setText(String.valueOf(datalist.get(0).getHumidity()));
        pm1tv.setText(String.valueOf(datalist.get(0).getPM1()));
        pm10tv.setText(String.valueOf(datalist.get(0).getPM10()));
        rbvtv.setText(String.valueOf(datalist.get(0).getRaw_battery_voltage()));
        statetv.setText(String.valueOf(datalist.get(0).getState()));
        tempbmptv.setText(String.valueOf(datalist.get(0).getTemperature_BMP()));
        tempnodetv.setText(String.valueOf(datalist.get(0).getTemperature_DS3231()));
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

    public String determineLoc(ArrayList<Data> list){
        return list.get(0).getLocation().displayLocation();
    }

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

        if (id == R.id.nav_overview) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_plots) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
