package ee.esutoniagodesu.domain;

import com.fasterxml.jackson.annotation.JsonView;
import ee.esutoniagodesu.config.Constants;
import ee.esutoniagodesu.web.rest.dto.View;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {

    @JsonView(View.Basic.class)
    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    protected String createdBy;

    @JsonView(View.Basic.class)
    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    protected ZonedDateTime createdDate;

    @JsonView(View.Basic.class)
    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    @JsonView(View.Basic.class)
    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected ZonedDateTime lastModifiedDate;

    @Transient
    public boolean isCreatedBy(String userId) {
        return createdBy != null && userId != null && createdBy.equals(userId);
    }

    @Transient
    public boolean isModifiedBy(String userId) {
        return lastModifiedBy != null && userId != null && lastModifiedBy.equals(userId);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String toString() {
        return "AbstractAuditingEntity{" +
            "createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy=" + lastModifiedBy +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
