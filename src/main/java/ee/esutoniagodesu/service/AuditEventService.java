package ee.esutoniagodesu.service;

import ee.esutoniagodesu.config.audit.AuditEventConverter;
import ee.esutoniagodesu.domain.ac.table.PersistentAuditEvent;
import ee.esutoniagodesu.repository.domain.ac.PersistenceAuditEventRepository;
import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service for managing audit events.
 * <p/>
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    @Inject
    private PersistenceAuditEventRepository repository;

    @Inject
    private AuditEventConverter auditEventConverter;

    public List<AuditEvent> findAll() {
        return auditEventConverter.convertToAuditEvent(repository.findAll());
    }

    public List<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        List<PersistentAuditEvent> persistentAuditEvents =
            repository.findAllByAuditEventDateBetween(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }
}
