package ee.esutoniagodesu.repository.domain.ac;

import ee.esutoniagodesu.domain.ac.table.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
