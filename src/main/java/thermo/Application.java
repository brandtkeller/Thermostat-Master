package thermo;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import thermo.models.Thermostat;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Thermostat global = Thermoshare.getInstance();

        SpringApplication.run(Application.class, args);

        
        for (int i = 0; i < 100; i++) {
            System.out.println("Here is some other logic that I need to run, currentTemp = " + global.getCurrentTemp());
            global.setCurrentTemp((global.getCurrentTemp() + 1));
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e) {
                
            }
        }

        // Check Temperature 

        // Get Current timestamp

        // Check for schedule update (Compare current timestamp against setting timestamp)

        // Compare current temp against schedule temp
        // If outside threshold, send signal to HCU

        

        
    }
}