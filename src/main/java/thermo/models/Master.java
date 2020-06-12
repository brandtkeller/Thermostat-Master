package thermo.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public void removeThermostat(int id) {
        Iterator<Thermostat> itr = tList.iterator(); 
        while (itr.hasNext()) 
        { 
            int x = itr.next().getId(); 
            if (x == id) 
                itr.remove(); 
        } 
    }

    public void modifyThermostat(Thermostat temp) {
        ListIterator<Thermostat> itr = tList.listIterator();
        int id = temp.getId();
        while (itr.hasNext()) 
        { 
            int x = itr.next().getId(); 
            if (x == id) {
                itr.set(temp);
                // Instant response to change
                temp.executeTemperatureCheck();
            }
                
        } 
    }

    public void executeThermostatCheck() {
        for (Thermostat thermo : tList) {
            thermo.executeTemperatureCheck();
            
        }
    }
}