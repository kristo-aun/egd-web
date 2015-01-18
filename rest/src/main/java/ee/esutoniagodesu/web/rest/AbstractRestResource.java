package ee.esutoniagodesu.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public abstract class AbstractRestResource<T, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public String getLoggedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public abstract void save(T entity);
    public abstract List<T> getAll();
    public abstract ResponseEntity<T> get(ID id);
    public abstract void delete(ID id);
}