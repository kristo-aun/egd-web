<a href="https://twitter.com/EsutoniaGoDesu"><img src="https://pbs.twimg.com/profile_images/526708451062206464/qbT9Q4hE.png"/></a>

# About
EsutoniaGoDesu is a free Japanese-Estonian dictionary based on JMDict. 
Besides traditional two-way translation capabilities, it includes additional features such as 

- a transcripting module, which provides various reading aids to articles such as vocabulary analyzer and audio;
- a kanji database linked with Heisig's RTK and stroke diagrams;
- a vocabulary test generator.

Advanced users can apply for access to edit the dictionary.

# Goals
The main focus in developing this dictionary and its complementing functionality has been 
to provide Estonian speakers with various study aids and accurate contextualized translations of Japanese words.

Presumably this solution can be applied to any JP-X language pair, but the development has not been done
keeping in mind such generalization. If you wish to apply this software to another language pair you should be prepared
to make significant changes in the source code.


This dictionary has some value to Japanese speakers as it enables translating in Estonian -> Japanese direction.
Switching to Japanese mode replaces Japanese study aids to those of Estonian. For instance, example sentences in Estonian appear instead of Japanese and 
translations are complemented with links to ÕS.
However, in order to qualify as a professional ET-JP dictionary, it should be based on an Estonian language specific data structure.
It is our belief that such a structure should be developed in joint collaboration of EKI and heavy-weight Japanese linguists.
Recommendations on how to provide more value to Japanese speakers are welcome.

Hopefully this project will be an inspiration for the emergence of heavyweight Chinese-Estonian and Korean-Estonian dictionaries.

# Resources
- Live address <a href="https://momo.koodur.com">momo.koodur.com</a>
- Aternative address (<a href="http://EsutoniaGoDesu.ddns.net/">EsutoniaGoDesu.ddns.net/</a>, which redirects port 80 to <a href="213.168.13.40:54680">213.168.13.40:54680</a>
This address is managed by noip.com and has to be updated once a month as as we don't have a paid subscription there.

- <a href="https://twitter.com/EsutoniaGoDesu">Twitter</a>
- <a href="https://facebook.com/EsutoniaGoDesu">Facebook</a>

- Docs: [Main wiki](https://github.com/esutoniagodesu/REST/wiki)
- Technical forum: [rest-security google group](https://groups.google.com/forum/#!forum/restsec)

# JMDict/EDICT
- <a href="http://www.edrdg.org/jmdict/edict_doc.html">JMdict/EDICT project homepage</a>
- <a href="http://edrdg.org/~smg/">JMdictDB PostgreSQL documentation, schema design, etc</a>

# Quick Start
Database schema is hosted in another project called egd-db, but the data is not. 
If you wish to have the full databse dump, please write at esutoniagodesu[at]gmail.com.
Full dump is too large to host on github and there are some licensing constraints. 

Required: jkd8, maven3
node
bower
grunt


```
git clone git://github.com/esutoniagodesu/webapp.git
```
<br/><br/>Download dependencies via bower and maven<br/>
```
mvn clean package -Pprod -Dmaven.test.skip=true
```

```
mvn spring-boot:run
```

- **next**: open localhost:8080 in your browser



# Technology Stack
The project uses these following technologies: <br/>
The webapp was initially generated using [JHipster](http://jhipster.github.io/creating_an_entity.html).
Significant changes to the project structure and software modules were made, which means there will be complications if you try to
use yeoman code generation.

### Version control
- **source**: [GIT](http://git-scm.com/)
- **database**: [liquibase](http://www.liquibase.org/)

### Back-end 
- **web/REST**: [Spring Boot](http://projects.spring.io/spring-boot/) 1.2.x
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.x (for JSON) and [XStream](http://xstream.codehaus.org/) (for XML)
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) and [Hibernate](http://www.hibernate.org/) 4.1.x
- **persistence providers**: PostgreSQL 9.3, h2 (for testing)
- **TTS**: [Google TTS API]

### Business logic
- **Japanese morphological analysis**: [Kuromoji] (http://www.atilika.org/)
- **Kanji/Kana/Romaji conversion**: [Kakasi] (https://github.com/nicolas-raoul/kakasi-java)
- **Spreadsheet provider**: [JasperReports] (https://community.jaspersoft.com/project/jasperreports-library)

### Front-end
- **Frameworks**: [AngularJS](https://angularjs.org/) 1.2.x, [JQuery](http://jquery.com)<br/>
- **RESTful service client**: [Swagger](https://github.com/swagger-api/swagger-ui)<br/>
- **Design/CSS**: [Bootstrap](http://getbootstrap.com/)

### Testing
[junit](http://www.junit.org/), 
[hamcrest](http://code.google.com/p/hamcrest/),
[mockito](http://code.google.com/p/mockito/), 
[rest-assured](http://code.google.com/p/rest-assured/) <br/>

## Hosting
- **Web container**: [Tomcat](http://tomcat.apache.org/tomcat-8.0-doc/index.html) 8.0
- **Monitoring**: Cacti, PSI Probe

# Data sources
## Dictionary
- **Japanese**: [JMDictDB](http://edrdg.org/~smg/)
- **Estonian**: [JATIK](http://web.zone.ee/jatik/),
[EKI lemma list](http://www.eki.ee/tarkvara/wordlist/)

## Sentences
- **Japanese**: [Core 2k/6k/10k](https://ankiweb.net/shared/decks/japanese),
[Tanaka corpus](http://www.edrdg.org/wiki/index.php/Tanaka_Corpus),
[E. Feldberg's "Jaapani keele grammatika"](http://www.rahvaraamat.ee/p/jaapani-keele-grammatika/25242/et?isbn=9789949170593)
- **Estonian**: [TEKSaurus](http://www.cl.ut.ee/ressursid/teksaurus/index.php?lang=en),
[Estonian Reference Corpus](http://www.cl.ut.ee/korpused/segakorpus/)

## Kanji
- **Base**: [Kanjidic2](http://www.csse.monash.edu.au/~jwb/kanjidic.html)
- **Study Lists**: [James Heisig's Remembering the Kanji](http://en.wikipedia.org/wiki/Remembering_the_Kanji_and_Remembering_the_Hanzi),
[Jinmei](http://en.wikipedia.org/wiki/Jinmeiy%C5%8D_kanji),
[JLPT](http://www.jlptstudy.net/),
[Jōyō](http://en.wikipedia.org/wiki/J%C5%8Dy%C5%8D_kanji),
[Kyōiku](http://en.wikipedia.org/wiki/Ky%C5%8Diku_kanji)
- **Stroke diagrams**: [KanjiVG](http://kanjivg.tagaini.net/)

## Tests
- **Compounds**: 
[ILO Jaapani-Eesti taskusõnastik](https://kirjastus.tea.ee/est/Tootekataloog/sonaraamatud/?productID=1603),
[Core 2k/6k/10k](https://ankiweb.net/shared/decks/japanese)
- **Articles**: [Free audiobooks](http://forum.koohii.com/viewtopic.php?id=804)

# Documentation
## User manual

## API documentation
- **Javadoc**: [Javadoc]

## Database
- **Design**: [Class diagram]

# Licensing

# Community
- [Discussion Group/Mailing List](https://groups.google.com/forum/#!forum/restsec)
