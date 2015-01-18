package ee.esutoniagodesu.domain.jmdict.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_pos", schema = "jmdict", catalog = "egd")
public final class VtPos implements Serializable {

    private static final long serialVersionUID = 4381453926235116330L;

    private Integer entr;
    private Integer sens;
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
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VtPos vtPos = (VtPos) o;

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
