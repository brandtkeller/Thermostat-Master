package thermo;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import thermo.models.Thermostat;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Thermostat thermo = null;

        SpringApplication.run(Application.class, args);

        // Allow the sprint boot application to initialize itself and the thermostatDAO instance
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            
        }

        // thermo = ThermostatDAO.getInstance();

        // Get ambient temperature from sensor
        // Compare against Thermostat -> schedule -> setting Temp
        // Thermo.getSetTemp() -> schedule.getSetTemp() -> Setting.getSetTemp() (all return integers)
        // How does the API update settings?

        // The main loop will not be changing the current schedule
        // The API will handle changing the current schedule

        // Do we store all settings in memory or only the current and pull from DB
            // Memory Thermostat instance -> current Schedule -> Array of Settings
            // Make change to Setting, alter database, if setting is on current schedule re-initialize current schedule settings array
            // Make change to current Schedule (new or existing), Configure objects in DB first, Assign Thermostat new current schedule
                // Query all Settings from DB, create object and add to Schedule's Settings Array. <- Minimizes DB calls storing in memory
                // Have failsafe to  
        // Compare current temp against schedule temp
        // If outside threshold, send signal to HCU

        

        
    }
}