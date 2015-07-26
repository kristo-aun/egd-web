package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_gloss", schema = "jmet")
public final class VtGloss implements Serializable {

    private static final long serialVersionUID = -3912585960176776736L;

    private Integer entr;
    private Integer sens;
    private String gtxt;

    @Id
    @Basic
    @Column(name = "entr", nullable = true, insertable = true, updatable = true)
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "gtxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getGtxt() {
        return gtxt;
    }

    public void setGtxt(String gtxt) {
        this.gtxt = gtxt;
    }

    @Basic
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VtGloss vtGloss = (VtGloss) o;

        if (entr != null ? !entr.equals(vtGloss.entr) : vtGloss.entr != null) return false;
        if (gtxt != null ? !gtxt.equals(vtGloss.gtxt) : vtGloss.gtxt != null) return false;
        if (sens != null ? !sens.equals(vtGloss.sens) : vtGloss.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (gtxt != null ? gtxt.hashCode() : 0);
        return result;
    }
}
