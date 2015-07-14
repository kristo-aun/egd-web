package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.repository.domain.library.ReadingRepository;
import ee.esutoniagodesu.repository.project.LibraryDB;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Lugemisharjutused. Artiklite moodulis saab hallata täispikki Jaapanikeelseid algtekste.
 * Tekst tükeldatakse 3-5 lauselisteks lõikudeks. Igale lõigule saab lisada heli.
 * Algajale on Jaapanikeelsete tekstide lugemine ilma abivahenditeta erakordselt tülikas.
 * Võib öelda, et ilma vähemalt 1500-t kanjit tundmata ei ole võimalik lugeda.
 * Seejuures ei ole lugemisoskus lineaarses seoses kanjide oskusega.
 * Tekstide moodulisse on koondatud erinevad abivahendid, et lugemist lihtsustada.
 * <p/>
 * TODO
 * kasutaja roll ei tohiks näha artiklite admin andmeid
 */
@Service
@Transactional
public class ReadingService {

    private static final Logger log = LoggerFactory.getLogger(ReadingService.class);

    @Inject
    private JasperService jasperService;

    @Inject
    private ProjectDAO dao;

    @Inject
    private ReadingRepository readingRepository;

    @Inject
    private LibraryDB libraryDB;

    @Inject
    private KuromojiService kuromojiService;

    @Inject
    private SHAFileService shaFileService;

    //------------------------------ artiklite vaade ------------------------------

    /**
     * Päises 3 tekstivälja: tiitel, autor, copyright, tõlke keel. Teksti sisu alas lisatakse lõike,
     * mille pikkus ei tohiks ületada 4-5 lauset. Lõigul on tõlge. Tõlge peaks olema kõrge keelelise kvaliteediga.
     * Lõigul võib olla heli. Helilõiku saab lisada ja eemaldada. Lõikude järjekorda saab muuta üles-alla.
     */

    /**
     * Lubatud on muuta ainult enda loodud artikleid.
     * Administraatoril on lubatud kõiki muuta.
     */
    @PreAuthorize("hasPermission(#reading.id, 'ee.esutoniagodesu.domain.library.table.Reading', 'reading_update')")
    public Reading update(Reading reading) throws IOException {
        log.debug("update: reading=" + reading);
        return save(reading);
    }

    @PreAuthorize("hasPermission(#reading, 'reading_create')")
    public Reading create(Reading reading) throws IOException {
        log.debug("create: reading=" + reading);
        return save(reading);
    }

    private Reading save(Reading reading) throws IOException {
        if (reading.getAudioFile() != null) {
            log.debug("save to shafs {}", reading.getAudioFile().getOriginalFilename());
            String sha = shaFileService.put(reading.getAudioFile());
            reading.setAudioSha(sha);
        }
        return dao.save(reading);
    }

    private String uuid() {
        return SecurityUtils.getUserUuid();
    }

    /**
     * Kasutajale näidatakse tema enda loodud ja avalikke artikleid. Pagineeritud.
     */
    public Page<Reading> getReadings(Integer page, Integer limit) {
        return readingRepository.findAvailable(uuid(), PaginationUtil.generatePageRequest(page, limit));
    }


    public Page<Reading> findByTag(String tag, Integer page, Integer limit) {
        return readingRepository.findByTag(tag, uuid(), PaginationUtil.generatePageRequest(page, limit));
    }

    /**
     * Leiab tekstist kõik sõnad ja kuvab sõnaraamatuvormi.
     * Server pakub vahendeid teksti tükeldamiseks ja analüüsimiseks, ent ei salvesta sõnavara.
     * Seetõttu võib esineda erandjuhte, kus sõna hääldus ei ole sama, mis helilõigus.
     * PÄIS: vali tõlke keel.
     * SISU: Esitab sõnad tekstis esinemise järjekorras. Tabeli väljad: sõna, hääldus, tõlge, lause.
     * Kui võimalik, siis ka sõna hääldus.
     * JALUS: Sõnavara tabelit saab alla laadida XMS/ODS faili.
     */
    //@PreAuthorize("hasPermission(#id, 'ee.esutoniagodesu.domain.library.table.Reading', 'reading_read')")
    public Reading getReading(int id) {
        log.debug("get: id=" + id);
        return dao.find(Reading.class, id);
    }

    /**
     * Lubatud on kustutada ainult enda loodud artikleid.
     * Administraatoril on lubatud kõiki kustutada.
     */
    @PreAuthorize("hasPermission(#id, 'ee.esutoniagodesu.domain.library.table.Reading', 'reading_delete')")
    public boolean deleteReading(int id) {
        log.debug("delete: id=", id);
        return dao.removeById(Reading.class, id);
    }

    public List<String> autocompleteTag(String tagstart) {
        return libraryDB.getAutocompleteTags(tagstart, 20);
    }

    //------------------------------ sõnavara vaade ------------------------------


    //------------------------------ statistika vaade ------------------------------

    /**
     * Annab ülevaate teksti keerukusest kanjide kontekstis. Lisaks tavapärane statistika teksti pikkuse kohta.
     * Graafikud leitakse nupu vajutusel.
     * GRAAFIK1: nelja erineva indeksi põhjal graafikud. x=kanji indeks, y=kogus.
     * Näitab erineva taseme kanjide hulka tekstis. Mida paremal ja ülevalpool paikneb mediaan, seda keerukam on tekst.
     * NB! Keerukate kanjidega tekst ei pruugi olla sisuliselt raske.
     * Samuti ei tõuse teksti keerukus oluliselt kui ühte kanjit kasutatakse palju.
     * Võib öelda, et keerukus tõuseb kui kanjit on kasutatud 1,2 või 3 korda,
     * kuid suurema arvu põhjutab tõenäoliselt korduvate sõnade kasutamine.
     */


    //------------------------------ allalaadimiste vaade ------------------------------

    /**
     * Allalaetavad failid luuakse nupuvajutusel.
     * ZIP - tekstilõikude csv fail, helifailid (audio/lõigu jrk.mp3)
     * XLS/ODS/PDF - ainult tekstilõigud.
     */

    /*
    public Map.Entry<String, byte[]> getZip(int id, User user) throws Exception {
        Reading reading = getReading(id, user);

        Map.Entry<String, byte[]> report = jasperService.getReport(ECfReportType.ARTICLE,
            JSGeneratorType.CSV, reading.getReadingParagraphs());

        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(ostream);

        JCIOUtils.addToZipFile(report, zos);

        for (ArticleParagraph p : reading.getArticleParagraphs()) {
            p.getAudio().getAudioFile();
            Audio audio = p.getAudio();
            if (audio.getAudioFile() != null) {
                JCIOUtils.addToZipFile("article_audio/" + audio.getFileName(), audio.getAudioFile(), zos);
            }
        }

        zos.close();
        ostream.close();

        return new AbstractMap.SimpleEntry<>(reading.getTitle() + ".zip", ostream.toByteArray());

    }
    //*/
}
