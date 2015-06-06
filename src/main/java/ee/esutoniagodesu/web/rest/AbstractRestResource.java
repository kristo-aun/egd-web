package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.List;

public abstract class AbstractRestResource<T, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private UserService userService;

    protected User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    public abstract void save(T entity);

    public abstract List<T> getAll();

    public abstract ResponseEntity<T> get(ID id);

    public abstract void delete(ID id);
}
