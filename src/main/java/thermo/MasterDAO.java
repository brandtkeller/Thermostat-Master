package thermo;

import thermo.models.*;
import thermo.database.*;

public class MasterDAO {
    private static Master mInstance = null;
    private static Pgdatabase pInstance = null;
    private static GpioRunner gInstance = null;

    
    // This will become the initialization logic
    // We'll have access to the global mInstance of thermostat here
    // If 'mInstance' is null, begin querying database and creating objects
    // if database is empty, start with a standard static template
    public static Master getMasterInstance() {
        if (mInstance == null) {
            Pgdatabase db = Pgdatabase.getInstance();
            mInstance = new Master();
            mInstance.setThermostats(db.init());
        }
        return mInstance;
    }

    // Since the number of calls is minimal, we can call db.init(). This can be improved to only update necessary objects
    public static void updateMaster() {
        if (mInstance == null) {
            getMasterInstance();
        } else {
            Pgdatabase db = Pgdatabase.getInstance();
            mInstance.setThermostats(db.init());
        }
    }
}