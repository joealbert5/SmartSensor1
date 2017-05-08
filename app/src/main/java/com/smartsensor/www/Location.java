package com.smartsensor.www;

/**
 * Created by joeal_000 on 4/22/2017.
 */

public class Location {
    int alt;
    double lat;
    double lng;

    Location(int alt, double lat, double lng){
        this.alt = alt;
        this.lat = lat;
        this.lng = lng;
    }

    Location(){
        this.alt = 0;
        this.lat = 0;
        this.lng = 0;
    }

    public String displayLocation(){
        return "Alt: " + String.valueOf(alt) + "  Lat: " + String.valueOf(lat) + "  Lng: " + String.valueOf(lng);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getAlt() {
        return alt;
    }
}
