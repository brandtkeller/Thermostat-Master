package thermo.models;

public class Setting {
    private int id;
    private int scheduleId;
    private String day; // IE 'Thur'
    private String wake; // IE '09:00:00'
    private int wakeTemp; // IE '67'
    private String leave;
    private int leaveTemp;
    private String home;
    private int homeTemp;
    private String sleep;
    private int sleepTemp;    


    public Setting(int id, int scheduleId, String day, String wake, int wt, String leave, int lt, String home, int ht, String sleep, int st) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.day = day;
        this.wake = wake;
        this.wakeTemp = wt;
        this.leave = leave;
        this.leaveTemp = lt;
        this.home = home;
        this.homeTemp = ht;
        this.sleep = sleep;
        this.sleepTemp = st;
    }
    
    public int getId() {
        return this.id;
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public String getDay() {
        return this.day;
    }

    public String getWakeTime() {
        return this.wake;
    }

    public int getWakeTemp() {
        return this.wakeTemp;
    }

    public String getLeaveTime() {
        return this.leave;
    }

    public int getLeaveTemp() {
        return this.leaveTemp;
    }

    public String getHomeTime() {
        return this.home;
    }

    public int getHomeTemp() {
        return this.homeTemp;
    }

    public String getSleepTime() {
        return this.sleep;
    }

    public int getSleepTemp() {
        return this.sleepTemp;
    }
}