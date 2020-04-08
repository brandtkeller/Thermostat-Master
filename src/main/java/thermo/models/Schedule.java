package thermo.models;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    // Arraylist of Settings?
    private Setting currentSetting = null;
    private String name;
    private List<Setting> settingList;
    
    public Schedule() {

    }

    public List<Setting> getsettingList() {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        return settingList;
    }

    public void addSettingToList(Setting setting) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        settingList.add(setting);
    }
}