package ee.esutoniagodesu.repository.domain.ac;

import ee.esutoniagodesu.domain.ac.table.PersistentAuditEvent;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Integer> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, DateTime after);

    List<PersistentAuditEvent> findAllByAuditEventDateBetween(DateTime fromDate, DateTime toDate);
}
