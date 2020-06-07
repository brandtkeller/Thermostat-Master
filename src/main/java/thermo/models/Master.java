package thermo.models;

import java.util.ArrayList;
import java.util.List;

public class Master {

    List<Thermostat> tList;

    public Master() {
        tList = new ArrayList<>();
    }

    public List<Thermostat> getThermostats() {
        return tList;
    }

    public void setThermostats( List<Thermostat> tList) {
        this.tList = tList;
    }

    public void addThermostat (Thermostat temp) {
        tList.add(temp);
    }

    public void executeThermostatCheck() {
        for (Thermostat thermo : tList) {
            thermo.executeTemperatureCheck();
            
        }
    }
}