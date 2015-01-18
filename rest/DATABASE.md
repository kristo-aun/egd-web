# Database
This is a short description of the PostgreSQL database design and objects.
Comprehensive documentation about the function of each database object is in SQL comments.

## Setup
Create database with encoding ja_JP.UTF-8. For this you need to have Japanese locale installed in your computer.
The databse and all its objects are owned by egdclient.<br/>
```
CREATE DATABASE "egd"
  WITH OWNER "egdclient"
  ENCODING 'UTF8'
  LC_COLLATE = 'en_US.UTF-8'
  LC_CTYPE = 'en_US.UTF-8';
```


Backup<br/>
```
pg_dump -i -h localhost -p 5432 -U egdclient -F c -b -v -f "egd.backup" egd
```

Restore<br/>
```
pg_restore -i -h localhost -p 5432 -U egdclient -d egd -v "egd.backup"
```

For the string ordering to work custom collations are required. You need to have both Estonian and Japanese locales installed
 in your computer.<br/>
```
CREATE COLLATION japanese (LOCALE = 'ja_JP.utf8');
```

```
CREATE COLLATION estonian (LOCALE = 'et_EE.utf8');
```



There are altogether 10 schemas.

- **ac**: Access control. Controling access to API resources, enforcing policies and auditing usage. 
    
- **core**: Core10k, Core6k and ILO vocabulary lists. Core data is based on respective Anki decks. Audio is stored in DB as well.
ILO list contains all words from the yellow pocket dictionary. Each word has been normalized to account for its kanji usage.
The entire Core10k list uses 2191 kanjis, Core6k - 1446, ILO - 1555.
- **estwn**: TEKSaurus data schema reduced to just 3 classes: example->variant->word_meaning.
- **freq**: various frequency lists. Used in test generation module and for creating translation queue.
- **heisig**: Doth RTK4 and RTK6 lists complemented with top 2 Koohii stories and various other information.    
- **jmdict**: JP-ET dictionary.
- **jmdict_en**: JP-EN dictionary. This is being used in the article- and test generation module as JP-ET dictionary is not comprehensive enough.
- **kanjidic2**: all about Kanjis. Stroke diagrams are in public.image
- **public**: EsutoniaGoDesu specific data.
- **test**: serves data for article- and test generation module.


* Item 1
* Item 2
  * Item 2a
  * Item 2b



- **Design**: [Class diagram]

## JMDict gochas

JMDict identifiers have a bad habit of changing from time-to-time, meaning that links between different versions using entr.id is not possible.
If both English and Estonian translations of the same entr are needed, X should be used.
