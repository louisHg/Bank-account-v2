# Bank account project

This project implements a bank account system.

- Business Logic: Implement core functionalities such as depositing money, withdrawing money, checking the current balance, and viewing transaction history.
- The aim is to isolate business logic from external dependencies, ensuring it is protected and independently testable.


## Table Of Content

- [Installation](#installation)
    - [Docker-compose](#Docker-compose)
    - [Run web interface](#Run-web-interface)
- [Code's working](#Code's-working)

## Installation

The installation is a necessary step before starting the running of java spring boot project and the quasar vuejs project.
In this step we will explain how can we starts the database, spring boot api and quasar vue project.

### Docker-compose

**Note:** Docker is required

First of all you need to run the docker-compose to build entirely the back project : 
-> Spring API = (http://localhost:8080/)
-> Quasar vue web app = (http://localhost:9000/)
-> PostgreSQL = (http://localhost:8278/)

```bash
docker-compose up -d 
```

### Run-web-interface

**Note:** Node and NPM is required

Go to the directory web-app, and install his dependency

```bash
npm install
```

When the node package installation is done you could run the project 

```bash
quasar dev
```

Here we are ! The project is now run 
Access to web app from http://localhost:9000/

More details about running the react project are given below
[Readme to launch quasar interface](./web-app/README.md)

### Code's-working

Now move on `.\bank-account-project` directory.

We can find `pom.xml`, in this file we could find the configuration of the springs projects.
The rest of the code are inplicits and all the configuration is in application.properties.
All service logic is tested.

About `.\web-app` directoy.
You could find in Vue quasar web app. Everything is based on components logics.



Enjoy the testing

