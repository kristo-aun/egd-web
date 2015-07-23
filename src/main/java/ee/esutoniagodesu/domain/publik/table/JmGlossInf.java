package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import ee.esutoniagodesu.domain.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "jm_gloss_inf", schema = "public")
public class JmGlossInf extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = -5946457647810376579L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    private Integer entr;

    @Column(name = "sens", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    private Integer sens;

    @Column(name = "gloss", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    private Integer gloss;

    @Column(name = "et_inf", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String etInf;

    @Column(name = "jp_inf", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String jpInf;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin", nullable = true)
    @Basic
    private EOrigin origin;

    @Column(name = "external_id", nullable = true)
    @Basic
    private String externalId;


    public JmGlossInf() {
    }

    public JmGlossInf(Integer entr, Integer sens, Integer gloss, EOrigin origin, String etInf) {
        this.entr = entr;
        this.sens = sens;
        this.gloss = gloss;
        this.origin = origin;
        this.etInf = etInf;
    }

    public JmGlossInf(Integer entr, Integer sens, Integer gloss, String etInf, EOrigin origin, String externalId) {
        this.entr = entr;
        this.sens = sens;
        this.gloss = gloss;
        this.etInf = etInf;
        this.origin = origin;
        this.externalId = externalId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public Integer getGloss() {
        return gloss;
    }

    public void setGloss(Integer gloss) {
        this.gloss = gloss;
    }

    public String getEtInf() {
        return etInf;
    }

    public void setEtInf(String etInf) {
        this.etInf = etInf;
    }

    public String getJpInf() {
        return jpInf;
    }

    public void setJpInf(String jpInf) {
        this.jpInf = jpInf;
    }

    public EOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(EOrigin origin) {
        this.origin = origin;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmGlossInf that = (JmGlossInf) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (entr != null ? !entr.equals(that.entr) : that.entr != null) return false;
        if (sens != null ? !sens.equals(that.sens) : that.sens != null) return false;
        if (gloss != null ? !gloss.equals(that.gloss) : that.gloss != null) return false;
        if (etInf != null ? !etInf.equals(that.etInf) : that.etInf != null) return false;
        if (jpInf != null ? !jpInf.equals(that.jpInf) : that.jpInf != null) return false;
        if (origin != that.origin) return false;
        return !(externalId != null ? !externalId.equals(that.externalId) : that.externalId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (entr != null ? entr.hashCode() : 0);
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        result = 31 * result + (etInf != null ? etInf.hashCode() : 0);
        result = 31 * result + (jpInf != null ? jpInf.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JmGlossInf{" +
            "id=" + id +
            ", entr=" + entr +
            ", sens=" + sens +
            ", gloss=" + gloss +
            ", etInf='" + etInf + '\'' +
            ", jpInf='" + jpInf + '\'' +
            ", origin=" + origin +
            ", externalId='" + externalId + '\'' +
            "} " + super.toString();
    }
}
