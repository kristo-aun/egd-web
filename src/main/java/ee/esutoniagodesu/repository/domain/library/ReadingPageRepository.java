package ee.esutoniagodesu.repository.domain.library;

import ee.esutoniagodesu.domain.library.table.ReadingPage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Reading entity.
 */
public interface ReadingPageRepository extends JpaRepository<ReadingPage, Integer> {
}
