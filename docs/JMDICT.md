# Using JMDict in EsutoniaGoDesu 
This guide shows how to install JMDict data into a PostgreSQL database.

Ownership of the JMDict software belongs to Electronic Dictionary Research and Development Group,
Faculty of Information Technology, Monash University. The detail data copyright information are list as follows:
  - <a href="http://edrdg.org/~smg/">JMdictDB PostgreSQL documentation, schema design, etc</a>

*The JMdictDB project is an informal project to put the contents 
of Jim Breen's JMdict Japanese-English dictionary data  
into a database.*

## Environment
- **Ubuntu 14.04**: all commands here should work in other GNU/Linux distros as well.
- **PostgreSQL 9.4**
- **Python 3.4.3**

## Installing a brand new JMDict database
JMDict is part of the egd-db Postgres database. 
To serve both Estonian translations and features for various EsutoniaGoDesu's modules, 
the JMDict data is duplicated in jmen nad jmet database schemas. 
These schemas are kept independent from other egd-db shcemas, thus can be easily replaced.

Make sure you have a full backup of your database!

### Install Python3

    sudo apt-get install python3.4

### Download JMDictDB source code, latest development version (tip.tar.gz) from [EDRDG](http://edrdg.org/~smg/)

    wget http://edrdg.org/~smg/cgi-bin/hgweb-jmdictdb.cgi/archive/tip.tar.gz
    
### Untar

    tar -xvf tip.tar.gz
    
### Create users jmdictdb and jmdictdbv
I create these users as superuser just so that they could create the necessary database objects. 
After we have exported the jmdict schema to a backup file, these users can be deleted.

    postgres=# CREATE USER jmdictdb WITH PASSWORD 'changeit' SUPERUSER;
    postgres=# CREATE USER jmdictdbv WITH PASSWORD 'changeit' SUPERUSER;

### Add new users to .pgpass (optional)

    echo "localhost:*:*:jmdictdb:changeit" >> ~/.pgpass
    echo "localhost:*:*:jmdictdbv:changeit" >> ~/.pgpass

### Load JMdict, JMnedict, and Examples 
    
    make loadall

This will create a database named "jmnew", download
the needed XML files, then parse and load the JMdict, JMnedict,
and Examples files into it and recreate the necessary foreign
key constraints and indexes which were disabled during loading
for performance reasons.

## Transfer newly imported data to a different database
I will export jmnew.public schema to an SQL file without owner info, which can be imported to other databases. 

### Prepare the schema for export

    jmnew=# COMMENT ON SCHEMA jmen IS null;
    jmnew=# COMMENT ON EXTENSION plpgsql IS null;
    jmnew=# ALTER SCHEMA public RENAME TO jmen; 
    
### Dump the schema to an SQL file

    pg_dump --host=localhost --port=5432 --username=jmdictdb --format=plain --blobs --verbose --no-owner --no-privileges --file="jmen.sql" --dbname=jmnew

### Drop the previous schema 

    DROP SCHEMA jmen;

### Import the schema

    psql --host=localhost --port=5432 --username=egdrole --dbname=egd --single-transaction --file "jmen.sql"
    
    
## Importing a JMDict with empty "gloss". You can translate this schema to your own language     
I'll delere all records from the table gloss.

I'm using "et" suffic to identify the second schema, but you can use any string you like.
 
### Prepare the schema for a second export

    jmnew=# ALTER SCHEMA jmen RENAME TO jmet; 
    jmnew=# TRUNCATE jmet.gloss;

### Dump the schema to an SQL file

    pg_dump --host=localhost --port=5432 --username=jmdictdb --format=plain --blobs --verbose --no-owner --no-privileges --file="jmet.sql" --dbname=jmnew

### Backup your old schema if necessary   

    ALTER SCHEMA jmet RENAME TO jmet_bak; 
    
### Import the new schema
    
    psql --host=localhost --port=5432 --username=egdrole --dbname=egd --single-transaction --file "jmet.sql"

## Finalize the installation

### Final points
- If the domain model has been changed, then ORM mapping must be updated as well.
- You should keep a record of translation author, time and changes.

- Comments about a particular translation have enormous value to users, but the design doesn't support adding contextual information.
Therefore, in EGD, metainfo is being kept in a dedicated table in another schema.

### ORM
You can find the Hibernate mapping in src/main/java/ee/esutoniagodesu/domain/jmen.
Composite keys are being used instead of sequences, which is a good practice, but complitates things at the persistence level.


## Mapping CSV records to JMDict glosses


#### Tips

Remove prefix from all files recursively
    
    for f in EN_*; do mv "$f" "${f#EN_}"; done
    
