package ee.esutoniagodesu.dataworks;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.heisig.table.HeisigCoreKw;
import ee.esutoniagodesu.repository.domain.heisig.HeisigCoreKwRepository;
import ee.esutoniagodesu.service.SHAFileService;
import ee.esutoniagodesu.util.TextUtil;
import ee.esutoniagodesu.web.rest.WebappTestEnvironment;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class DBToSHA extends WebappTestEnvironment {

    //------------------------------ spring ------------------------------

    @Inject
    private HeisigCoreKwRepository dao;

    @Inject
    public SHAFileService fileService;

    @Test
    public void tes3t() throws SQLException, IOException {


        List<HeisigCoreKw> list = dao.findAll();

        for (HeisigCoreKw p : list) {

            char kanji = p.getKanji().charAt(0);
            String name = "0" + TextUtil.hexValueOf(kanji) + ".svg";

            System.out.println(p.getKanji() + "," + name);
        }
        //*/
    }

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
