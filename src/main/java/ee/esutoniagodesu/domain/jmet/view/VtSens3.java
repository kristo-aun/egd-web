package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_sens3", schema = "jmet", catalog = "egd")
public final class VtSens3 implements Serializable {

    private static final long serialVersionUID = 3249552435786066422L;
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

        VtSens3 vtSens3 = (VtSens3) o;

        if (entr != null ? !entr.equals(vtSens3.entr) : vtSens3.entr != null) return false;
        if (stxt != null ? !stxt.equals(vtSens3.stxt) : vtSens3.stxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (stxt != null ? stxt.hashCode() : 0);
        return result;
    }
}
