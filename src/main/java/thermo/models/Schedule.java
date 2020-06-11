package thermo.models;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Schedule {
    // Arraylist of Settings?
    private int id;
    private Setting currentSetting = null;
    private String title;
    private List<Setting> settingList;

    private static final Map<String, Integer> daymap;
    static {
        daymap = new HashMap<>();
        daymap.put("Sun", 0);
        daymap.put("Mon", 1);
        daymap.put("Tue", 2);
        daymap.put("Wed", 3);
        daymap.put("Thur", 4);
        daymap.put("Fri", 5);
        daymap.put("Sat", 6);
    }

    public Schedule(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("{'type':'schedule','id':'" + id + "','attributes':{'title':'" + title + "}},");
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

    public void sortSettingList() {
        List<Setting> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : daymap.entrySet()) {

            List<Setting> temp = settingList.stream().filter(a -> Objects.equals(a.getDay(), entry.getKey()))
                    .collect(Collectors.toList());
            result.add(temp.get(0));
        }
        settingList = result;
    }

    public void updateCurrentSetting() {
        ZoneId currentZone = ZoneId.of("America/Los_Angeles");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E");
        ZonedDateTime currentTime = ZonedDateTime.now(currentZone);

        String currentDay = format.format(currentTime);
        Setting tempSetting = settingList.get(daymap.get(currentDay));
        
        String[] timeArray = tempSetting.getWakeTime().split(":");
        ZonedDateTime compareTime = currentTime.with(LocalTime.of ( Integer.parseInt(timeArray[0]) , Integer.parseInt(timeArray[1]) ));
        
        if (currentTime.isBefore(compareTime) ) {
            int index = settingList.indexOf(tempSetting);
            if (index == 0) {
                this.currentSetting = settingList.get(6);
            } else {
                this.currentSetting = settingList.get(index - 1);
            }
        } else {
            this.currentSetting = tempSetting;
        }
    }

    public void setCurrentSetting(Setting cs) {
        this.currentSetting = cs;
    }

    public int getCurrentSettingTemp() {
        ZoneId currentZone = ZoneId.of("America/Los_Angeles");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E");
        ZonedDateTime currentTime = ZonedDateTime.now(currentZone);
        String currentDay = format.format(currentTime);

        updateCurrentSetting();
        if (currentDay.compareToIgnoreCase(this.currentSetting.getDay()) != 0) {
            // This means it is still the sleep period of the setting
            System.out.println("Previous day sleep period");
            return this.currentSetting.getSleepTemp();
        } 

        if (checkPeriod(this.currentSetting.getWakeTime(), this.currentSetting.getLeaveTime())) {
            System.out.println("Wake period");
            return this.currentSetting.getWakeTemp();
        } else if (checkPeriod(this.currentSetting.getLeaveTime(), this.currentSetting.getHomeTime())) {
            System.out.println("Leave period");
            return this.currentSetting.getLeaveTemp();
        } else if (checkPeriod(this.currentSetting.getHomeTime(), this.currentSetting.getSleepTime())) {
            System.out.println("Home period");
            return this.currentSetting.getHomeTemp();
        } else {
            System.out.println("Sleep period");
            return this.currentSetting.getSleepTemp();
        }
    }

    private boolean checkPeriod(String start, String end) {
        ZoneId currentZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime currentTime = ZonedDateTime.now(currentZone);

        String[] timeArray = start.split(":");
        ZonedDateTime compareStartTime = currentTime.with(LocalTime.of ( Integer.parseInt(timeArray[0]) , Integer.parseInt(timeArray[1]) ));
        timeArray = end.split(":");
        ZonedDateTime compareEndTime = currentTime.with(LocalTime.of ( Integer.parseInt(timeArray[0]) , Integer.parseInt(timeArray[1]) ));
        if ((currentTime.isEqual(compareStartTime) || currentTime.isAfter(compareStartTime)) && currentTime.isBefore(compareEndTime)) {
            return true;
        } else {
            return false;
        }
    }
}