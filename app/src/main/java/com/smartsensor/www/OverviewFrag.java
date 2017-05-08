package com.smartsensor.www;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by joeal_000 on 4/25/2017.
 */

public class OverviewFrag extends Fragment{

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            getActivity().setTitle("Overview");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.overview_main, container, false);
        }

    public ArrayList<Data> getData(){
        //make a firebase instance
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final ArrayList<Data> datalist = new ArrayList<Data>();
        DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                    String date2 = child.getKey();

                    Data d = new Data(PM, PM1, PM10, battery_voltage, formaldehyde, humidity,
                            location, pressure, rbv, state, temperature, temperature_BMP, temperature_DS3231,
                            uv);
                    d.setDate(date2);
                    datastack.push(d);
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

    public ArrayList<Data> getLatestData(){
        //make a firebase instance
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final ArrayList<Data> datalist = new ArrayList<Data>();
        DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
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
                ;
            }
        });

        return datalist;

    }

    public void fillData(ArrayList<Data> datalist){
        final TextView PMTV = (TextView) getView().findViewById(R.id.PMTVLabel);
        final TextView AirPressureTV = (TextView) getView().findViewById(R.id.AirPressureTV);
        final TextView AirQualTV = (TextView) getView().findViewById(R.id.AirQualTV);
        final TextView BatVoltTV = (TextView) getView().findViewById(R.id.BatVoltTV);
        final TextView GeoLocTV = (TextView) getView().findViewById(R.id.GeoLocTV);
        final TextView UVTV = (TextView) getView().findViewById(R.id.UVTV);
        final TextView TempTV = (TextView) getView().findViewById(R.id.TempTV);
        final TextView RelHumTV = (TextView) getView().findViewById(R.id.RelHumTV);
        final TextView pm1tv = (TextView) getView().findViewById(R.id.pm1tv);
        final TextView pm10tv = (TextView) getView().findViewById(R.id.pm10tv);
        final TextView rbvtv = (TextView) getView().findViewById(R.id.rbvtv);
        final TextView statetv = (TextView) getView().findViewById(R.id.statetv);
        final TextView tempbmptv = (TextView) getView().findViewById(R.id.tempbmptv);
        final TextView tempnodetv = (TextView) getView().findViewById(R.id.tempnodetv);
        final TextView datetv = (TextView) getView().findViewById(R.id.dateTV);

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
        datetv.setText(String.valueOf(datalist.get(0).getDate()));
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
}
