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
import thermo.models.Setting;

@RestController
@RequestMapping(path ="/settings")
public class SettingsController {

    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <String> getSettings() {
        String response = "{'data':[";
        Pgdatabase test = new Pgdatabase();

        List<Setting> tList = test.getAllSettings();

        for (Setting temp : tList) {
            response += temp;
        }
        // Remove the comma from the last object converted to string
        response = StringUtils.chop(response);
        // End of payload
        response += "]}";
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> getSetting(@PathVariable("id") String id) { 
        Pgdatabase test = new Pgdatabase();
        Setting temp = test.getSettingById(Integer.parseInt(id));

        if (temp != null) {
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(temp.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Setting was not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> putSetting(@PathVariable("id") String id, @RequestBody Setting thermo) { 
        Pgdatabase test = new Pgdatabase();

        if (test.modifySetting(thermo)) {
            return new ResponseEntity<String>("{'data':[" + StringUtils.chop(thermo.toString()) + "]}", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Setting was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addSetting(@RequestBody Setting thermo) {
        Pgdatabase test = new Pgdatabase();
        int id = test.createSetting(thermo);

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
    public ResponseEntity<String> deleteSetting(@PathVariable("id") String id) { 
        Pgdatabase test = new Pgdatabase();

        List<Setting> tList = test.getAllSettings();

        for (Setting temp : tList) {
            if (temp.getId() == Integer.parseInt(id)) {
                if (test.removeThermostat(Integer.parseInt(id))) {
                    return new ResponseEntity<String>("{'data':[]}", HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<String>("Problem Deleting the requested resource", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<String>("Setting was not found", HttpStatus.NOT_FOUND);
    }

}