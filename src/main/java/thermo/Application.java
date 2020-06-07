package thermo;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import thermo.database.Pgdatabase;
import thermo.models.Master;

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
        Master thermo = MasterDAO.getInstance();
        // Handle keyboard interrupt gracefully
        Runtime.getRuntime().addShutdownHook(new Thread() 
        {
            @Override
            public void run() 
            {
                System.out.println("Shutting down");
                // Enter other shutdown logic here
            }
        });
        while(true) {
            thermo.executeThermostatCheck();
            try {
                TimeUnit.SECONDS.sleep(10);
            }
            catch (InterruptedException e) {
                
            }
        }
    }
}