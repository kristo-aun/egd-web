package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_kinf", schema = "jmen")
public final class EN_VtKinf implements Serializable {

    private static final long serialVersionUID = -6553144566667102469L;
    private Integer entr;
    private Short kanj;
    private String kitxt;

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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Short getKanj() {
        return kanj;
    }

    public void setKanj(Short kanj) {
        this.kanj = kanj;
    }

    @Basic
    @Column(name = "kitxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getKitxt() {
        return kitxt;
    }

    public void setKitxt(String kitxt) {
        this.kitxt = kitxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_VtKinf vtKinf = (EN_VtKinf) o;

        if (entr != null ? !entr.equals(vtKinf.entr) : vtKinf.entr != null) return false;
        if (kanj != null ? !kanj.equals(vtKinf.kanj) : vtKinf.kanj != null) return false;
        if (kitxt != null ? !kitxt.equals(vtKinf.kitxt) : vtKinf.kitxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (kitxt != null ? kitxt.hashCode() : 0);
        return result;
    }
}
