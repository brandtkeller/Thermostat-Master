package thermo;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import thermo.database.Pgdatabase;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Master will not work without associated DB, exit 1 if properties are not present
        String db_url = System.getProperty("db_url");
        String db_user = System.getProperty("db_user");
        String db_pass = System.getProperty("db_pass");

        if (db_url == null || db_user == null || db_pass == null) {
            System.out.println("Not all command line elements present");
            System.out.println("Expecting Format: java -jar -Ddb_url=<postgresurl:port/db> -Ddb_user=<db user> -Ddb_pass=<db password>  target/thermo-master-0.0.1.jar");
            System.exit(1);
        } else {
            Pgdatabase.initializeDb(db_url, db_user, db_pass);
        }


        SpringApplication.run(Application.class, args);

        // Allow the sprint boot application to initialize itself and the thermostatDAO instance
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            
        }

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