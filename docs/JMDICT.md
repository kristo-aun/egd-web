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
- **Ubuntu 12.04**: Everything should work in all Debian-based OS-s.
- **PostgreSQL 9.3**
- **Python 3**


Download source code, latest development version (tip.tar.gz) from [EDRDG](http://edrdg.org/~smg/)
====
    wget http://edrdg.org/~smg/cgi-bin/hgweb-jmdictdb.cgi/archive/tip.tar.gz
    
Untar archive
====
    tar -xvf tip.tar.gz
    
If you don't have Python3 installed, here is how to do this
====
    

## Updating JMDictDB
there are scripts for that

## Mapping CSV records to JMDict glosses

## Final points



You might want to delete all entries from gloss table.
You should keep a record of translation author, time and changes.

Comments about a particular translation have enormous value to users, but the design doesn't support adding contextual information.
Therefore, in EGD, metainfo is being kept in a dedicated table in another schema.
