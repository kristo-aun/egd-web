# JMDict knowhow
This guide goes through the process of getting JMDict data to PostgreSQL database.
We'll also see how to get JMAudio

Finally, we add supporting database objects to keep record of changes in data.


Some minor adjustments need to be made in to schema for the JPA domain model to work.

Composite keys are being used instead of sequences, which is a good practice, but complitates things at the webapp level.
This makes mapping views to domain classes complicated. This breaks entity search functions in hibernate for example.

Installing JMDictDB to

Ownership of the JMDict software belongs to Electronic Dictionary Research and Development Group,
Faculty of Information Technology, Monash University. The detail data copyright information are list as follows:
  - <a href="http://edrdg.org/~smg/">JMdictDB PostgreSQL documentation, schema design, etc</a>

## Environment
- **Ubuntu 14.04**: Everything should work in all Debian-based OS-s.
- **PostgreSQL 9.4**
- **Python 3**

## Updating JMDict
JMDict is part of the egd-db Postgres database. 
To serve both Estonian translations and features for various EsutoniaGoDesu's modules, 
the JMDict data is duplicated in jmen nad jmet database schemas. 
These schemas are not linked to other egd-db objects, thus can be replaced at any time.

*The JMdictDB project is an informal project to put the contents 
of Jim Breen's JMdict Japanese-English dictionary data  
into a database.*

### Install Python3
...

### Download source code, latest development version (tip.tar.gz) from [EDRDG](http://edrdg.org/~smg/)

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

### To load JMdict, JMnedict, and Examples on a Unix-like machine, run 
    
    make loadall

This will create a database named "jmnew", download
the needed XML files, then parse and load the JMdict, JMnedict,
and Examples files into it and recreate the necessary foreign
key constraints and indexes which were disabled during loading
for performance reasons.

### Export 

Prepare schema for export

    jmnew=# COMMENT ON SCHEMA jmen IS null;
    jmnew=# COMMENT ON EXTENSION plpgsql IS null;
    jmnew=# ALTER SCHEMA public RENAME TO jmen; 
    
Dump schema to an SQL file

    pg_dump --host=localhost --port=5432 --username=jmdictdb --format=plain --blobs --verbose --no-owner --no-privileges --file="jmen.sql" --dbname=jmnew


Prepare schema for export for a second language

    jmnew=# ALTER SCHEMA jmen RENAME TO jmet; 

Dump schema to an SQL file

    pg_dump --host=localhost --port=5432 --username=jmdictdb --format=plain --blobs --verbose --no-owner --no-privileges --file="jmet.sql" --dbname=jmnew


### Import

    pg_restore --host=localhost --port=5432 --username=egdrole --dbname=egd "jmen.sql" --single-transaction --verbose --exit-on-error

## Mapping CSV records to JMDict glosses

## Final points



You might want to delete all entries from gloss table.
You should keep a record of translation author, time and changes.

Comments about a particular translation have enormous value to users, but the design doesn't support adding contextual information.
Therefore, in EGD, metainfo is being kept in a dedicated table in another schema.
