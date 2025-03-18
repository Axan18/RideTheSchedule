# RideTheSchedule
RideTheSchedule is web application offering possibilities of managing schedules, and sharing it with your friends. After signing in, user can
create his schedule for any day, add other users to friends list and share them his schedule for the sake of improved
collaboration. To sign in Google Oauth2 is used, and then JWT is created to be used for authentication. Communication is done with HTTPS, as it is necessary for sending JWT in cookies. SpringBoot & Hibernate handle RESTful backend services and connectivity with database. On the frontend [FullCalendar](https://github.com/fullcalendar/fullcalendar) framework is used to provide nicely looking schedule UI. Dynamic size of the page is secured by [BootStrap](https://github.com/twbs/bootstrap) framework. Frontend is really simple with just basic use of FetchAPI to get data from backend.

The goal of the app is to show (and train) my backend development skills, especially in Java and Spring ecosystem.

## Table of contents
* [Technologies](#technologies)
* [Setup](#setup)
* [Future updates](#future-updates)

## How it looks like?
#### Welcoming screen
![obraz](https://github.com/user-attachments/assets/ba9c1751-c777-4b12-aacc-9d59e9e0a92a)

#### Home screen
![obraz](https://github.com/user-attachments/assets/c242a379-a4f9-41a5-8eb6-5baf7134a0be)

#### Empty schedule & forms
![obraz](https://github.com/user-attachments/assets/bbc0bca9-73e7-4be6-92b1-fa645cd259c5)

#### Shared schedule view
![obraz](https://github.com/user-attachments/assets/bbf8a9e8-643b-4807-85ae-73ec65c572a6)



## Technologies
* Backend
  * Java
  * SpringBoot
  * Hibernate
  * PostgreSQL
  * JUnit
  * Mockito
  * Maven
* Frontend
  * HTML
  * CSS
  * JavaScript
  * [BootStrap](https://github.com/twbs/bootstrap)
  * [FullCalendar](https://github.com/fullcalendar/fullcalendar) 

## Setup
### Frontend 
 
As HTTPS certificate is necessary to send JWT in cookies, we have to generate local certificate:

 Install mkcert if you don't have it:
    [Check instructions here](https://github.com/FiloSottile/mkcert)

  Now lets generate certificate:
>
    mkcert -key-file localhost-key.pem -cert-file localhost.pem localhost
  Go into frontend directory:
>
    cd frontend/
  Install dependencies:

    npm -install
  Run HTTPS server:
>
    npx http-server -S -C localhost.pem -K localhost-key.pem
***
### Backend

  Create your PostgreSQL database:
  >
    CREATE DATABASE db_name;

  Hibernate will manage creating tables.
  Set correct schema_name in appilaction-postgresql.properties
  >
    spring.jpa.properties.hibernate.default_schema=schema_name

  To fill application properties you have to have .env file with such values set:
  >
    DB_USERNAME=
    DB_PASSWORD=
    DB_URL=
    GOOGLE_CLIENT_ID=
    GOOGLE_CLIENT_SECRET=
    JWT_SECRET=
    HTTPS_PSWD=

  Build the .jar file:
  >
    mvn clean install -DskipTests

  Run application:
  >
    java -jar target/RideTheSchedule-1.0.0-SNAPSHOT.jar --spring.profiles.active=postgresql
    
    
    

## Future updates
As the app is in constant development phase, features will be added:
* todo list functionalities
* more features to interact with other users like comments to events
* friends management improvement & profile page
* better security and error handling
* Flyway migrations
* more backend tests
* deployment to open access in the internet
* own Spring Authorization Server
