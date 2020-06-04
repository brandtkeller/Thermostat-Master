package thermo.models;


public class Thermostat {
    private int id;
    private int currentTemp;
    private int setTemp;
    private int threshold;
    private boolean fan;
    private String mode;
    private boolean state;
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
    public int getCurrentTempSetting() {
        // Get currentDay
        // Get currentTime
        // if thermostat.currentSettingEndTime == null || thermostat.currentSettingEndTime < currentTime 
            // Get all settings from schedule
            // get setting for current day
            // if current time < wake
                // thermostat.currentSettingTemp = previous day sleep
                // thermostat.currentSettingEndTime = current day wake
            // else
                // thermostat.currentSettingTemp = current day wake
                // thermostat.currentSettingEndTime = current day leave
            // return currentSettingTemp
        // else
            // return currentSettingTemp

        return 0;
    }

    
}