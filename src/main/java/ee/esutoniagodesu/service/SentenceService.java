package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.publik.table.*;
import ee.esutoniagodesu.pojo.cf.ECfAudioQuality;
import ee.esutoniagodesu.pojo.cf.ECfOrigin;
import ee.esutoniagodesu.repository.project.SentenceDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class SentenceService {

    private static final Logger log = LoggerFactory.getLogger(SentenceService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private SentenceDB sentenceDB;

    public List<JpSentence> findAllJpSentence() {
        StringBuilder msg = new StringBuilder("findAllJpSentence");
        long ms = System.currentTimeMillis();
        List<JpSentence> result = dao.findAll(JpSentence.class);
        log.debug(msg.append("result.size=").append(result.size())
            .append(", time=").append(System.currentTimeMillis() - ms).toString());
        return result;
    }

    private Audio _jpAudio;
    private JpSentence _jpSentence;
    private EnSentence _enSentence;
    private EtSentence _etSentence;
    private MtmJmSensJpSentence _mtmJmSensJpSentence;

    public void setAnkiCoreHuman(String jpSentenceWithFurigana, Map.Entry<String, byte[]> jpAudio, String enSentence) {

        CfOrigin cfOrigin = dao.find(CfOrigin.class, ECfOrigin.ANKI.ID);
        Assert.notNull(cfOrigin);
        CfAudioQuality cfAudioQuality = dao.find(CfAudioQuality.class, ECfAudioQuality.HUMAN_VERY_GOOD.ID);
        Assert.notNull(cfAudioQuality);

        //objektide loomine
        _jpAudio = new Audio();
        Assert.isTrue(jpAudio.getValue().length > 0);
        _jpAudio.setAudioFile(jpAudio.getValue());
        _jpAudio.setFileName(jpAudio.getKey());
        _jpAudio.setCopyright("Core_2k6k10k_Audio__Image_Files_0k-10k.apkg");
        _jpAudio.setCfAudioQuality(cfAudioQuality);

        _jpSentence = new JpSentence();
        _jpSentence.setCfOrigin(cfOrigin);
        _jpSentence.setJp(removeFurigana(jpSentenceWithFurigana));
        log.debug("setAnkiCoreHuman: jp=" + _jpSentence.getJp());

        if (sentenceDB.jpSentenceExists(_jpSentence.getJp())) {
            throw new IllegalStateException("jpSentenceExists: jp=" + _jpSentence.getJp());
        }

        _jpSentence.setWithFurigana(jpSentenceWithFurigana);

        _enSentence = new EnSentence();
        _enSentence.setCfOrigin(cfOrigin);
        _enSentence.setEn(enSentence);
    }

    public void attachToJMDictSens(String kanj, String rdng, String enGloss) {
        StringBuilder msg = new StringBuilder("attachToJMDictSens: kanj=" + kanj + ", rdng=" + rdng);
        if (kanj == null || rdng == null || enGloss == null) throw new IllegalStateException(msg.toString());
        enGloss = firstGloss(enGloss);
        msg.append(", enGloss=").append(enGloss);

        _mtmJmSensJpSentence = new MtmJmSensJpSentence();
        _mtmJmSensJpSentence.setKanj(kanj);
        _mtmJmSensJpSentence.setRdng(rdng);
        _mtmJmSensJpSentence.setSens(1);//tähendused on en ja et sõnastikes liiga erinevalt, et üritada asukohapõhiselt omistada
    }

    public void save() {
        log.debug("save");
        if (_jpAudio != null) {
            dao.save(_jpAudio);
        }

        if (_jpSentence != null) {
            _jpSentence.setAudio(_jpAudio);
            dao.save(_jpSentence);
        }

        if (_etSentence != null) {
            if (_jpSentence != null)//kuna baasi võib et lauseid ka ilma jp lauseta panna
                _etSentence.setJpSentence(_jpSentence);
            dao.save(_etSentence);
        }

        if (_enSentence != null) {
            if (_jpSentence != null)//kuna baasi võib en lauseid ka ilma jp lauseta panna
                _enSentence.setJpSentence(_jpSentence);
            dao.save(_enSentence);
        }

        if (_mtmJmSensJpSentence != null) {
            _mtmJmSensJpSentence.setJpSentence(_jpSentence);
            dao.save(_mtmJmSensJpSentence);
        }

        dao.flush();
    }

    public void clear() {
        log.debug("clear");
        _jpAudio = null;
        _jpSentence = null;
        _etSentence = null;
        _enSentence = null;
    }

    private static String firstGloss(String rawGloss) {
        if (!rawGloss.contains(",") && !rawGloss.contains("(") && !rawGloss.contains(" ")) return rawGloss;
        if (rawGloss.contains(",")) {
            rawGloss = rawGloss.split(",")[0];
        }
        if (rawGloss.contains("(")) {
            if (rawGloss.indexOf("(") != 0)
                rawGloss = rawGloss.split("\\(")[0];
            else
                rawGloss = rawGloss.split("\\)")[1];
        }
        return rawGloss.trim();
    }

    private static String removeFurigana(String jpSentenceWithFurigana) {
        StringBuilder kanji = new StringBuilder();
        boolean ignore = false;
        for (char p : jpSentenceWithFurigana.toCharArray()) {
            if (p == ' ' || p == '\t') continue;
            if (p == '[') ignore = true;
            else if (p == ']') {
                if (!ignore) throw new IllegalStateException("et ignore kinni panna, peab see olema avatud");
                ignore = false;
                continue;
            }
            if (!ignore) kanji.append(p);
        }
        return kanji.toString();
    }
}
