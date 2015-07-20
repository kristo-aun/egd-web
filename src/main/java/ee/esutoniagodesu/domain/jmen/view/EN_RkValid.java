package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "rk_valid", schema = "jmen", catalog = "egd")
public final class EN_RkValid implements Serializable {

    private static final long serialVersionUID = -4090597414572971187L;
    private Integer entr;
    private Short rdng;
    private String rtxt;
    private Short kanj;
    private String ktxt;

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
    @Column(name = "ktxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getKtxt() {
        return ktxt;
    }

    public void setKtxt(String ktxt) {
        this.ktxt = ktxt;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Short getRdng() {
        return rdng;
    }

    public void setRdng(Short rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_RkValid rkValid = (EN_RkValid) o;

        if (entr != null ? !entr.equals(rkValid.entr) : rkValid.entr != null) return false;
        if (kanj != null ? !kanj.equals(rkValid.kanj) : rkValid.kanj != null) return false;
        if (ktxt != null ? !ktxt.equals(rkValid.ktxt) : rkValid.ktxt != null) return false;
        if (rdng != null ? !rdng.equals(rkValid.rdng) : rkValid.rdng != null) return false;
        if (rtxt != null ? !rtxt.equals(rkValid.rtxt) : rkValid.rtxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        return result;
    }
}
