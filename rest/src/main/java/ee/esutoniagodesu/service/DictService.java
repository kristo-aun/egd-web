package ee.esutoniagodesu.service;

import com.jc.structure.pojo.IntIDStringTitle;
import com.jc.structure.pojo.IntIDStringTitleImpl;
import ee.esutoniagodesu.pojo.entity.EstJap;
import ee.esutoniagodesu.pojo.entity.JapEst;
import ee.esutoniagodesu.repository.project.JMDictDB;
import ee.esutoniagodesu.repository.project.PhraseEtDB;
import ee.esutoniagodesu.repository.project.PhraseJpDB;
import ee.esutoniagodesu.repository.project.SentenceDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sõnaraamatu lehe teenindamine
 */
@Service
@Transactional
public class DictService {

    private static final Logger log = LoggerFactory.getLogger(DictService.class);

    @Inject
    private PhraseEtDB phraseEtDB;
    @Inject
    private PhraseJpDB phraseJpDB;
    @Inject
    private JMDictDB jmDictDB;
    @Inject
    private SentenceDB sentenceDB;

    private static final int _limitAutocompleteSize = 100;

    public List<IntIDStringTitle> autocomplete(String lang, String q) {
        List<IntIDStringTitle> result = new ArrayList<>();
        switch (lang) {
            case "et": {
                result.addAll(phraseEtDB.getIdAndEtByRightOpen(q, _limitAutocompleteSize).entrySet().stream().map(
                    p -> new IntIDStringTitleImpl(p.getValue(), p.getKey())).collect(Collectors.toList()));
                break;
            }
            case "jp": {
                result.addAll(phraseJpDB.getIdAndJapaneseByRightOpen(q, _limitAutocompleteSize).entrySet().stream().map(
                    p -> new IntIDStringTitleImpl(p.getValue(), p.getKey())).collect(Collectors.toList()));
                break;
            }
            default:
                throw new IllegalArgumentException("Language not available: " + lang);
        }
        log.debug("autocomplete: result.size=" + result.size());
        return result;
    }

    /**
     * Tõlkimine jaapani keele õppijale
     * JP sisendi vastuseks on JP entry list & gloss. JP audio
     * ET sisendi vastuseks on seda glossi sisaldavate entry'te list & nende glossid
     *
     * @return list JMDict entries that contain 0-or-* senses that contain 0-or-* glosses
     * @see <a href="http://google.com">http://google.com</a>
     */
    public List<JapEst> japest(String lang, String query) {
        List<JapEst> result = new ArrayList<>();
        JapEst item;
        switch (lang) {
            case "et": {
                for (int p : jmDictDB.findEntrIdsFromGloss(query)) {
                    item = jmDictDB.getEntryDataForJapEst(p);
                    if (item != null) result.add(item);
                }
                phraseEtDB.recordSearch(query, result.size(), "jp");
                break;
            }
            case "jp": {
                List<Integer> entrIds = jmDictDB.findEntrIdsByKanjOrKana(query);

                for (int p : entrIds) {
                    item = jmDictDB.getEntryDataForJapEst(p);
                    if (item != null) result.add(item);
                }

                for (JapEst p : result) {
                    for (JapEst.Sens q : p.sens) {
                        q.sentences = sentenceDB.getExampleSentences(p.kanj[0], p.rdng[0], p.sens.indexOf(q));
                    }
                }

                phraseJpDB.recordSearch(query, result.size(), "jp");
                break;
            }
            default:
                throw new IllegalArgumentException("Language not available: " + lang);
        }
        log.debug("japest: result.size=" + result.size());
        return result;
    }

    /**
     * Tõlkimine eesti keele õppijale
     * JP sisendi vastuseks on eestikeelsed tõlked.
     * ET audio, ET sõnaliigid, ET näitelaused
     * <p>
     * <p>
     * Leia list entr, mis sisaldab "maja"
     * <p>
     * <p>
     * Lisa igale kaardile estwn.example by variant.literal
     */
    public List<EstJap> estjap(String lang, String query) {
        List<EstJap> result = new ArrayList<>();

        switch (lang) {
            case "et": {//otsingusõna on eesti keeles
                List<Integer> entrids = jmDictDB.findEntrIdsFromGloss(query);
                EstJap estjap = estjap(query, entrids);
                if (estjap != null) result.add(estjap);
                phraseEtDB.recordSearch(query, result.size(), "et");
                break;
            }
            case "jp": {//otsingusõna jaapani keeles
                List<Integer> entrids = jmDictDB.findEntrIdsByKanjOrKana(query);
                for (int i : entrids) {
                    for (String gloss : jmDictDB.getGlossByEntr(i)) {
                        List<Integer> iwrap = jmDictDB.findEntrIdsFromGloss(gloss);
                        result.add(estjap(gloss, iwrap));
                    }
                }
                phraseJpDB.recordSearch(query, result.size(), "et");
                break;
            }
            default:
                throw new IllegalArgumentException("not available: " + lang);
        }

        log.debug("estjap: result.size=" + result.size());
        return result;
    }

    private EstJap estjap(String query, List<Integer> entrids) {
        EstJap item = null;

        if (entrids.size() > 0) {
            item = new EstJap();
            item.gtxt = query;

            String[] sonaliigid = phraseEtDB.getSonaliigid(query);
            if (sonaliigid != null && sonaliigid.length > 0)
                item.gpos = toHumanReadableSonaliigid(sonaliigid);

            for (int p : entrids) {
                JapEst japest = jmDictDB.getEntryDataForJapEst(p);
                EstJap.Sens sens = new EstJap.Sens(japest.kanj, japest.rdng);
                sens.entr = p;
                item.sens.add(sens);
            }

            item.sentences = sentenceDB.getEestiWordnetExampleSentences(query);
        }

        return item;
    }

    private static String[] toHumanReadableSonaliigid(String[] sonaliigid) {
        for (int i = 0; i < sonaliigid.length; i++) {
            switch (sonaliigid[i]) {
                case "n": {
                    sonaliigid[i] = "n";
                    break;
                }
                case "v": {
                    sonaliigid[i] = "v";
                    break;
                }
                case "a": {
                    sonaliigid[i] = "adj";
                    break;
                }
                case "b": {
                    sonaliigid[i] = "adv";
                    break;
                }
            }
        }
        return sonaliigid;
    }
}