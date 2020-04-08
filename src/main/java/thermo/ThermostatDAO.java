package thermo;

import thermo.models.*;

public class ThermostatDAO {
    private static Thermostat instance = null;
    
    // This will become the initialization logic
    // We'll have access to the global instance of thermostat here
    // If 'instance' is null, begin querying database and creating objects
    // if database is empty, start with a standard static template
    public static Thermostat getInstance() {
        if (instance == null) {
            // Query DB and create thermostat object, get the active schedule ID
            instance = new Thermostat(3, 1, "test");
            // Query DB and create schedule object from active schedule
                // Assign to thermostat
            Schedule schedule = new Schedule();
            instance.setSchedule(schedule);
            // Query DB and create all setting objects
                // Assign to schedule
            //schedule.addSettingToList(new Setting());
        }
        return instance;
    }

    
}