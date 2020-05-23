# Thermostat Main Display Unit

Main backend server for the Open Thermostat integration system

## TO DO
* Schedule controller
* Setting controller
* Enrollment controller

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
