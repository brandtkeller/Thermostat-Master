package thermo;

import thermo.models.*;
import thermo.database.*;

public class MasterDAO {
    private static Master instance = null;
    
    // This will become the initialization logic
    // We'll have access to the global instance of thermostat here
    // If 'instance' is null, begin querying database and creating objects
    // if database is empty, start with a standard static template
    public static Master getInstance() {
        if (instance == null) {
            Pgdatabase db = Pgdatabase.getInstance();
            instance = new Master();
            instance.setThermostats(db.init());
        }
        return instance;
    }

    // Since the number of calls is minimal, we can call db.init(). This can be improved to only update necessary objects
    public static void updateMaster() {
        if (instance == null) {
            getInstance();
        } else {
            Pgdatabase db = Pgdatabase.getInstance();
            instance.setThermostats(db.init());
        }
    }
}