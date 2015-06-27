package ee.esutoniagodesu.repository.domain.library;

import ee.esutoniagodesu.domain.library.table.Reading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Reading entity.
 */
public interface ReadingRepository extends JpaRepository<Reading, Integer> {
    @Query(value = "select a from Reading a where a.createdBy=?1 or a.shared=true ORDER BY a.id desc")
    Page<Reading> findByCreatedBy(String createdBy, Pageable pageable);
}
