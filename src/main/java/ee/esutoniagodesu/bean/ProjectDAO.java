package ee.esutoniagodesu.bean;

import com.googlecode.genericdao.dao.jpa.GeneralDAOImpl;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ProjectDAO extends GeneralDAOImpl {

    private static final Logger log = LoggerFactory.getLogger(ProjectDAO.class);

    public ProjectDAO() {
        log.info("New instance of " + getClass());
    }

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        log.debug("setEntityManager");
        super.setEntityManager(em);
    }

    @Override
    @Autowired
    public void setSearchProcessor(JPASearchProcessor searchProcessor) {
        log.debug("setSearchProcessor");
        super.setSearchProcessor(searchProcessor);
    }

    @Inject
    protected JdbcTemplate jdbcTemplate;

    @Inject
    protected DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
