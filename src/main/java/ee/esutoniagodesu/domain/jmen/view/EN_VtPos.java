package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_pos", schema = "jmen", catalog = "egd")
public final class EN_VtPos implements Serializable {

    private static final long serialVersionUID = -7486929933498389919L;
    private Integer entr;
    private Short sens;
    private String ptxt;

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
    @Column(name = "ptxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getPtxt() {
        return ptxt;
    }

    public void setPtxt(String ptxt) {
        this.ptxt = ptxt;
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

        EN_VtPos vtPos = (EN_VtPos) o;

        if (entr != null ? !entr.equals(vtPos.entr) : vtPos.entr != null) return false;
        if (ptxt != null ? !ptxt.equals(vtPos.ptxt) : vtPos.ptxt != null) return false;
        if (sens != null ? !sens.equals(vtPos.sens) : vtPos.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (ptxt != null ? ptxt.hashCode() : 0);
        return result;
    }
}
