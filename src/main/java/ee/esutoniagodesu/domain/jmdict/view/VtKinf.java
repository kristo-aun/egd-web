package ee.esutoniagodesu.domain.jmdict.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_kinf", schema = "jmdict", catalog = "egd")
public final class VtKinf implements Serializable {

    private static final long serialVersionUID = 7182592965153230166L;

    private Integer entr;
    private Integer kanj;
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
    public Integer getKanj() {
        return kanj;
    }

    public void setKanj(Integer kanj) {
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

        VtKinf vtKinf = (VtKinf) o;

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
