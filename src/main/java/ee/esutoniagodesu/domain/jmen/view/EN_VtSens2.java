package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_sens2", schema = "jmen", catalog = "egd")
public final class EN_VtSens2 implements Serializable {

    private static final long serialVersionUID = 4289025549712077631L;
    private Integer entr;
    private String stxt;

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
    @Column(name = "stxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getStxt() {
        return stxt;
    }

    public void setStxt(String stxt) {
        this.stxt = stxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_VtSens2 vtSens2 = (EN_VtSens2) o;

        if (entr != null ? !entr.equals(vtSens2.entr) : vtSens2.entr != null) return false;
        if (stxt != null ? !stxt.equals(vtSens2.stxt) : vtSens2.stxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (stxt != null ? stxt.hashCode() : 0);
        return result;
    }
}
