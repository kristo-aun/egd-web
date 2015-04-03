package ee.esutoniagodesu.domain.publik.table;

import ee.esutoniagodesu.domain.publik.pk.JmGlossInfPK;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@IdClass(JmGlossInfPK.class)
@Table(name = "jm_gloss_inf", schema = "public", catalog = "egd")
@Entity
public final class JmGlossInf implements Serializable {

    private static final long serialVersionUID = -5946557647810376579L;

    private int entr;
    private int sens;
    private int gloss;
    private String etInf;
    private String jpInf;
    private Time inserted;
    private CfOrigin cfOrigin;

    @ManyToOne
    @JoinColumn(name = "cf_origin", referencedColumnName = "id", nullable = false)
    public CfOrigin getCfOrigin() {
        return cfOrigin;
    }

    public void setCfOrigin(CfOrigin cfOrigin) {
        this.cfOrigin = cfOrigin;
    }

    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
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
    public int getGloss() {
        return gloss;
    }

    public void setGloss(int gloss) {
        this.gloss = gloss;
    }

    @Column(name = "inserted", nullable = false, insertable = true, updatable = true, length = 15, precision = 6)
    @Basic
    public Time getInserted() {
        return inserted;
    }

    public void setInserted(Time inserted) {
        this.inserted = inserted;
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
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmGlossInf that = (JmGlossInf) o;

        if (entr != that.entr) return false;
        if (gloss != that.gloss) return false;
        if (sens != that.sens) return false;
        if (etInf != null ? !etInf.equals(that.etInf) : that.etInf != null) return false;
        if (inserted != null ? !inserted.equals(that.inserted) : that.inserted != null) return false;
        if (jpInf != null ? !jpInf.equals(that.jpInf) : that.jpInf != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + gloss;
        result = 31 * result + (etInf != null ? etInf.hashCode() : 0);
        result = 31 * result + (jpInf != null ? jpInf.hashCode() : 0);
        result = 31 * result + (inserted != null ? inserted.hashCode() : 0);
        return result;
    }
}
