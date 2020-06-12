package thermo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;

import thermo.models.Thermostat;
import thermo.database.*;
import thermo.MasterDAO;

public class ThermostatController {

    // Get all thermostat objects
    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <String> getThermostats() {
        String response = "{'data':[";
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        List<Thermostat> tList = test.getAllThermostats();

        for (Thermostat temp : tList) {
            response += temp;
        }
        // Remove the comma from the last object converted to string
        response = StringUtils.chop(response);
        // End of payload
        response += "]}";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> getThermostat(@PathVariable("id") String id) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();
        Thermostat temp = test.getThermostatById(Integer.parseInt(id));

        if (temp != null) {
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(temp.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Thermostat was not found", HttpStatus.NOT_FOUND);
        }
    }  

    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> putThermostat(@PathVariable("id") String id, @RequestBody Thermostat thermo) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        if (test.modifyThermostat(thermo)) {
            MasterDAO.updateMaster();
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(thermo.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Thermostat was not found", HttpStatus.NOT_FOUND);
        }
    }  

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addThermostat(@RequestBody Thermostat thermo) {
        Pgdatabase test = MasterDAO.getDatabaseInstance();
        int id = test.createThermostat(thermo);

        if (id == -1) {
            return new ResponseEntity<String>("Problem creating the requested resource", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        thermo.setId(id);
        MasterDAO.updateMaster();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(thermo.getId())
        .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> deleteThermostat(@PathVariable("id") String id) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        List<Thermostat> tList = test.getAllThermostats();

        for (Thermostat temp : tList) {
            if (temp.getId() == Integer.parseInt(id)) {
                if (test.removeThermostat(Integer.parseInt(id))) {
                    MasterDAO.updateMaster();
                    return new ResponseEntity<String>("{'data':[]}", HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<String>("Problem Deleting the requested resource", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<String>("Thermostat was not found", HttpStatus.NOT_FOUND);
    }
}