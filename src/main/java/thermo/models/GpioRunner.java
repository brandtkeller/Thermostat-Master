package thermo.models;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import com.pi4j.wiringpi.GpioUtil;

import java.util.Hashtable;

import com.pi4j.component.temperature.TemperatureSensor;

public class GpioRunner {
    private Hashtable<Integer, GpioPinDigitalOutput> output_dict = new Hashtable<Integer, GpioPinDigitalOutput>();
    private Hashtable<Integer, Pin> pin_dict = new Hashtable<Integer, Pin>();
    private Hashtable<Integer, Boolean> state_dict = new Hashtable<Integer, Boolean>();


    public void initializeGPIO() {
        // Add pre-configured pins to the initialization 
        pin_dict.put(1, RaspiPin.GPIO_00); // Fan relay
        pin_dict.put(2, RaspiPin.GPIO_02); // Heat relay
        pin_dict.put(3, RaspiPin.GPIO_03); // Cool relay

        state_dict.put(1, false);
        state_dict.put(2, false);
        state_dict.put(3, false);
    }

    public void activateRelay(int relayPin) {
        GpioUtil.enableNonPrivilegedAccess();
        final GpioController gpioRelay = GpioFactory.getInstance();
        GpioPinDigitalOutput relay = null;
        // Check if dictionary item for pin is null
        relay = output_dict.get(relayPin);
        // If null, assign
        if (relay == null) {
            output_dict.put(relayPin, gpioRelay.provisionDigitalOutputPin(pin_dict.get(relayPin),"RelayLED",PinState.HIGH)); // OFF
            relay = output_dict.get(relayPin);
        }
        relay.low(); // ON
    }

    public void deactivateRelay(int relayPin) {
        GpioUtil.enableNonPrivilegedAccess();
        final GpioController gpioRelay = GpioFactory.getInstance();
        GpioPinDigitalOutput relay = null;
        // Check if dictionary item for pin is null
        relay = output_dict.get(relayPin);
        // If null, assign
        if (relay == null) {
            output_dict.put(relayPin, gpioRelay.provisionDigitalOutputPin(pin_dict.get(relayPin),"RelayLED",PinState.HIGH)); // OFF
            relay = output_dict.get(relayPin);
        }
        relay.high(); // OFF

    }
    
    public double getTemperature(){
		
        W1Master w1Master = new W1Master();

        // System.out.println(w1Master);

        for (TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)) {
            // System.out.printf("%-20s %3.1f°C %3.1f°F\n", device.getName(), device.getTemperature(),
            //         device.getTemperature(TemperatureScale.CELSIUS));
            if(device.getName().contains("28-"))
            	return device.getTemperature(TemperatureScale.FARENHEIT);
        	//return 32;
        }
		return 0;
    }
}