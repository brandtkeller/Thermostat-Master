package thermo.models;

public class Setting {
    private int id;
    private String day; // IE 'Thur'
    private String wake; // IE '09:00:00'
    private int wakeTemp; // IE '67'
    private String leave;
    private int leaveTemp;
    private String home;
    private int homeTemp;
    private String sleep;
    private int sleepTemp;    


    public Setting(String day, String wake, int wt, String leave, int lt, String home, int ht, String sleep, int st) {
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
    // Contain logic (method) for returning current 'Set Temperature'
    // If Setting is valid return 'Set Temperature', else return -1
    // If -1, schedule needs to update current setting
}