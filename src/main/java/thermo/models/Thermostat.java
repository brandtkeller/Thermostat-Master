package thermo.models;

import thermo.database.Pgdatabase;

public class Thermostat {
    private int id;
    private int currentTemp; // This should not be needed
    private int setTemp; // Cached setting temperature until settingEndTime
    private String settingEndTime;
    private int threshold; // 3 or 4
    private boolean fan; // 
    private String mode; // 'heat', 'cool', "off"
    private boolean state; // true = on, false = off
    private String title; 
    private int scheduleId;
    private Schedule schedule;
    // Maybe a list of associated nodes?

    public Thermostat() {

        // Initial default values for testing
        this.currentTemp = 70;
        this.setTemp = 70;
        this.threshold = 3;
        this.fan = false;
        this.schedule = null;
    }

    public Thermostat(int id, int threshold, String title, int scheduleId) {
        this.id = id;
        this.threshold = threshold;
        this.title = title;
        this.scheduleId = scheduleId;
    }

    // This should return the stringified version of the JSON API spec object for data
    @Override
    public String toString() { 
        return String.format("{'type':'thermostat','id':'" + id + "','attributes':{'title':'" + title + "','threshold':'" + threshold + "'}},"); 
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentTemp() {
        return this.currentTemp;
    }

    public void setCurrentTemp(int ct) {
        this.currentTemp = ct;
    }

    public int getSetTemp() {
        return this.setTemp;
    }

    public void setSetTemp(int st) {
        this.setTemp = st;
    }

    public boolean getFan() {
        return this.fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int t) {
        this.threshold = t;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(int id) {
        this.scheduleId = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    /* ---------- Main loop logic ---------- */
    public void executeTemperatureCheck() {

        // If mode == 'off' - Do nothing

        int setTemp = schedule.getCurrentSettingTemp();
        System.out.println("Current set Temperature is: " + setTemp);
        
        // We now have the current set temperature
        // Compare against node mean temperature
        Pgdatabase db = Pgdatabase.getInstance();
        int meanTemp = db.getAllNodeTempsByThermostat(this.id);
        if (meanTemp == -1) {
            System.out.println("No nodes with temperature updates to utilize.");
        } else {
            System.out.println("Mean temperature is : " + meanTemp);
        }

        // If mode == heat
            // If (setTemp - meanTemp) > threshold && state == false
                // activate Heat and Fan Relays
            // If (meanTemp - setTemp) > (threshold - 1) && state == true
                // de-activate Heat and Fan Relays
            // Otherwise do nothing

        // If mode == cool
            // If (meanTemp - setTemp) > threshold && state == false
                // activate Cool and Fan Relays
            // If (setTemp - meanTemp) > (threshold - 1) && state == true
                // de-activate Cool and Fan Relays
            // Otherwise do nothing
    }

    
}