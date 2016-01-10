package ee.esutoniagodesu.dataworks;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.kanjidic2.table.MtmKanjiImage;
import ee.esutoniagodesu.service.AbstractAuthenticatedServiceTest;
import ee.esutoniagodesu.service.SHADBService;
import ee.esutoniagodesu.service.SHAFileService;
import org.junit.Test;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FilesToH2 extends AbstractAuthenticatedServiceTest {

    @Inject
    private SHADBService shadbService;

    @Inject
    private SHAFileService shaFileService;

    @Inject
    private ProjectDAO projectDAO;

    @Test
    public void transferSVGImages() throws IOException {
        List<MtmKanjiImage> list = projectDAO.findAll(MtmKanjiImage.class);

        for (MtmKanjiImage p : list) {
            String sha = p.getImageSha();
            Map.Entry<Properties, File> entry = shaFileService.getWithProperties(sha);
            shadbService.put(entry.getValue(), entry.getKey().getProperty("orig-name"));
        }
    }
}
