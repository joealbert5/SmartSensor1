package com.fuck.www;

/**
 * Created by joeal_000 on 4/22/2017.
 */

public class Data {
    private long date;
    private int PM;
    private int PM1;
    private int PM10;
    private double battery_voltage;
    private int formaldehyde;
    private double humidity;
    private Location location;
    private int pressure;
    private double raw_battery_voltage;
    private int state;
    private double temperature;
    private double temperature_BMP;
    private double temperature_DS3231;
    private int ultraviolet;

    public Data(int PM, int PM1, int PM10, double battery_voltage,
            int formaldehyde,
            double humidity,
            Location location,
            int pressure,
            double raw_battery_voltage,
            int state,
            double temperature,
            double temperature_BMP,
            double temperature_DS3231,
            int ultraviolet){

        this.PM = PM;
        this.PM1 = PM1;
        this.PM10 = PM10;
        this.battery_voltage = battery_voltage;
        this.formaldehyde = formaldehyde;
        this.humidity = humidity;
        this.location = location;
        this.pressure = pressure;
        this.raw_battery_voltage = raw_battery_voltage;
        this.state = state;
        this.temperature = temperature;
        this.temperature_BMP = temperature_BMP;
        this.temperature_DS3231 = temperature_DS3231;
        this.ultraviolet = ultraviolet;
        this.date = -1;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getBattery_voltage() {
        return battery_voltage;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getRaw_battery_voltage() {
        return raw_battery_voltage;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperature_BMP() {
        return temperature_BMP;
    }

    public double getTemperature_DS3231() {
        return temperature_DS3231;
    }

    public int getFormaldehyde() {
        return formaldehyde;
    }

    public int getPM() {
        return PM;
    }

    public int getPM1() {
        return PM1;
    }

    public int getPM10() {
        return PM10;
    }

    public int getPressure() {
        return pressure;
    }

    public int getState() {
        return state;
    }

    public int getUltraviolet() {
        return ultraviolet;
    }

    public Location getLocation() {
        return location;
    }
}
