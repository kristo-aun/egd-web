package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.repository.project.KanjiDB;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class KanjiService {

    private static final Logger log = Logger.getLogger(KuromojiService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private KanjiDB kanjiDB;

    public List<String[]> examplewords(String kanji, int count) {
        StringBuilder msg = new StringBuilder("examplewords: kanji=" + kanji);
        if (kanji == null) throw new IllegalArgumentException(msg.toString());
        log.debug(msg.toString());
        return kanjiDB.getExampleWords(kanji, count);
    }

    public boolean hasKanji(char kanji) {
        return kanjiDB.containsKanji(kanji);
    }
}
