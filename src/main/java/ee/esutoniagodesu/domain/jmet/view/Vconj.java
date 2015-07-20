package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vconj", schema = "jmet", catalog = "egd")
public final class Vconj implements Serializable {

    private static final long serialVersionUID = 7785469709605138927L;
    private Short pos;
    private String ptxt;
    private Short conj;
    private String ctxt;
    private Boolean neg;
    private Boolean fml;

    @Id
    @Basic
    @Column(name = "conj", nullable = true, insertable = true, updatable = true)
    public Short getConj() {
        return conj;
    }

    public void setConj(Short conj) {
        this.conj = conj;
    }

    @Basic
    @Column(name = "ctxt", nullable = true, insertable = true, updatable = true, length = 50)
    public String getCtxt() {
        return ctxt;
    }

    public void setCtxt(String ctxt) {
        this.ctxt = ctxt;
    }

    @Basic
    @Column(name = "fml", nullable = true, insertable = true, updatable = true)
    public Boolean getFml() {
        return fml;
    }

    public void setFml(Boolean fml) {
        this.fml = fml;
    }

    @Basic
    @Column(name = "neg", nullable = true, insertable = true, updatable = true)
    public Boolean getNeg() {
        return neg;
    }

    public void setNeg(Boolean neg) {
        this.neg = neg;
    }

    @Basic
    @Column(name = "pos", nullable = true, insertable = true, updatable = true)
    public Short getPos() {
        return pos;
    }

    public void setPos(Short pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "ptxt", nullable = true, insertable = true, updatable = true, length = 20)
    public String getPtxt() {
        return ptxt;
    }

    public void setPtxt(String ptxt) {
        this.ptxt = ptxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vconj vconj = (Vconj) o;

        if (conj != null ? !conj.equals(vconj.conj) : vconj.conj != null) return false;
        if (ctxt != null ? !ctxt.equals(vconj.ctxt) : vconj.ctxt != null) return false;
        if (fml != null ? !fml.equals(vconj.fml) : vconj.fml != null) return false;
        if (neg != null ? !neg.equals(vconj.neg) : vconj.neg != null) return false;
        if (pos != null ? !pos.equals(vconj.pos) : vconj.pos != null) return false;
        if (ptxt != null ? !ptxt.equals(vconj.ptxt) : vconj.ptxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + (ptxt != null ? ptxt.hashCode() : 0);
        result = 31 * result + (conj != null ? conj.hashCode() : 0);
        result = 31 * result + (ctxt != null ? ctxt.hashCode() : 0);
        result = 31 * result + (neg != null ? neg.hashCode() : 0);
        result = 31 * result + (fml != null ? fml.hashCode() : 0);
        return result;
    }
}
