package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.domain.library.table.ReadingPage;
import ee.esutoniagodesu.repository.domain.library.ReadingPageRepository;
import ee.esutoniagodesu.repository.domain.library.ReadingRepository;
import ee.esutoniagodesu.repository.project.LibraryDB;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.security.permission.CustomPermissionEvaluator;
import ee.esutoniagodesu.security.permission.Permission;
import ee.esutoniagodesu.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
    private ProjectDAO dao;

    @Inject
    private ReadingRepository readingRepository;

    @Inject
    private ReadingPageRepository readingPageRepository;

    @Inject
    private LibraryDB libraryDB;

    @Inject
    private KuromojiService kuromojiService;

    @Inject
    private SHAFileService shaFileService;

    @Inject
    private CustomPermissionEvaluator CPE;

    //------------------------------ artiklite vaade ------------------------------

    public Reading update(Reading reading) {
        log.debug("update: reading=" + reading);
        CPE.check(reading, Permission.reading_update);
        return save(reading);
    }

    public Reading create(Reading reading) {
        log.debug("create: reading=" + reading);
        CPE.check(reading, Permission.reading_create);
        return save(reading);
    }

    private Reading save(Reading reading) {
        return readingRepository.save(reading);
    }

    public ReadingPage create(ReadingPage page) throws IOException {
        log.debug("create: page=" + page);
        CPE.check(readingRepository.findOne(page.getId()), Permission.reading_update);
        return save(page);
    }

    public ReadingPage update(ReadingPage page) throws IOException {
        log.debug("update: page=" + page);
        CPE.check(readingRepository.findOne(page.getReadingId()), Permission.reading_update);
        return save(page);
    }

    private ReadingPage save(ReadingPage p) throws IOException {
        if (p.getAudioFile() != null) {
            log.debug("save to shafs {}", p.getAudioFile().getOriginalFilename());
            String sha = shaFileService.put(p.getAudioFile());
            if (p.getAudioSha() != null && !p.getAudioSha().equals(sha)) {
                shaFileService.delete(p.getAudioSha());
            }
            p.setAudioSha(sha);
        }
        return readingPageRepository.save(p);
    }

    private String uuid() {
        return SecurityUtils.getUserUuid();
    }

    public String deleteAudio(int readingPageId) throws IOException {
        log.debug("deleteAudio: readingPageId=", readingPageId);
        ReadingPage page = readingPageRepository.findOne(readingPageId);
        if (page == null) return null;

        CPE.check(readingRepository.findOne(page.getId()), Permission.reading_update);

        String sha = page.getAudioSha();
        if (sha == null) return null;
        page.setAudioSha(null);
        readingPageRepository.save(page);

        shaFileService.delete(sha);
        return sha;
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

    public Reading getReading(int id) {
        log.debug("get: id=" + id);
        Reading result = readingRepository.findOne(id);
        CPE.check(result, Permission.reading_read);
        return result;
    }

    /**
     * Lubatud on kustutada ainult enda loodud artikleid.
     * Administraatoril on lubatud kõiki kustutada.
     */
    public void deleteReading(int id) {
        log.debug("delete: id=", id);

        Reading result = readingRepository.findOne(id);
        CPE.check(result, Permission.reading_delete);
        dao.removeById(Reading.class, id);
    }

    public List<String> autocompleteTag(String tagstart) {
        return libraryDB.getAutocompleteTags(tagstart, 20);
    }

    public List<ReadingPage> getReadingPages(int readingId) {
        Reading reading = readingRepository.findOne(readingId);
        CPE.check(reading, Permission.reading_read);
        return readingPageRepository.findByReadingId(readingId);
    }

    private Reading findByPageId(int readingPageId) {
        ReadingPage page = readingPageRepository.findOne(readingPageId);
        return readingRepository.findOne(page.getReadingId());
    }

    public void deleteReadingPage(int id) {
        log.debug("deleteReadingPage: id=", id);
        CPE.check(findByPageId(id), Permission.reading_update);
        dao.removeById(ReadingPage.class, id);
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
