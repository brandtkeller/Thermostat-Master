package thermo;

import thermo.models.Thermostat;

public class Thermoshare {
    private static Thermostat instance = null;
    
    public static Thermostat getInstance() {
        if (instance == null) {
            instance = new Thermostat(70,70,true);
        }
        System.out.println("Returning instance of thermo");
        return instance;
    }
}