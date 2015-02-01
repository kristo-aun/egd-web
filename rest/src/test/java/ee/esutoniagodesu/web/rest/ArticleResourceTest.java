package ee.esutoniagodesu.web.rest;

import com.jc.hibernate.ProjectDAO;
import ee.esutoniagodesu.Application;
import ee.esutoniagodesu.domain.test.table.Article;
import ee.esutoniagodesu.repository.project.TestRepository;
import ee.esutoniagodesu.service.ArticleService;
import ee.esutoniagodesu.service.JasperService;
import ee.esutoniagodesu.service.KuromojiService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleResourceTest extends WebappTestEnvironment {

    private static final String USERNAME = "admin";

    private static final String DEFAULT_AUTHOR = "SAMPLE_AUTHOR";
    private static final String UPDATED_AUTHOR = "UPDATED_AUTHOR";

    private static final String DEFAULT_COPYRIGHT = "SAMPLE_COPYRIGHT";
    private static final String UPDATED_COPYRIGHT = "UPDATED_COPYRIGHT";

    private static final String DEFAULT_TITLE = "SAMPLE_TITLE";
    private static final String UPDATED_TITLE = "UPDATED_TITLE";

    private static final String DEFAULT_TRANSCRIPT_LANG = "EN";
    private static final String UPDATED_TRANSCRIPT_LANG = "ET";

    private Article article;

    //------------------------------ spring ------------------------------

    @Inject
    private TestRepository testRepository;
    @Inject
    private ProjectDAO dao;

    @Inject
    private ArticleService articleService;

    @Inject
    private JasperService jasperService;

    @Inject
    private KuromojiService kuromojiService;

    @PostConstruct
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(articleService, "jasperService", jasperService);
        ReflectionTestUtils.setField(articleService, "kuromojiService", kuromojiService);
        ReflectionTestUtils.setField(articleService, "dao", dao);
        ReflectionTestUtils.setField(articleService, "testRepository", testRepository);

        ArticleResource resource = new ArticleResource();
        ReflectionTestUtils.setField(resource, "service", articleService);

        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
        setSession(USERNAME);
    }

    @Before
    public void initTest() {
        article = new Article();
        article.setAuthor(DEFAULT_AUTHOR);
        article.setCopyright(DEFAULT_COPYRIGHT);
        article.setTitle(DEFAULT_TITLE);
        article.setTranscriptLang(DEFAULT_TRANSCRIPT_LANG);
    }

    @Test
    @Transactional
    public void t1_createArticle() throws Exception {
        int size = dao.findAll(Article.class).size();

        // Create the Article
        mockMvc.perform(post("/app/rest/articles").session(session)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articles = dao.findAll(Article.class);
        assertThat(articles).hasSize(size + 1);
        Article testArticle = articles.get(1);
        assertThat(testArticle.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testArticle.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticle.getTranscriptLang()).isEqualTo(DEFAULT_TRANSCRIPT_LANG);
    }

    @Test
    public void t11_kuromoji() throws Exception {

        // Get the article
        mockMvc.perform(get("/app/rest/articles/{id}", 58).session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.author").exists())
            .andExpect(jsonPath("$.copyright").exists())
            .andExpect(jsonPath("$.title").exists())
            .andExpect(jsonPath("$.transcriptLang").exists());

    }

    @Test
    public void t2_getAllArticles() throws Exception {
        Article first = articleService.getArticlesByUser(null).get(0);
        // Get all the articles
        mockMvc.perform(get("/app/rest/articles").session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.[0].id").value(first.getId()))
        .andExpect(jsonPath("$.[0].author").value(first.getAuthor()))
        .andExpect(jsonPath("$.[0].copyright").value(first.getCopyright()))
        .andExpect(jsonPath("$.[0].title").value(first.getTitle()))
        .andExpect(jsonPath("$.[0].transcriptLang").value(first.getTranscriptLang()));
    }

    @Test
    @Transactional
    public void t3_getArticle() throws Exception {
        // Initialize the database
        dao.save(article);
        dao.flush();
        dao.refresh(article);

        // Get the article
        String content = mockMvc.perform(get("/app/rest/articles/{id}", article.getId()).session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(article.getId()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.transcriptLang").value(DEFAULT_TRANSCRIPT_LANG))
            .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    @Transactional
    public void t4_getNonExistingArticle() throws Exception {
        // Get the article
        mockMvc.perform(get("/app/rest/articles/{id}", 1L).session(session))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void t5_updateArticle() throws Exception {
        // Initialize the database
        dao.save(article);
        dao.flush();
        dao.refresh(article);

        // Update the article
        article.setAuthor(UPDATED_AUTHOR);
        article.setCopyright(UPDATED_COPYRIGHT);
        article.setTitle(UPDATED_TITLE);
        article.setTranscriptLang(UPDATED_TRANSCRIPT_LANG);
        mockMvc.perform(post("/app/rest/articles").session(session)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articles = dao.findAll(Article.class);

        TestCase.assertTrue(articles.size() > 0);
        Article testArticle = articles.get(articles.size() - 1);
        assertThat(testArticle.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testArticle.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticle.getTranscriptLang()).isEqualTo(UPDATED_TRANSCRIPT_LANG);
    }

    @Test
    @Transactional
    public void t6_deleteArticle() throws Exception {
        // Initialize the database
        dao.save(article);
        dao.flush();
        dao.refresh(article);

        // Get the article
        mockMvc.perform(delete("/app/rest/articles/{id}", article.getId()).session(session)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }
}
