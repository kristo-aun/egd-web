package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.repository.project.KanjiDB;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public void setKanjiStrokeDiagram(String kanji, Map.Entry<String, byte[]> imageFile, String copyright) throws IllegalArgumentException, IOException, SQLException {
        log.debug("setKanjiStrokeDiagram");
        int imageId = kanjiDB.insUpdStrokeDiagram(kanji, imageFile, copyright);
        if (imageId < 1) throw new IllegalStateException("imageId < 1");
    }

    public int countKanjisWithoutImages() throws IllegalArgumentException, SQLException {
        String imageless = kanjiDB.getKanjisWithoutStrokeDiagram();
        if (imageless == null) return 0;
        String[] kanjis = imageless.split(",");
        return kanjis.length;
    }

    public String[] getKanjisWithoutImages() throws IllegalArgumentException, SQLException {
        String imageless = kanjiDB.getKanjisWithoutStrokeDiagram();
        if (imageless == null) return null;
        return imageless.split(",");
    }

    public Map.Entry<String, byte[]> getImageBytes(char kanji) throws IllegalArgumentException, SQLException {
        return kanjiDB.getStrokeDiagram(kanji);
    }

    public boolean hasKanji(char kanji) {
        return kanjiDB.containsKanji(kanji);
    }
}
