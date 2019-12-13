# Sidewalk Gallery Notes
## Dependencies
* Docker (2.1.0.5)
* [Node.js](https://nodejs.org/) (version 10 or higher)
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (recommend version 1.8 or higher)
* [scala](https://www.scala-lang.org/download/)

## Setup Instructions

### Frontend (Angular / Scala)
The front-end just requires us to run Scala. 
```
sbt run
```

### Backend
```
docker build -t "sidewalkgallery_db" .
sudo docker run --name "postgis" -p 25432:5432 -d -t "sidewalkgallery_db"
```

To run the interactive terminal:
```
docker exec -it "postgis" bin/bash
```

To create a database
```
createdb gallery -T template0 -U docker -h localhost
```

To get into `psql` for sidewalk gallery:
```
psql -h localhost -U docker -p 5432 [database name here]
```