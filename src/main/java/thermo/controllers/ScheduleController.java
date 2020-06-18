package thermo.controllers;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import thermo.database.Pgdatabase;
import thermo.models.Schedule;
import thermo.MasterDAO;

@RestController
@RequestMapping(path ="/schedules")
public class ScheduleController {

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <String> getSchedules() {
        String response = "{'data':[";
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        List<Schedule> tList = test.getAllSchedules();

        for (Schedule temp : tList) {
            response += temp;
        }
        // Remove the comma from the last object converted to string
        response = StringUtils.chop(response);
        // End of payload
        response += "]}";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> getSchedule(@PathVariable("id") String id) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();
        Schedule temp = test.getScheduleById(Integer.parseInt(id));

        if (temp != null) {
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(temp.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Schedule was not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> putSchedule(@PathVariable("id") String id, @RequestBody Schedule thermo) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        if (test.modifySchedule(thermo)) {
            MasterDAO.modifyScheduleOnMaster(thermo);
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(thermo.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Schedule was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addSchedule(@RequestBody Schedule thermo) {
        Pgdatabase test = MasterDAO.getDatabaseInstance();
        int id = test.createSchedule(thermo);

        if (id == -1) {
            return new ResponseEntity<String>("Problem creating the requested resource", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        thermo.setId(id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(thermo.getId())
        .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> deleteSchedule(@PathVariable("id") String id) { 
        Pgdatabase test = MasterDAO.getDatabaseInstance();

        List<Schedule> tList = test.getAllSchedules();

        for (Schedule temp : tList) {
            if (temp.getId() == Integer.parseInt(id)) {
                if (MasterDAO.removeScheduleFromMaster(Integer.parseInt(id))){
                    if (test.removeSchedule(Integer.parseInt(id))) {
                        return new ResponseEntity<String>("{'data':[]}", HttpStatus.NO_CONTENT);
                    } else {
                        return new ResponseEntity<String>("Problem Deleting the requested resource", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return new ResponseEntity<String>("Schedule is assigned to thermostat, remove assignment before deleting", HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<String>("Schedule was not found", HttpStatus.NOT_FOUND);
    }

}