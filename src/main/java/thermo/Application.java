package thermo;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import thermo.models.Master;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Master thermo = MasterDAO.getMasterInstance();
        SpringApplication.run(Application.class, args);

        // Allow the sprint boot application to initialize itself and the thermostatDAO instance
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e) {
            
        }
        
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