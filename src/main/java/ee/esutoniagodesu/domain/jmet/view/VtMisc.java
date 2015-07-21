package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_misc", schema = "jmet")
public final class VtMisc implements Serializable {

    private static final long serialVersionUID = 3562279279040281682L;
    private Integer entr;
    private Short sens;
    private String mtxt;

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
    @Column(name = "mtxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getMtxt() {
        return mtxt;
    }

    public void setMtxt(String mtxt) {
        this.mtxt = mtxt;
    }

    @Basic
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Short getSens() {
        return sens;
    }

    public void setSens(Short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VtMisc vtMisc = (VtMisc) o;

        if (entr != null ? !entr.equals(vtMisc.entr) : vtMisc.entr != null) return false;
        if (mtxt != null ? !mtxt.equals(vtMisc.mtxt) : vtMisc.mtxt != null) return false;
        if (sens != null ? !sens.equals(vtMisc.sens) : vtMisc.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (mtxt != null ? mtxt.hashCode() : 0);
        return result;
    }
}
