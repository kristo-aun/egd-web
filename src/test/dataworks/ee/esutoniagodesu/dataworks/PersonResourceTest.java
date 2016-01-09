package ee.esutoniagodesu.dataworks;

import ee.esutoniagodesu.Application;
import ee.esutoniagodesu.domain.heisig.table.HeisigCoreKw;
import ee.esutoniagodesu.repository.domain.heisig.HeisigCoreKwRepository;
import ee.esutoniagodesu.service.HeisigService;
import ee.esutoniagodesu.util.commons.JCAudio;
import ee.esutoniagodesu.web.rest.HeisigResource;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonResourceTest {

    @Inject
    private HeisigCoreKwRepository heisigCoreKwRepository;

    @Inject
    private HeisigService heisigService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        HeisigResource resource = new HeisigResource();
        ReflectionTestUtils.setField(resource, "service", heisigService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

	//@Test
    //@Rollback(false)
	public void t_en_to_db() throws Exception {

        String address = "/home/scylla/esutoniagodesu/egd-web/tmp/en_null/";
        Path enPath = Paths.get(address);

        Files.walk(enPath).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    String fileName = filePath.getFileName().toString();
                    System.out.println(fileName);
                    Integer frameNo = Integer.valueOf(fileName.substring(0, fileName.indexOf(".mp3")));
                    System.out.println(frameNo);

                    byte[] array = Files.readAllBytes(filePath);
                    String src = "GOOGLE-TTS";

                    HeisigCoreKw kw = heisigCoreKwRepository.findOne(frameNo);

                    kw.setKeywordEnAudio(array);
                    kw.setKeywordEnAudioSrc(src);

                    heisigCoreKwRepository.save(kw);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    //@Rollback(false)
    public void t_en_is_null() throws Exception {

        String address = "/home/scylla/esutoniagodesu/egd-web/tmp/en_null/";

        List<HeisigCoreKw> kws = heisigCoreKwRepository.findByKeywordAudioIsNull(1, 1000);
        System.out.println(kws);

        for (HeisigCoreKw p : kws) {

            //Map.Entry<String, byte[]> entry = JCAudio.googleTTSBytes(p.getKeywordEn(), "en_UK");
            //System.out.println(entry.getKey());
            //Path path = Paths.get(address + "RTK1_keyword_en_"+p.getId()+".mp3");
            //Files.write(path, entry.getValue());

            System.out.println(p.getId() + ", " + p.getKeywordEn());

        }
    }

}
