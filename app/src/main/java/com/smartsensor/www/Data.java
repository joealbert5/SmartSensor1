package com.smartsensor.www;

/**
 * Created by joeal_000 on 4/22/2017.
 */

public class Data {
    private String date;
    private double PM;
    private double PM1;
    private double PM10;
    private double battery_voltage;
    private double formaldehyde;
    private double humidity;
    private Location location;
    private double pressure;
    private double raw_battery_voltage;
    private int state;
    private double temperature;
    private double temperature_BMP;
    private double temperature_DS3231;
    private double ultraviolet;

    public Data(double PM, double PM1, double PM10, double battery_voltage,
            double formaldehyde,
            double humidity,
            Location location, double pressure,
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
        this.location = location;
        this.pressure = pressure;
        this.raw_battery_voltage = raw_battery_voltage;
        this.state = state;
        this.temperature = temperature;
        this.temperature_BMP = temperature_BMP;
        this.temperature_DS3231 = temperature_DS3231;
        this.ultraviolet = ultraviolet;
        this.date = "error";
    }

    Data(){
        this.PM = -1;
        this.PM1 = -1;
        this.PM10 = -1;
        this.battery_voltage = -1;
        this.formaldehyde = -1;
        this.humidity = -1;
        this.location = new Location();
        this.pressure = -1;
        this.raw_battery_voltage = -1;
        this.state = -1;
        this.temperature = -1;
        this.temperature_BMP = -1;
        this.temperature_DS3231 = -1;
        this.ultraviolet = -1;
        this.date = "error";
    }

    public Data add(Data dn){
        this.PM += dn.getPM();
        this.PM1 += dn.getPM1();
        this.PM10 += dn.getPM10();
        this.battery_voltage += dn.getBattery_voltage();
        this.formaldehyde += dn.getFormaldehyde();
        this.humidity += dn.getHumidity();
        this.pressure += dn.getPressure();
        this.raw_battery_voltage += dn.getRaw_battery_voltage();
        this.state += dn.getState();
        this.temperature += dn.getTemperature();
        this.temperature_BMP += dn.getTemperature_BMP();
        this.temperature_DS3231 += dn.getTemperature_DS3231();
        this.ultraviolet += dn.getUltraviolet();

        return this;
    }

    public Data divide(double d){
        this.PM /= d;
        this.PM1 /= d;
        this.PM10 /= d;
        this.battery_voltage /= d;
        this.formaldehyde /= d;
        this.humidity /= d;
        this.pressure /= d;
        this.raw_battery_voltage /= d;
        this.state /= d;
        this.temperature /= d;
        this.temperature_BMP /= d;
        this.temperature_DS3231 /= d;
        this.ultraviolet /= d;

        return this;
    }

    public Data multiply(double d){
        this.PM *= d;
        this.PM1 *= d;
        this.PM10 *= d;
        this.battery_voltage *= d;
        this.formaldehyde *= d;
        this.humidity *= d;
        this.pressure *= d;
        this.raw_battery_voltage *= d;
        this.state *= d;
        this.temperature *= d;
        this.temperature_BMP *= d;
        this.temperature_DS3231 *= d;
        this.ultraviolet *= d;

        return this;
    }

    public Data multiply(Data dn){
        this.PM *= dn.getPM();
        this.PM1 *= dn.getPM1();
        this.PM10 *= dn.getPM10();
        this.battery_voltage *= dn.getBattery_voltage();
        this.formaldehyde *= dn.getFormaldehyde();
        this.humidity *= dn.getHumidity();
        this.pressure *= dn.getPressure();
        this.raw_battery_voltage *= dn.getRaw_battery_voltage();
        this.state *= dn.getState();
        this.temperature *= dn.getTemperature();
        this.temperature_BMP *= dn.getTemperature_BMP();
        this.temperature_DS3231 *= dn.getTemperature_DS3231();
        this.ultraviolet *= dn.getUltraviolet();

        return this;
    }

    public Data sqrt(){
        this.PM = Math.sqrt(PM);
        this.PM1 = Math.sqrt(PM1);
        this.PM10 = Math.sqrt(PM10);
        this.battery_voltage = Math.sqrt(battery_voltage);
        this.formaldehyde = Math.sqrt(formaldehyde);
        this.humidity = Math.sqrt(humidity);
        this.pressure = Math.sqrt(pressure);
        this.raw_battery_voltage = Math.sqrt(raw_battery_voltage);
        this.temperature = Math.sqrt(temperature);
        this.temperature_BMP = Math.sqrt(temperature_BMP);
        this.temperature_DS3231 = Math.sqrt(temperature_DS3231);
        this.ultraviolet = Math.sqrt(ultraviolet);

        return this;
    }

    public void setDate(String date) {
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

    public double getFormaldehyde() {
        return formaldehyde;
    }

    public double getPM() {
        return PM;
    }

    public double getPM1() {
        return PM1;
    }

    public double getPM10() {
        return PM10;
    }

    public double getPressure() {
        return pressure;
    }

    public int getState() {
        return state;
    }

    public double getUltraviolet() {
        return ultraviolet;
    }

    public Location getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public void setBattery_voltage(double battery_voltage) {
        this.battery_voltage = battery_voltage;
    }

    public void setRaw_battery_voltage(double raw_battery_voltage) {
        this.raw_battery_voltage = raw_battery_voltage;
    }

    public void setFormaldehyde(double formaldehyde) {
        this.formaldehyde = formaldehyde;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPM(double PM) {
        this.PM = PM;
    }

    public void setPM1(double PM1) {
        this.PM1 = PM1;
    }

    public void setPM10(double PM10) {
        this.PM10 = PM10;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setTemperature_BMP(double temperature_BMP) {
        this.temperature_BMP = temperature_BMP;
    }

    public void setTemperature_DS3231(double temperature_DS3231) {
        this.temperature_DS3231 = temperature_DS3231;
    }

    public void setUltraviolet(double ultraviolet) {
        this.ultraviolet = ultraviolet;
    }
}
