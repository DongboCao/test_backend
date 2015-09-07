# candidate-back
--------------
## Prerequisites
### Tools
You need to have installed Oracle JDK 1.8
Gradle is used for building the project but since gradle-wrapper is included you don't need to install anything else.  
### Building and running
To start you need to verify if `candidate-back` project builds.
To do this you need to launch : `./gradlew clean build` from root.  
Project consists of 2 simple gradle modules : candidate-service and candidate-persistence. 

If you count on implementing front-end as well please read `../candidate-ui/README.md` as well from candidate-ui where you'll find correspondance between UI and BACK steps.
### Back-end modules
### candidate-persistence
Contains DB config (H2 in-memory) and Spring data entities and repositories.
### candidate-service
Consists of controllers and service business logic.   
Module is packaged as a fat jar with all dependencies (with embedded Tomcat server) using Spring Boot.  
There are several ways to launch the application:  
Via gradle : 
* `./gradlew :candidate-service:bootRun` (builds and runs )  

Via main class:
* launch main class : `CandidateServiceApp.class` 

Via produced jar :
* `java -jar candidate-service/build/libs/candidate-service-1.0-SNAPSHOT.jar`
#### application.yml
Default server port can be found there  and changed if needed.


## EXERCICE
You need to expose simple  CRUD REST api to you clients to be able to manage candidates.
Don't forget to check global remarks section at the end before starting working at your exercice!
### Step 1: Create an endpoint to get the list of candidates
* Map missing entity Candidate (see `candidate-db-schema.sql`) 
* Add CandidateRepository
* Create a service to use the repository
* Create a new controller which exposes  `GET /candidate`. This endpoint should return a list of candidates.

### Step 2: Add a new endpoint to modify existing candidate
* Add a new endpoint : `PUT /candidate/{id} requestBody: {name:'john',enable:true/false}` 
* Modify candidate in the DB 

### Step 3: Create an endpoint to add a new candidate
* Add a new endpoint : `POST /candidate requestBody : {name:'john',enable:true/false}`
* Add candidate in the DB

### Step 4: Create an endpoint to delete candidates by ids
* Add a new endpoint : `POST /candidate/delete requestBody : {ids : [1,6]}` 
* Delete candidates from DB
### Step 5 : BONUS
Imagine an application  `intake-generation-service` which is notified for every new candidate added  via `candidate-service` and generates an intake test exervice for him.
Explain how would you technically organize communication/event propagation from `candidate-service` to this new module taking into account that `intake-generation` could be deployed on different server.  
If you have time to implement your idea it  would be a big BONUS(!) (just communication part, what `intake-generation` does when receives information on a new candidate is not important!)

## General Remarks
Usage of Java 8 features as optionals, streams are more then welcomed.  
There are a couple of empty classes with TODO's created for you to help starting with exercice but not everything is covered. Feel free to create whatever you need!
### Dependencies
* In dependencies (build.gradle files) you can find indication on librairies which can be used to help wih implementation, testing etc.
* Feel free to add whatever you need
### Exception handling
Events like entity not found (f.i : GET by id) should generate exceptions and should be propagaded to HTTP level via corresponding codes : 
* 404 : for not found 
* 500 : for generic exceptions you would like to handle
* 400 : for invalid request input (PUT and POST)  with indication on what is missing/not correct.  
For instance post of a new candidate without name would give:
`{
    "type": "InvalidRequestException",
    "message": "Invalid NewCandidate",
    "fieldErrors": [
        {
            "resource": "newCandidate",
            "field": "name",
            "code": "NotNull",
            "rejectedValue": "null"
        }
    ]
}`  
Obviously we need a generic way to handle those. You can create as many types of exceptions as you need but beforementionned cases should be handled.

### DTO's
* Mapped entities shouldn't be returned from Controller layer, think of using DTO's
* all data which comes as request body should be validated (check DB schema to have an idea of what you need to check).
