package ee.esutoniagodesu;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.service.SHAFileService;
import ee.esutoniagodesu.web.rest.WebappTestEnvironment;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;

@Transactional
public class DBToSHA extends WebappTestEnvironment {

    //------------------------------ spring ------------------------------

    @Inject
    private ProjectDAO dao;

    @Inject
    public SHAFileService fileService;

    @Rollback(false)
    @Test
    public void test() throws SQLException, IOException {

        /*
        List<Kanji> list = dao.findAll(Kanji.class);

        for (Kanji p : list) {

            char kanji = p.getLiteral().charAt(0);
            String name = "0" + TextUtil.hexValueOf(kanji) + ".svg";
            String address = "/home/scylla/esutoniagodesu/resources.git/img/kanji/";
            File file = new File(address + name);

            if (file.exists()) {
                String sha = fileService.put(file);

                MtmKanjiImage image = new MtmKanjiImage();
                image.setImageSha(sha);
                image.setKanji(p);
                image.setSeq(1);
                dao.save(image);
            }
        }
        dao.flush();
        //*/
    }
}
