package thermo.controllers;

// import java.net.URI;
// import java.util.ArrayList;

// import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import thermo.Thermoshare;
import thermo.models.Thermostat;

@RestController
@RequestMapping(path ="/thermostats")
public class ThermostatController {
    Thermostat thermo = Thermoshare.getInstance();


    @GetMapping(path="", produces = "application/json")
    public ResponseEntity <String> getThermostats() {
        return new ResponseEntity<String>("returning the thermostat objects here" + thermo.getCurrentTemp(), HttpStatus.OK);
    }

    // @GetMapping(path="/{id}", produces = "application/json")
    // public ResponseEntity<Object> getEmployee(@PathVariable("id") String id) { 
    //     Gson gson = new Gson();
    //     for (int i = 0; i < employeeDao.getAllEmployees().getEmployeeList().size(); i++) {
    //         Employee emp = employeeDao.getAllEmployees().getEmployeeList().get(i);
    //         if (Integer.parseInt(id) == emp.getId()) {
    //             return new ResponseEntity<>(gson.toJson(emp), HttpStatus.OK);
    //         }
    //     }
    //     return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    // }  

    // @PutMapping(path="/{id}", produces = "application/json")
    // public ResponseEntity<Object> putEmployee(@PathVariable("id") String id, @RequestBody Employee employee) { 
    //     for (int i = 0; i < employeeDao.getAllEmployees().getEmployeeList().size(); i++) {
    //         Employee emp = employeeDao.getAllEmployees().getEmployeeList().get(i);
    //         if (Integer.parseInt(id) == emp.getId()) {
    //             employee.setId(Integer.parseInt(id));
    //             employeeDao.updateEmployee(i, employee);
    //             return new ResponseEntity<>("Employee has been updated successfully", HttpStatus.OK);
    //         }
    //     }
    //     return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    // }  

    // @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {

    //     employee.setId(this.currentInt);
    //     currentInt++;

    //     employeeDao.addEmployee(employee);

    //     URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    //     .path("/{id}")
    //     .buildAndExpand(employee.getId())
    //     .toUri();

    //     return ResponseEntity.created(location).build();
    // }

    // @DeleteMapping(path="/{id}", produces = "application/json")
    // public ResponseEntity<Object> deleteEmployee(@PathVariable("id") String id) { 
    //     for (int i = 0; i < employeeDao.getAllEmployees().getEmployeeList().size(); i++) {
    //         Employee emp = employeeDao.getAllEmployees().getEmployeeList().get(i);
    //         if (Integer.parseInt(id) == emp.getId()) {
    //             employeeDao.removeEmployee(i);
    //             return new ResponseEntity<>("Employee has been deleted successfully", HttpStatus.OK);
    //         }
    //     }
    //     return new ResponseEntity<>("Employee was not found", HttpStatus.NOT_FOUND);
    // }  
}