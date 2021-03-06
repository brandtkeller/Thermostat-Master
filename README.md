# Thermostat Main Display Unit

Main backend server for the Open Thermostat integration system

## Update Notice
This repository is a mirror. I host a private git server and CI/CD server that is currently active for all feature branches.
Activity there may be more recent as updates are only pushed to github during merge to master.

If you would like more information on updates, please reach out to:
```
Info@brandtkeller.net
```

## TO DO
* In-place updates
    * POST /Schedules should create a schedule and all 7 settings
    * schedule object should initialize a schedule with settings
    * PUT /Settings/{id} should create schedule object and call MasterDAO.ModifyScheduleOnMaster(Schedule temp)
    * Move that logic from thermostat constructor to schedule constructor 
* Modify thermostat model to include locality and address
* Delete Schedule should delete assigned settings
* Database error handling - IE do not allow removing schedule if assigned to a thermostat
* Raspberry Pi GPIO integration
* Automation Hub Backend
* Automation Hub Frontend
* Multi-thermostat system
    * Have a separate jar for remote relay operation
    * all secondary thermostats are stateless
* ARM architecture docker images
* Automatic database cleanup
* State lock on thermostat object main logic
* Docker -> Postgres connection docker environment variables

## Possible Capabilities
* In-place updates for the thermostat objects
    * This includes schedules and settings
* Instant change
    * If we toggle a setting on the UI, have the change reflected instantly rather than during the next schedule loop
    * How does this logic work beside initial startup/ restart logic?


## Standard Communication

The main server exists on a single device (Raspberry Pi) and contains RESTful interfacing, scheduling, and physical control of the HVAC unit via C-wire. As this device is usually located outside of the monitored region (garage or other closet) it relies on temperature nodes to enroll and simply await for a request of current temperature. 

This architecture will allow multiple nodes to enroll and enhance the temperature bias from that of a single sensor to a system with modifiable biases. 

Example: During the day-time, individual rooms are less populated while main living spaces (kitchen, living room) are subject to regular sustainment. In this case, biasing the system to refine control of the temperature to these spaces to sustain a comfortable temperature would make the most sense.

## Mutli-HVAC / Thermostat Configuration

The system will be built to handle a mutli-hvac building. There will only be one Master Node with an API and database connection active. The 'Type' selector of the node table will identify an enrolled node as either 'control','airtemp','watertemp'. 

## Start-up Logic - First Start
* Master instance initialized
    * Database instance Initialized
        * Connects to database and creates Master Thermostat, a Schedule , and the Schedule Settings.
    * Assign all (one) thermostats to Master Instance

* Start REST API

* Begin main runtime loop
    * Master.executeThermostatCheck()
        * For each thermostat
            * 
## Runtime Logic

## Main logic loop - Scaling

* Master startup and db initialization
* Each Thermostat object contains full run-time logic
* Skip main loop until temp sensor node enrolled or if mode == 'off' (default)
* Nodes will auto-enroll, UI required to assign to a thermostat object
* Main Application loop per each thermostat
    * GET request to temp sensor nodes assigned to thermostat object (thermostat.nodes.forEach())
    * Calculate some mean temperature value
        * Get temperature of all nodes assigned to thermostat?
    * Compare temperature against thermostat -> currentSettingTemp (thermostat.getCurrentSettingTemp())
    * if outside threshold && state == off , activate relays based on locality and mode
        * If thermostat locality == local then activate relays locally
        * If thermostat locality == remote then send relay activation to remote thermostat
    * if inside threshold && state == on, de-activate relays based on locality and mode

## Getting Started

These instructions will get you a .jar package that can be transferred from a Development machine or built on a Raspberry Pi. 

### Prerequisites

Necessary items to compile and run the server

```
JDK 1.8 or later
Maven 3.2+
Docker (when containerizing)
```

### Installing

A step by step series of examples that tell you how to get a development env running

Compile

```
mvn compile
```

Package

```
mvn package
```

Run

```
java -jar -Ddb_url=<postgresurl:port/db> -Ddb_user=<db user> -Ddb_pass=<db password>  target/thermo-master-0.0.1.jar
```

The server will now be running on port 8080.
See below for running command line manual testing.

## Manual testing

Testing the server via command line

### REST test examples


## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
