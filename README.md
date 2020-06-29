# Sidewalk Gallery
![image](https://user-images.githubusercontent.com/25534091/84452867-dec08c80-ac0b-11ea-8a90-ae8eeef03f01.png)

This is the main repo for Sidewalk Gallery. Above is a screenshot of the tool from 06/11/20.

# Development Notes
## Dependencies
The following dependencies are required to run this project:
* Docker (2.1.0.5)
* [Node.js](https://nodejs.org/) (version 10 or higher)
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  (recommend version 1.8 or higher)
* [scala](https://www.scala-lang.org/download/)

We use Angular/Typescript for the frontend, Scala/Slick for the backend and PostgreSQL for our database.

## Development environment
The recommended development environment for Sidewalk Gallery is IntelliJ IDEA.
Valentina Studio is nice for database administration.

## First-time Setup Instructions
0. You will need to get the following things from Mikey (send him an email!)
    * Core Sidewalk database dump (it's recommended you get the smallest dump
      possible -- there's more than enough data collected for dev purposes).
      You should create a `db` folder in the home directory, and name the
      database `sidewalk-seattle-dump`.
    * Google Maps API key. This should be saved in the project's home directory.

1. Run `npm install` in the project's home directory to get dependencies for the
   front-end components.
```
SidewalkGallery$ npm install
```

2. Run the following `docker` commands. This downloads the dependencies for the
   database and will start up the container with your database in it.

```
SidewalkGallery$ docker build -t "sidewalkgallery_db" .
SidewalkGallery$ docker start postgis
SidewalkGallery$ sudo docker run --name "postgis" -p 25432:5432 -d -t "sidewalkgallery_db"
```

3. Check that your container is running properly. Run `docker ps`. You should
   get something that looks like this:

```
Aileens-MacBook-Pro:~ aileen$ docker ps
CONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS              PORTS                     NAMES
<container id>      sidewalkgallery_db   "/bin/sh -c /docker-…"   5 months ago        Up 4 weeks          0.0.0.0:25432->5432/tcp   postgis
```

4. Open up an interactive docker terminal with this `docker` command.
```
SidewalkGallery$ docker exec -it "postgis" bin/bash
```

5. Create the gallery database.
```
createdb gallery -T template0 -U docker -h localhost

```

6. Run the following commands to import the Sidewalk database:
```
root@: $ su - postgres
postgres@: $ psql
postgres=# \du
postgres=# CREATE ROLE sidewalk SUPERUSER LOGIN ENCRYPTED PASSWORD 'anything';
postgres=# CREATE ROLE sidewalk_seattle SUPERUSER LOGIN ENCRYPTED PASSWORD 'anything';
postgres=# CREATE ROLE saugstad SUPERUSER LOGIN ENCRYPTED PASSWORD 'anything';
postgres=# exit
root@: createdb gallery -T template0 -U docker -h localhost
root@: pg_restore -d sidewalk-seattle db/sidewalk-seattle-dump -U docker -h localhost
```

This restores the Sidewalk Seattle database. You won't be working with this
database directly, but you will want to copy over some tables into the gallery
database.

6. Copy tables from the Sidewalk database into the gallery database.
```
postgres@: pg_dump -t sidewalk.label_type sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.tag sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.validation_options sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.label sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.label_description sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.label_point sidewalk-seattle | psql gallery;
postgres@: pg_dump -t sidewalk.label_severity sidewalk-seattle | psql gallery;
```

7. Exit the interactive terminals. You'll need to type `exit` twice. (The first
   time is to get out of `psql`, the second time is to get out of `docker`).

8. Run `sbt run`. This command will take a while to run originally because it
   needs to download several Scala dependencies. In the future, this is the only
   command that you will need to run.

## Running the Project
The front-end just requires us to run Scala. Everything in the `app`, `conf` and 
ui` directories will automatically be updated upon the next reload. This command
should be run in the home directory of the project.

```
SidewalkGallery$ sbt run
```

## Useful Database commands

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

## Known errors

###  Adding the Postgis extension to gallery database
Sometimes there will be an error that looks like this:
```
ERROR:  type "public.geometry" does not exist
LINE 17:     geom public.geometry
```

This means that the postgis extension hasn't been enabled. These are the 
commands that you can run to create the extensions.

```
aileenz $ docker exec -it postgis /bin/bash
root@: $ su - postgres
postgres@:~$ psql -h localhost -U docker gallery         
Password for user docker: 
psql (11.6 (Debian 11.6-1.pgdg100+1))
SSL connection (protocol: TLSv1.3, cipher: TLS_AES_256_GCM_SHA384, bits: 256, compression: off)
Type "help" for help.

gallery=# \dx
                 List of installed extensions
  Name   | Version |   Schema   |         Description          
---------+---------+------------+------------------------------
 plpgsql | 1.0     | pg_catalog | PL/pgSQL procedural language
(1 row)

gallery=# CREATE EXTENSION postgis; 
CREATE EXTENSION
```