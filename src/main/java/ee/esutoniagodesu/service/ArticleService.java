package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.publik.table.Audio;
import ee.esutoniagodesu.domain.test.table.Article;
import ee.esutoniagodesu.domain.test.table.ArticleParagraph;
import ee.esutoniagodesu.pojo.cf.ECfReportType;
import ee.esutoniagodesu.pojo.dto.ArticleDTO;
import ee.esutoniagodesu.repository.project.TestRepository;
import ee.esutoniagodesu.util.commons.JCIOUtils;
import ee.esutoniagodesu.util.jasperreports.JSGeneratorType;
import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Lugemisharjutused. Artiklite moodulis saab hallata täispikki Jaapanikeelseid algtekste.
 * Tekst tükeldatakse 3-5 lauselisteks lõikudeks. Igale lõigule saab lisada heli.
 * Algajale on Jaapanikeelsete tekstide lugemine ilma abivahenditeta erakordselt tülikas.
 * Võib öelda, et ilma vähemalt 1500-t kanjit tundmata ei ole võimalik lugeda.
 * Seejuures ei ole lugemisoskus lineaarses seoses kanjide oskusega.
 * Tekstide moodulisse on koondatud erinevad abivahendid, et lugemist lihtsustada.
 *
 * TODO
 * kasutaja roll ei tohiks näha artiklite admin andmeid
 */
@Service
@Transactional
public class ArticleService {

    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

    @Inject
    private JasperService jasperService;

    @Inject
    private ProjectDAO dao;

    @Inject
    private TestRepository testRepository;

    @Inject
    private KuromojiService kuromojiService;

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
    public void save(Article article, User user) {
        log.debug("save: article=" + article);
        if (!user.hasRoleAdmin() && !article.isCreatedBy(user.getLogin()))
            throw new IllegalAccessError("alert.article.changing-articles-you-dont-own-not-allowed");

        dao.save(article);
    }

    /**
     * Kasutajale näidatakse tema enda loodud ja avalikke artikleid.
     */
    public List<ArticleDTO> getArticlesByUser(User user) {
        return testRepository.findUserArticles(user.getLogin());
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
    public Article getArticle(int id, User user) {
        log.debug("get: id=" + id);
        Article article = dao.find(Article.class, id);
        if (!user.hasRoleAdmin() && !article.isShared() && !article.isCreatedBy(user.getLogin()))
            throw new IllegalAccessError("alert.article.article-not-public");

        return article;
    }

    /**
     * Lubatud on kustutada ainult enda loodud artikleid.
     * Administraatoril on lubatud kõiki kustutada.
     */
    public void deleteArticle(int id, User user) {
        log.debug("delete: id=", id);
        if (!user.hasRoleAdmin() && !getArticle(id, user).isCreatedBy(user.getLogin()))
            throw new IllegalAccessError("alert.article.delete-not-allowed-if-not-owner");

        dao.removeById(Article.class, id);
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

    public Map.Entry<String, byte[]> getZip(int id, User user) throws Exception {
        Article article = getArticle(id, user);

        Map.Entry<String, byte[]> report = jasperService.getReport(ECfReportType.ARTICLE,
            JSGeneratorType.CSV, article.getArticleParagraphs());

        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(ostream);

        JCIOUtils.addToZipFile(report, zos);

        for (ArticleParagraph p : article.getArticleParagraphs()) {
            p.getAudio().getAudioFile();
            Audio audio = p.getAudio();
            if (audio.getAudioFile() != null) {
                JCIOUtils.addToZipFile("article_audio/" + audio.getFileName(), audio.getAudioFile(), zos);
            }
        }

        zos.close();
        ostream.close();

        return new AbstractMap.SimpleEntry<>(article.getTitle() + ".zip", ostream.toByteArray());

    }
}
