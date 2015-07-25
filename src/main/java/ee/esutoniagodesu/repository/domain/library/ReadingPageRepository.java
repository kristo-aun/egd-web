package ee.esutoniagodesu.repository.domain.library;

import ee.esutoniagodesu.domain.library.table.ReadingPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Reading entity.
 */
public interface ReadingPageRepository extends JpaRepository<ReadingPage, Integer> {
    @Query(value = "SELECT a FROM ReadingPage a WHERE a.readingId=?1 ORDER BY a.page")
    List<ReadingPage> findByReadingId(int readingId);
}
