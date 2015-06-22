package ee.esutoniagodesu.domain.ac.table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Persist AuditEvent managed by the Spring Boot actuator
 *
 * @see org.springframework.boot.actuate.audit.AuditEvent
 */
@Entity
@Table(name = "persistent_audit_event", schema = "ac")
public final class PersistentAuditEvent implements Serializable {

    private static final long serialVersionUID = 1701187813434104565L;

    private int id;
    @NotNull
    private String principal;
    private LocalDateTime auditEventDate;
    private String auditEventType;
    private Map<String, String> data = new HashMap<>();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "event_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    public LocalDateTime getAuditEventDate() {
        return auditEventDate;
    }

    public void setAuditEventDate(LocalDateTime auditEventDate) {
        this.auditEventDate = auditEventDate;
    }

    @Column(name = "event_type")
    public String getAuditEventType() {
        return auditEventType;
    }

    public void setAuditEventType(String auditEventType) {
        this.auditEventType = auditEventType;
    }

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "persistent_audit_event_data", schema = "ac", joinColumns = @JoinColumn(name = "event_id"))
    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Column(nullable = false)
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String toString() {
        return "PersistentAuditEvent{" +
            "id=" + id +
            ", principal='" + principal + '\'' +
            ", auditEventDate=" + auditEventDate +
            ", auditEventType='" + auditEventType + '\'' +
            ", data=" + data +
            '}';
    }
}
