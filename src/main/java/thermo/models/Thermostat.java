package thermo.models;

public class Thermostat {
    private int currentTemp;
    private int setTemp;
    private int threshold;
    private boolean fan;
    private Schedule schedule;

    public Thermostat(int ct, int st, boolean fan) {
        this.currentTemp = ct;
        this.setTemp = st;
        this.threshold = 3;
        this.fan = fan;
        // This needs to be populated from the database or given a default
        this.schedule = null;
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


}