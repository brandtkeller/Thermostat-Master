# Thermostat Main Display Unit

Main backend server for the Open Thermostat integration system

## TO DO
* Schedule controller
* Setting controller
* Enrollment controller

## Updates
This repository is a mirror. I host a private git server and CI/CD server that is currently active for all feature branches.
Activity there may be more recent as updates are only pushed to github during merge to master.

If you would like more information on updates, please reach out to:
```
Info@brandtkeller.net
```

## Standard Communication

The main server exists on a single device (Raspberry Pi) and contains RESTful interfacing, scheduling, and physical control of the HVAC unit via C-wire. As this device is usually located outside of the monitored region (garage or other closet) it relies on temperature nodes to enroll and simply await for a request of current temperature. 

This architecture will allow multiple nodes to enroll and enhance the temperature view of the house as a whole.

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
java -jar ./target/thermo-mdu-0.0.1.jar
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
