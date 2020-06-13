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
            Pgdatabase db = getDatabaseInstance();
            mInstance = new Master();
            mInstance.setThermostats(db.getAllThermostats());
        }
        return mInstance;
    }

    public static Pgdatabase getDatabaseInstance() {

        if (pInstance == null) {
            String db_url = System.getProperty("db_url");
            String db_user = System.getProperty("db_user");
            String db_pass = System.getProperty("db_pass");

            if (db_url == null || db_user == null || db_pass == null) {
                System.out.println("Not all command line elements present");
                System.out.println("Expecting Format: java -jar -Ddb_url=<postgresurl:port/db> -Ddb_user=<db user> -Ddb_pass=<db password>  target/thermo-master-0.0.1.jar");
                System.exit(1);
            } else {
                pInstance = new Pgdatabase(db_url, db_user, db_pass);
            }
                
        }
        return pInstance;
    }

    public static GpioRunner getGpioInstance() {
        if ( gInstance == null ) {
            gInstance = new GpioRunner();
        }
        return gInstance;
    }

    // Since the number of calls is minimal, we can call db.init(). This can be improved to only update necessary objects
    // This can be removed after adding more specific update functionality
    public static void updateMaster() {
        if (mInstance == null) {
            getMasterInstance();
        } else {
            Pgdatabase db = getDatabaseInstance();
            mInstance.setThermostats(db.getAllThermostats());
        }
    }

    public static void addThermostatToMaster(Thermostat temp) {
        if (mInstance == null) {
            getMasterInstance();
        }
        mInstance.addThermostat(temp);
    }

    public static void removeThermostatFromMaster(int id) {
        if (mInstance == null) {
            getMasterInstance();
        }
        mInstance.removeThermostat(id);
    }

    public static void modifyThermostatOnMaster(Thermostat temp) {
        if (mInstance == null) {
            getMasterInstance();
        } 
        mInstance.modifyThermostat(temp);
    }

    public static void modifyScheduleOnMaster(Schedule temp) {
        if (mInstance == null) {
            getMasterInstance();
        }
        mInstance.modifySchedule(temp);
    }

    public static boolean removeScheduleFromMaster(int id) {
        if (mInstance == null) {
            getMasterInstance();
        }
        return mInstance.removeSchedule(id);
    }
    
    // Add functionality to update thermostat objects in-place
    // override updateMaster() with a version for thermostat updates, schedule updates, and setting updates
    // All thermostat updates must update the master object -> Thermostat object
    // POST to thermostat should update master object via masterDAO
    // During schedule update, only update master when the ID belongs to one of the thermostat objects (loop through thermostat objects and getScheduleId())
    // During setting updates, only update master when the setting.scheduleId belongs to one of the thermostat objects

    // Thermostat objects should have other non-modifiable private variables for changing state. 
    
}