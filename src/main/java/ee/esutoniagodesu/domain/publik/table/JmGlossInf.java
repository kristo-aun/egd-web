package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import ee.esutoniagodesu.domain.AbstractAuditingEntity;
import ee.esutoniagodesu.domain.publik.pk.JmGlossInfPK;

import javax.persistence.*;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(JmGlossInfPK.class)
@Entity
@Table(name = "jm_gloss_inf", schema = "public")
public class JmGlossInf extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = -5946457647810376579L;

    private Integer entr;
    private Integer sens;
    private Integer gloss;
    private String etInf;
    private String jpInf;

    private EOrigin origin;
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin", nullable = true)
    @Basic
    public EOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(EOrigin origin) {
        this.origin = origin;
    }

    @Column(name = "external_id", nullable = true)
    @Basic
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    @Column(name = "et_inf", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getEtInf() {
        return etInf;
    }

    public void setEtInf(String etInf) {
        this.etInf = etInf;
    }

    @Column(name = "gloss", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getGloss() {
        return gloss;
    }

    public void setGloss(Integer gloss) {
        this.gloss = gloss;
    }

    @Column(name = "jp_inf", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJpInf() {
        return jpInf;
    }

    public void setJpInf(String jpInf) {
        this.jpInf = jpInf;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmGlossInf that = (JmGlossInf) o;

        if (entr != null ? !entr.equals(that.entr) : that.entr != null) return false;
        if (sens != null ? !sens.equals(that.sens) : that.sens != null) return false;
        if (gloss != null ? !gloss.equals(that.gloss) : that.gloss != null) return false;
        if (etInf != null ? !etInf.equals(that.etInf) : that.etInf != null) return false;
        return !(jpInf != null ? !jpInf.equals(that.jpInf) : that.jpInf != null);

    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        result = 31 * result + (etInf != null ? etInf.hashCode() : 0);
        result = 31 * result + (jpInf != null ? jpInf.hashCode() : 0);
        return result;
    }
}
