package thermo.models;

import thermo.database.Pgdatabase;

public class Thermostat {
    private int id;
    private int currentTemp; // This should not be needed
    // private int setTemp; // Cached setting temperature until settingEndTime
    // private String settingEndTime;
    private int threshold; // 3 or 4
    private boolean fan; //
    private boolean fanState;
    private String mode; // 'heat', 'cool', "off"
    private boolean state; // true = on, false = off
    private String locality; // local or remote
    private String address; // Ip address
    private String title; 
    private int scheduleId;
    private Schedule schedule;
    // Maybe a list of associated nodes?

    public Thermostat() {
        // Initial default values for testing
        this.currentTemp = 70;
        this.threshold = 3;
        this.fan = false;
        this.schedule = null;
    }

    public Thermostat(int id, int threshold, String title, int scheduleId, String mode) {
        this.id = id;
        this.threshold = threshold;
        this.title = title;
        this.scheduleId = scheduleId;
        this.mode = mode;
        // By default the state will be false. Need to have some pin cleanup
        this.state = false;
        this.fanState = false;
    }

    // This should return the stringified version of the JSON API spec object for data
    @Override
    public String toString() { 
        return String.format("{'type':'thermostat','id':'" + id + "','attributes':{'title':'" + title + "','threshold':'" + threshold + "','mode':'" + mode + "','scheduleId':'" + scheduleId + "'}},"); 
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

    public boolean getFan() {
        return this.fan;
    }

    public void setFan(boolean fan) {
        this.fan = fan;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode( String mode) {
        this.mode = mode;
    }

    public boolean getState() {
        return this.state;
    }

    public void setMode( boolean state) {
        this.state = state;
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

        if (this.mode != "Off") {
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

            if (this.mode == "Heat") {
                if ((setTemp - meanTemp) > threshold && this.state == false) {
                    System.out.println("Activating the relays");
                    this.state = true;
                }

                if ((meanTemp - setTemp) > threshold && this.state == true) {
                    System.out.println("De-activating the relays");
                    this.state = false;
                }
            }

            if (this.mode == "Cool") {
                if ((meanTemp - setTemp) > threshold && this.state == false) {
                    System.out.println("Activating the relays");
                    this.state = true;
                }

                if ((setTemp - meanTemp) > threshold && this.state == true) {
                    System.out.println("De-activating the relays");
                    this.state = false;
                }
            }
        } else {
            System.out.println("Thermostat mode is set to off.");
            // TODO: we need to deactivate any relays currently on. Can we simply call de-activate on non-running pins?
            // Or do we need to create an object to maintain state?
        }
    }

    
}