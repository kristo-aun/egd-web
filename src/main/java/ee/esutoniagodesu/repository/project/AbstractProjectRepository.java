package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractProjectRepository {

    protected final Logger log = Logger.getLogger(getClass());

    public AbstractProjectRepository() {
        log.debug("New instance of " + getClass());
    }

    @Inject
    protected ProjectDAO dao;

    @PersistenceContext
    protected EntityManager em;
}
