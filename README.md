# Sidewalk Gallery
![image](https://user-images.githubusercontent.com/25534091/84452867-dec08c80-ac0b-11ea-8a90-ae8eeef03f01.png)

This is the main repo for Sidewalk Gallery. Above is a screenshot of the tool from 06/11/20.

# Development Notes
## Dependencies
The following dependencies are required to run this project:
* Docker (2.1.0.5)
* [Node.js](https://nodejs.org/) (version 10 or higher)
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (recommend version 1.8 or higher)
* [scala](https://www.scala-lang.org/download/)

We use Angular/Typescript for the frontend, Scala/Slick for the backend and PostgreSQL for our database.

## Development environment
The recommended development environment for Sidewalk Gallery is IntelliJ IDEA. Valentina Studio is nice for database administration.

## First-time Setup Instructions
Run `npm install` in the root directory to get dependencies.
```
SidewalkGallery$ npm install
```

### Frontend / Backend (Angular / Scala)
The front-end just requires us to run Scala. Everything in the `app`, `conf` and `ui` directories will automatically be updated upon the next reload. This command should be run in the root directory of the project. 
```
SidewalkGallery$ sbt run
```

### Database
To build and run the database:
```
docker build -t "sidewalkgallery_db" .
sudo docker run --name "postgis" -p 25432:5432 -d -t "sidewalkgallery_db"
```

To run the interactive terminal:
```
docker exec -it "postgis" bin/bash
```

To create an empty database
```
createdb gallery -T template0 -U docker -h localhost
```

To use the interactive `psql` command line tool for sidewalk gallery:
```
psql -h localhost -U docker -p 5432 [database name here]
```

