<a href="https://twitter.com/EsutoniaGoDesu"><img src="https://pbs.twimg.com/profile_images/526708451062206464/qbT9Q4hE.png"/></a>

# About
EsutoniaGoDesu is a free Japanese-Estonian dictionary based on JMDict. 
In addition to traditional two-way translation, it includes features such as 

- a transcript module, which adds reading aids to articles such as vocabulary analyzer and audio;
- a kanji database linked with Heisig's RTK and stroke diagrams;
- a vocabulary test generator.

# Goals
The main focus in developing this dictionary and its complementing functionality has been 
to provide Estonian speakers with various study aids and accurate contextualized translations of Japanese words.

This webapp can be used with any JP-X language pair, but the development is not being done
keeping this in mind. If you wish to apply this software to another language pair you should be prepared
to make significant changes in the source code.

This dictionary hopefully adds some value to Japanese natives as it enables translating in Estonian -> Japanese direction.
However, in order to qualify as a professional ET-JP dictionary, it should be based on an Estonian language specific data structure.
Such a structure does not yet exist.
Recommendations on how to provide more value to Japanese speakers are welcome.

Hopefully this project will be an inspiration too for the emergence of Chinese-Estonian and Korean-Estonian dictionaries.

# Resources
- Live address: <a href="http://momo.koodur.com">momo.koodur.com</a>
- <a href="https://twitter.com/EsutoniaGoDesu">Twitter</a> (en)
- <a href="https://facebook.com/EsutoniaGoDesu">Facebook</a> (et, jp, en)

# Quick Start
Database schema is hosted in another project called [egd-db](https://github.com/esutoniagodesu/egd-db), but the data is not.
If you wish to have the full databse dump, please write at esutoniagodesu[at]gmail.com.
Full dump is too large to host on github and there are a few licensing constraints. 

Required: JDK8, [Maven3](docs/MAVEN3.md), [Node.js](docs/NODE.md), bower, grunt

Install kakasi.jar manually<br/>
```
mvn install:install-file -Dfile=lib/kakasi.jar -DgroupId=com.kawao.kakasi -DartifactId=kakasi -Dversion=0.4.0 -Dpackaging=jar
```


After DB setup, download dependencies and run in Spring Boot<br/>
```
mvn clean spring-boot:run
```

- **next**: open localhost:8443 in your browser


To compile into war<br/>
```
mvn clean package -Pprod -Dmaven.test.skip=true
```

# Technology Stack
This webapp was initially generated with [JHipster](http://jhipster.github.io/creating_an_entity.html).
There have been significant changes to the project structure and software modules, which means there will be complications if you try to
use the JHipster code generation tool.

### Version control
- **source**: [GIT](http://git-scm.com/)
- **database**: [liquibase](http://www.liquibase.org/)

### Back-end 
- **web/REST**: [Spring Boot](http://projects.spring.io/spring-boot/) 1.2.x, Spring Security, Spring Social
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.x (for JSON)
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) and [Hibernate](http://www.hibernate.org/) 4.1.x
- **persistence providers**: PostgreSQL 9.4, h2 (for testing)
- **file storage**: file sha256 based low-level storage architecture
- **TTS**: Google TTS, [Kõnesüntees](http://heli.eki.ee/koduleht/)

### Business logic
- **Japanese morphological analysis**: [Kuromoji] (http://www.atilika.org/)
- **Kanji/Kana/Romaji conversion**: [Kakasi] (https://github.com/nicolas-raoul/kakasi-java)
- **Spreadsheets & PDF**: [JasperReports] (https://community.jaspersoft.com/project/jasperreports-library)

### Front-end
- **Frameworks**: [AngularJS](https://angularjs.org/) 1.4.x, [JQuery](http://jquery.com),
[JQuery-UI](http://jqueryui.com/), <br/>
- **Design/CSS**: [Bootstrap](http://getbootstrap.com/), Angular-UI

### Testing
[JUnit](http://www.junit.org/),
[hamcrest](http://code.google.com/p/hamcrest/),
[mockito](http://code.google.com/p/mockito/), 
[rest-assured](http://code.google.com/p/rest-assured/) <br/>

## Hosting
- **Web container**: [Tomcat 8.0](http://tomcat.apache.org/tomcat-8.0-doc/index.html), [Tomcat setup guide](docs/TOMCAT.md)

# Data sources
## JMDict/EDICT
A major part of the database was taken from Electronic Dictionary Research and Development Group,
Faculty of Information Technology, Monash University. The detail data copyright information are list as follows:

- <a href="http://www.edrdg.org/jmdict/edict_doc.html">Professor Jim Breen's JMdict/EDICT project homepage</a>
- <a href="http://edrdg.org/~smg/">JMdictDB PostgreSQL documentation, schema design, etc</a>

## Translations
- **Estonian**: [JATIK](http://web.zone.ee/jatik/),
[ILO Jaapani-Eesti taskusõnastik](https://kirjastus.tea.ee/est/Tootekataloog/sonaraamatud/?productID=1603),
*[EKI lemma list](http://www.eki.ee/tarkvara/wordlist/)*

## Sentences
- **Japanese**: [Core 2k/6k/10k](https://ankiweb.net/shared/decks/japanese),
[Tanaka corpus](http://www.edrdg.org/wiki/index.php/Tanaka_Corpus),
[Elli Feldberg's "Jaapani keele grammatika"](http://www.rahvaraamat.ee/p/jaapani-keele-grammatika/25242/et?isbn=9789949170593)
- **Estonian**: [TEKSaurus](http://www.cl.ut.ee/ressursid/teksaurus/index.php?lang=en),
[Estonian Reference Corpus](http://www.cl.ut.ee/korpused/segakorpus/)

## Kanji
- **Base**: [Kanjidic2](http://www.csse.monash.edu.au/~jwb/kanjidic.html)
- **Study lists**: [James Heisig's Remembering the Kanji](http://en.wikipedia.org/wiki/Remembering_the_Kanji_and_Remembering_the_Hanzi),
[Jinmei](http://en.wikipedia.org/wiki/Jinmeiy%C5%8D_kanji),
[JLPT](http://www.jlptstudy.net/),
[Jōyō](http://en.wikipedia.org/wiki/J%C5%8Dy%C5%8D_kanji),
[Kyōiku](http://en.wikipedia.org/wiki/Ky%C5%8Diku_kanji)
- **Stroke diagrams**: [KanjiVG](http://kanjivg.tagaini.net/)

## Language tests
- **Compounds**: 
[ILO Jaapani-Eesti taskusõnastik](https://kirjastus.tea.ee/est/Tootekataloog/sonaraamatud/?productID=1603),
[Core 2k/6k/10k](https://ankiweb.net/shared/decks/japanese)
- **Study materials**: [Free audiobooks](http://forum.koohii.com/viewtopic.php?id=804)

# Documentation
## User manual

## API documentation
- **Javadoc**: [Javadoc]

# Licensing
GNUv2. Further information about copying this software is [here](COPYING.md).
