package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.Application;
import ee.esutoniagodesu.domain.Tofu;
import ee.esutoniagodesu.repository.TofuRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TofuResource REST controller.
 *
 * @see ee.esutoniagodesu.web.rest.TofuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TofuResourceTest {

    private static final String DEFAULT_WORD = "SAMPLE_TEXT";
    private static final String UPDATED_WORD = "UPDATED_TEXT";
    private static final String DEFAULT_SENTENCE = "SAMPLE_TEXT";
    private static final String UPDATED_SENTENCE = "UPDATED_TEXT";

    @Inject
    private TofuRepository tofuRepository;

    private MockMvc restTofuMockMvc;

    private Tofu tofu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TofuResource tofuResource = new TofuResource();
        ReflectionTestUtils.setField(tofuResource, "tofuRepository", tofuRepository);
        this.restTofuMockMvc = MockMvcBuilders.standaloneSetup(tofuResource).build();
    }

    @Before
    public void initTest() {
        tofu = new Tofu();
        tofu.setWord(DEFAULT_WORD);
        tofu.setSentence(DEFAULT_SENTENCE);
    }

    @Test
    @Transactional
    public void createTofu() throws Exception {
        int databaseSizeBeforeCreate = tofuRepository.findAll().size();

        // Create the Tofu
        restTofuMockMvc.perform(post("/api/tofus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tofu)))
                .andExpect(status().isCreated());

        // Validate the Tofu in the database
        List<Tofu> tofus = tofuRepository.findAll();
        assertThat(tofus).hasSize(databaseSizeBeforeCreate + 1);
        Tofu testTofu = tofus.get(tofus.size() - 1);
        assertThat(testTofu.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testTofu.getSentence()).isEqualTo(DEFAULT_SENTENCE);
    }

    @Test
    @Transactional
    public void checkWordIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tofuRepository.findAll()).hasSize(0);
        // set the field null
        tofu.setWord(null);

        // Create the Tofu, which fails.
        restTofuMockMvc.perform(post("/api/tofus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tofu)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tofu> tofus = tofuRepository.findAll();
        assertThat(tofus).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSentenceIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tofuRepository.findAll()).hasSize(0);
        // set the field null
        tofu.setSentence(null);

        // Create the Tofu, which fails.
        restTofuMockMvc.perform(post("/api/tofus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tofu)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Tofu> tofus = tofuRepository.findAll();
        assertThat(tofus).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllTofus() throws Exception {
        // Initialize the database
        tofuRepository.saveAndFlush(tofu);

        // Get all the tofus
        restTofuMockMvc.perform(get("/api/tofus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tofu.getId().intValue())))
                .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD.toString())))
                .andExpect(jsonPath("$.[*].sentence").value(hasItem(DEFAULT_SENTENCE.toString())));
    }

    @Test
    @Transactional
    public void getTofu() throws Exception {
        // Initialize the database
        tofuRepository.saveAndFlush(tofu);

        // Get the tofu
        restTofuMockMvc.perform(get("/api/tofus/{id}", tofu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tofu.getId().intValue()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD.toString()))
            .andExpect(jsonPath("$.sentence").value(DEFAULT_SENTENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTofu() throws Exception {
        // Get the tofu
        restTofuMockMvc.perform(get("/api/tofus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTofu() throws Exception {
        // Initialize the database
        tofuRepository.saveAndFlush(tofu);

		int databaseSizeBeforeUpdate = tofuRepository.findAll().size();

        // Update the tofu
        tofu.setWord(UPDATED_WORD);
        tofu.setSentence(UPDATED_SENTENCE);
        restTofuMockMvc.perform(put("/api/tofus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tofu)))
                .andExpect(status().isOk());

        // Validate the Tofu in the database
        List<Tofu> tofus = tofuRepository.findAll();
        assertThat(tofus).hasSize(databaseSizeBeforeUpdate);
        Tofu testTofu = tofus.get(tofus.size() - 1);
        assertThat(testTofu.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testTofu.getSentence()).isEqualTo(UPDATED_SENTENCE);
    }

    @Test
    @Transactional
    public void deleteTofu() throws Exception {
        // Initialize the database
        tofuRepository.saveAndFlush(tofu);

		int databaseSizeBeforeDelete = tofuRepository.findAll().size();

        // Get the tofu
        restTofuMockMvc.perform(delete("/api/tofus/{id}", tofu.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tofu> tofus = tofuRepository.findAll();
        assertThat(tofus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
