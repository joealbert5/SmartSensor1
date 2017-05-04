package com.fuck.www;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by joeal_000 on 4/26/2017.
 */

public class HistoryActivityReal extends ActionBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);
        //fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        getPastData();
    }

    public void onClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getPastData(){
        //make a firebase instance
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final ArrayList<Data> datalist = new ArrayList<Data>();
        DatabaseReference myRef = db.getReferenceFromUrl("https://smartsensor-842a9.firebaseio.com/SensorNode/S127");
        Query lastQuery = myRef.orderByKey().limitToLast(1000);
        // Read from the database
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datalist.clear();
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
                    datalist.add(d);
                }
                Data mean = calculateMean(datalist);
                Data std = calculateStd(datalist, mean);
                fillData(mean, std);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void fillData(Data d, Data std) {
        final TextView temp = (TextView) findViewById(R.id.textView17);
        final TextView tempbmptv = (TextView) findViewById(R.id.textView18);
        final TextView tempnodetv = (TextView) findViewById(R.id.textView19);
        final TextView RelHumTV = (TextView) findViewById(R.id.textView20);
        final TextView pm1tv = (TextView) findViewById(R.id.textView21);
        final TextView pm25tv = (TextView) findViewById(R.id.textView22);
        final TextView pm10tv = (TextView) findViewById(R.id.textView23);
        final TextView UVTV = (TextView) findViewById(R.id.textView24);
        final TextView fermaldehyde = (TextView) findViewById(R.id.textView25);
        final TextView BatVoltTV = (TextView) findViewById(R.id.textView26);
        final TextView rbvtv = (TextView) findViewById(R.id.textView27);
        final TextView AirPressureTV = (TextView) findViewById(R.id.textView2);

        temp.setText("Mean: " + String.format( "%.2f", d.getTemperature()) + "  Std: " + String.format("%.2f", std.getPressure()));
        tempbmptv.setText("Mean: " + String.format("%.2f", d.getTemperature_BMP()) + "  Std: " + String.format("%.2f", std.getTemperature_BMP()));
        tempnodetv.setText("Mean: " + String.format("%.2f", d.getTemperature_DS3231()) + "  Std: " + String.format("%.2f", std.getTemperature_DS3231()));
        RelHumTV.setText("Mean: " + String.format("%.2f", d.getHumidity()) + "  Std: " + String.format("%.2f", std.getHumidity()));
        pm1tv.setText("Mean: " + String.format("%.2f", d.getPM1()) + "  Std: " + String.format("%.2f", std.getPM1()));
        pm25tv.setText("Mean: " + String.format("%.2f", d.getPM()) + "  Std: " + String.format("%.2f", std.getPM()));
        pm10tv.setText("Mean: " + String.format("%.2f", d.getPM10()) + "  Std: " + String.format("%.2f", std.getPM10()));
        UVTV.setText("Mean: " + String.format("%.2f", d.getUltraviolet()) + "  Std: " + String.format("%.2f", std.getUltraviolet()));
        fermaldehyde.setText("Mean: " + String.format("%.2f", d.getFormaldehyde()) + "  Std: " + String.format("%.2f", std.getFormaldehyde()));
        BatVoltTV.setText("Mean: " + String.format("%.2f", d.getBattery_voltage()) + "  Std: " + String.format("%.2f", std.getBattery_voltage()));
        rbvtv.setText("Mean: " + String.format("%.2f", d.getRaw_battery_voltage()) + "  Std: " + String.format("%.2f", std.getRaw_battery_voltage()));
        AirPressureTV.setText("Mean: " + String.format("%.2f", d.getPressure()) + "  Std: " + String.format("%.2f", std.getPressure()));


    }

    private Data calculateMean(ArrayList<Data> list){
        Data dAvg = new Data(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, new Location(), 0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0);
        for (int i = 0; i < list.size(); i++){
            dAvg.add(list.get(i));
        }
        return dAvg.divide(list.size());
    }

    private Data calculateStd(ArrayList<Data> list, Data mean){
        Data temp;
        Data sum = new Data(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, new Location(), 0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0);
        for (int i = 0; i < list.size(); i++){
            mean.multiply(-1);
            temp = list.get(i);
            temp.add(mean);
            temp.multiply(temp);
            sum.add(temp);
        }
        sum.divide(list.size());
        return sum.sqrt();
    }


}


/*
class DataNums{
            private double PM;
            private double PM1;
            private double PM10;
            private double battery_voltage;
            private double formaldehyde;
            private double humidity;
            private double pressure;
            private double raw_battery_voltage;
            private int state;
            private double temperature;
            private double temperature_BMP;
            private double temperature_DS3231;
            private double ultraviolet;

            DataNums(double PM, double PM1, double PM10, double battery_voltage,
                 double formaldehyde,
                 double humidity,
                 double pressure,
                 double raw_battery_voltage,
                 int state,
                 double temperature,
                 double temperature_BMP,
                 double temperature_DS3231,
                 double ultraviolet){

                this.PM = PM;
                this.PM1 = PM1;
                this.PM10 = PM10;
                this.battery_voltage = battery_voltage;
                this.formaldehyde = formaldehyde;
                this.humidity = humidity;
                this.pressure = pressure;
                this.raw_battery_voltage = raw_battery_voltage;
                this.state = state;
                this.temperature = temperature;
                this.temperature_BMP = temperature_BMP;
                this.temperature_DS3231 = temperature_DS3231;
                this.ultraviolet = ultraviolet;
            }
            DataNums(){
                this.PM = 0;
                this.PM1 = 0;
                this.PM10 = 0;
                this.battery_voltage = 0;
                this.formaldehyde = 0;
                this.humidity = 0;
                this.pressure = 0;
                this.raw_battery_voltage = 0;
                this.state = 0;
                this.temperature = 0;
                this.temperature_BMP = 0;
                this.temperature_DS3231 = 0;
                this.ultraviolet = 0;
            }

            public DataNums add(DataNums dn){
                this.PM += dn.PM;
                this.PM1 += 0;
                this.PM10 += 0;
                this.battery_voltage += 0;
                this.formaldehyde += 0;
                this.humidity += 0;
                this.pressure += 0;
                this.raw_battery_voltage += 0;
                this.state += 0;
                this.temperature += 0;
                this.temperature_BMP += 0;
                this.temperature_DS3231 += 0;
                this.ultraviolet += 0;
            }
        };

        for (int i = 0; i < list.size(); i++){
            DataNums dm = new DataNums(list.get(i).getPM(), list.get(i).getPM1(), list.get(i).getPM10(), list.get(i).getBattery_voltage(),
                    list.get(i).getFormaldehyde(), list.get(i).getHumidity(), list.get(i).getPressure(), list.get(i).getRaw_battery_voltage(),
                    list.get(i).getState(), list.get(i).getTemperature(), list.get(i).getTemperature_BMP(), list.get(i).getTemperature_DS3231(),
                    list.get(i).getUltraviolet());
            dnlist.add(dm);
        }
 */