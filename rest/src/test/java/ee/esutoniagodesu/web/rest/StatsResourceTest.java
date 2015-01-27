package ee.esutoniagodesu.web.rest;

import com.jc.hibernate.ProjectDAO;
import ee.esutoniagodesu.domain.core.view.VCoreStats;
import ee.esutoniagodesu.repository.project.ReportDB;
import ee.esutoniagodesu.service.StatsService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StatsResource REST controller.
 *
 * @see ee.esutoniagodesu.web.rest.StatsResource
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatsResourceTest extends WebappTestEnvironment {

    private static final String USERNAME = "admin";

    //------------------------------ spring ------------------------------

    @Inject
    private ProjectDAO dao;

    @Inject
    private ReportDB reportDB;

    @Inject
    private StatsService statsService;

    @PostConstruct
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(statsService, "dao", dao);
        ReflectionTestUtils.setField(statsService, "reportDB", reportDB);

        StatsResource resource = new StatsResource();
        ReflectionTestUtils.setField(resource, "service", statsService);

        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
        setSession(USERNAME);
    }

    @Before
    public void initTest() {

    }

    @Test
    public void t1_core_stats() throws Exception {
        List<VCoreStats> stats = dao.findAll(VCoreStats.class);
        VCoreStats first = stats.get(0);

        mockMvc.perform(get("/app/rest/stats/core_stats"))
            .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/app/rest/stats/core_stats").session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].resource").value(first.getResource()));
    }
}
