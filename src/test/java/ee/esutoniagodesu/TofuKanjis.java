package ee.esutoniagodesu;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.core.table.MtmTofuSentenceKanji;
import ee.esutoniagodesu.domain.core.table.TofuSentence;
import ee.esutoniagodesu.domain.core.table.TofuSentenceKanji;
import ee.esutoniagodesu.repository.domain.core.TofuSentenceKanjiRepository;
import ee.esutoniagodesu.util.lang.alphab.JapaneseCharacter;
import ee.esutoniagodesu.web.rest.WebappTestEnvironment;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TofuKanjis extends WebappTestEnvironment {

    private static final String USERNAME = "admin";

    //------------------------------ spring ------------------------------

    @Inject
    private ProjectDAO dao;

    @Inject
    public TofuSentenceKanjiRepository tofuSentenceKanjiRepository;

    @Transactional
    @Test
    public void ukanjis() throws SQLException {

        List<TofuSentence> sentences = dao.findAll(TofuSentence.class);


        for (TofuSentence p : sentences) {

            char[] chars = p.getSentence().toCharArray();

            for (char c : chars) {
                List<Character> korduv = new ArrayList<>();

                if (JapaneseCharacter.isKanji(c)) {

                    if (korduv.contains(c)) continue;
                    korduv.add(c);

                    TofuSentenceKanji kanji = tofuSentenceKanjiRepository.findOneByKanji(String.valueOf(c)).get();
                    MtmTofuSentenceKanji mtm = new MtmTofuSentenceKanji(p, kanji);

                    dao.save(mtm);

                }
            }
        }

        dao.flush();


    }
}
