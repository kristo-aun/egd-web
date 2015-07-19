package ee.esutoniagodesu.domain.ac.table;


/**
 * An authority (a security role) used by Spring Security.
 */
public enum Authority {
    ROLE_ADMIN,
    ROLE_SENSEI,
    ROLE_USER,
    ROLE_ANONYMOUS;
}
