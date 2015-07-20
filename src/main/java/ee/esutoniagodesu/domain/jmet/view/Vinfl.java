package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vinfl", schema = "jmet", catalog = "egd")
public final class Vinfl implements Serializable {

    private static final long serialVersionUID = -6254646034207852626L;
    private Integer id;
    private Long seq;
    private Short src;
    private Boolean unap;
    private Short pos;
    private String ptxt;
    private Short knum;
    private String ktxt;
    private Short rnum;
    private String rtxt;
    private Short conj;
    private String ctxt;
    private Boolean neg;
    private Boolean fml;
    private String t;
    private Short onum;
    private String kitxt;
    private String ritxt;

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

    @Id
    @Basic
    @Column(name = "id", nullable = true, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "kitxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getKitxt() {
        return kitxt;
    }

    public void setKitxt(String kitxt) {
        this.kitxt = kitxt;
    }

    @Basic
    @Column(name = "knum", nullable = true, insertable = true, updatable = true)
    public Short getKnum() {
        return knum;
    }

    public void setKnum(Short knum) {
        this.knum = knum;
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
    @Column(name = "neg", nullable = true, insertable = true, updatable = true)
    public Boolean getNeg() {
        return neg;
    }

    public void setNeg(Boolean neg) {
        this.neg = neg;
    }

    @Basic
    @Column(name = "onum", nullable = true, insertable = true, updatable = true)
    public Short getOnum() {
        return onum;
    }

    public void setOnum(Short onum) {
        this.onum = onum;
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

    @Basic
    @Column(name = "ritxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getRitxt() {
        return ritxt;
    }

    public void setRitxt(String ritxt) {
        this.ritxt = ritxt;
    }

    @Basic
    @Column(name = "rnum", nullable = true, insertable = true, updatable = true)
    public Short getRnum() {
        return rnum;
    }

    public void setRnum(Short rnum) {
        this.rnum = rnum;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
    }

    @Basic
    @Column(name = "seq", nullable = true, insertable = true, updatable = true)
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "src", nullable = true, insertable = true, updatable = true)
    public Short getSrc() {
        return src;
    }

    public void setSrc(Short src) {
        this.src = src;
    }

    @Basic
    @Column(name = "t", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    @Basic
    @Column(name = "unap", nullable = true, insertable = true, updatable = true)
    public Boolean getUnap() {
        return unap;
    }

    public void setUnap(Boolean unap) {
        this.unap = unap;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vinfl vinfl = (Vinfl) o;

        if (conj != null ? !conj.equals(vinfl.conj) : vinfl.conj != null) return false;
        if (ctxt != null ? !ctxt.equals(vinfl.ctxt) : vinfl.ctxt != null) return false;
        if (fml != null ? !fml.equals(vinfl.fml) : vinfl.fml != null) return false;
        if (id != null ? !id.equals(vinfl.id) : vinfl.id != null) return false;
        if (kitxt != null ? !kitxt.equals(vinfl.kitxt) : vinfl.kitxt != null) return false;
        if (knum != null ? !knum.equals(vinfl.knum) : vinfl.knum != null) return false;
        if (ktxt != null ? !ktxt.equals(vinfl.ktxt) : vinfl.ktxt != null) return false;
        if (neg != null ? !neg.equals(vinfl.neg) : vinfl.neg != null) return false;
        if (onum != null ? !onum.equals(vinfl.onum) : vinfl.onum != null) return false;
        if (pos != null ? !pos.equals(vinfl.pos) : vinfl.pos != null) return false;
        if (ptxt != null ? !ptxt.equals(vinfl.ptxt) : vinfl.ptxt != null) return false;
        if (ritxt != null ? !ritxt.equals(vinfl.ritxt) : vinfl.ritxt != null) return false;
        if (rnum != null ? !rnum.equals(vinfl.rnum) : vinfl.rnum != null) return false;
        if (rtxt != null ? !rtxt.equals(vinfl.rtxt) : vinfl.rtxt != null) return false;
        if (seq != null ? !seq.equals(vinfl.seq) : vinfl.seq != null) return false;
        if (src != null ? !src.equals(vinfl.src) : vinfl.src != null) return false;
        if (t != null ? !t.equals(vinfl.t) : vinfl.t != null) return false;
        if (unap != null ? !unap.equals(vinfl.unap) : vinfl.unap != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (unap != null ? unap.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (ptxt != null ? ptxt.hashCode() : 0);
        result = 31 * result + (knum != null ? knum.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        result = 31 * result + (rnum != null ? rnum.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (conj != null ? conj.hashCode() : 0);
        result = 31 * result + (ctxt != null ? ctxt.hashCode() : 0);
        result = 31 * result + (neg != null ? neg.hashCode() : 0);
        result = 31 * result + (fml != null ? fml.hashCode() : 0);
        result = 31 * result + (t != null ? t.hashCode() : 0);
        result = 31 * result + (onum != null ? onum.hashCode() : 0);
        result = 31 * result + (kitxt != null ? kitxt.hashCode() : 0);
        result = 31 * result + (ritxt != null ? ritxt.hashCode() : 0);
        return result;
    }
}
