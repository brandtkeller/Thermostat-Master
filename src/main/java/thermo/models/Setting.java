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

    @Override
    public String toString() { 
        return String.format("{'type':'setting','id':'" + id + "','attributes':{'day':'" + day + 
        "','wake':'" + wake + "','wakeTemp':'" + wakeTemp +
        "','leave':'" + leave + "','leaveTemp':'" + leaveTemp +
        "','home':'" + home + "','homeTemp':'" + homeTemp +
        "','sleep':'" + sleep + "','sleepTemp':'" + sleepTemp +
        "'}},"); 
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(int id) {
        this.scheduleId = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWakeTime() {
        return this.wake;
    }

    public void setWakeTime(String wake) {
        this.wake = wake;
    }

    public int getWakeTemp() {
        return this.wakeTemp;
    }

    public void setWaketemp(int temp) {
        this.wakeTemp = temp;
    }

    public String getLeaveTime() {
        return this.leave;
    }

    public void setLeaveTime(String leave) {
        this.leave = leave;
    }

    public int getLeaveTemp() {
        return this.leaveTemp;
    }

    public void setLeaveTemp(int temp) {
        this.leaveTemp = temp;
    }

    public String getHomeTime() {
        return this.home;
    }

    public void setHomeTime(String home) {
        this.home = home;
    }

    public int getHomeTemp() {
        return this.homeTemp;
    }

    public void setHomeTemp(int temp) {
        this.homeTemp = temp;
    }

    public String getSleepTime() {
        return this.sleep;
    }

    public void setSleepTime(String sleep) {
        this.sleep = sleep;
    }

    public int getSleepTemp() {
        return this.sleepTemp;
    }

    public void setSleepTemp(int temp) {
        this.sleepTemp = temp;
    }
}

