package ee.esutoniagodesu.domain.ac.table;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
public enum Authority {
    ROLE_ADMIN,
    ROLE_SENSEI,
    ROLE_USER,
    ROLE_ANONYMOUS;
}
