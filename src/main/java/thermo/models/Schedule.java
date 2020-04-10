package thermo.models;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    // Arraylist of Settings?
    private int id;
    private Setting currentSetting = null;
    private String title;
    private List<Setting> settingList;
    
    public Schedule(int id, String title) {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Setting> getsettingList() {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        return settingList;
    }

    public void setSettingList(List<Setting> settings) {
        this.settingList = settings;
    }

    public void addSettingToList(Setting setting) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        settingList.add(setting);
    }

    public Setting getCurrentSetting() {
        return this.currentSetting;
    }

    public void setCurrentSetting(Setting cs) {
        this.currentSetting = cs;
    }
}