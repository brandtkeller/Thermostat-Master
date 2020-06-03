package thermo;

import thermo.models.*;
import thermo.database.*;

public class ThermostatDAO {
    private static Thermostat instance = null;
    
    // This will become the initialization logic
    // We'll have access to the global instance of thermostat here
    // If 'instance' is null, begin querying database and creating objects
    // if database is empty, start with a standard static template
    public static Thermostat getInstance() {
        if (instance == null) {
            Pgdatabase db = Pgdatabase.getInstance();
            instance = db.init("Master");
        }
        return instance;
    }

    
}