package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vinflxt", schema = "jmen", catalog = "egd")
public final class EN_Vinflxt implements Serializable {

    private static final long serialVersionUID = -3185778213246268205L;
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
    private String w0;
    private String w1;
    private String w2;
    private String w3;

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
    @Column(name = "unap", nullable = true, insertable = true, updatable = true)
    public Boolean getUnap() {
        return unap;
    }

    public void setUnap(Boolean unap) {
        this.unap = unap;
    }

    @Basic
    @Column(name = "w0", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getW0() {
        return w0;
    }

    public void setW0(String w0) {
        this.w0 = w0;
    }

    @Basic
    @Column(name = "w1", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getW1() {
        return w1;
    }

    public void setW1(String w1) {
        this.w1 = w1;
    }

    @Basic
    @Column(name = "w2", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getW2() {
        return w2;
    }

    public void setW2(String w2) {
        this.w2 = w2;
    }

    @Basic
    @Column(name = "w3", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getW3() {
        return w3;
    }

    public void setW3(String w3) {
        this.w3 = w3;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Vinflxt vinflxt = (EN_Vinflxt) o;

        if (conj != null ? !conj.equals(vinflxt.conj) : vinflxt.conj != null) return false;
        if (ctxt != null ? !ctxt.equals(vinflxt.ctxt) : vinflxt.ctxt != null) return false;
        if (id != null ? !id.equals(vinflxt.id) : vinflxt.id != null) return false;
        if (knum != null ? !knum.equals(vinflxt.knum) : vinflxt.knum != null) return false;
        if (ktxt != null ? !ktxt.equals(vinflxt.ktxt) : vinflxt.ktxt != null) return false;
        if (pos != null ? !pos.equals(vinflxt.pos) : vinflxt.pos != null) return false;
        if (ptxt != null ? !ptxt.equals(vinflxt.ptxt) : vinflxt.ptxt != null) return false;
        if (rnum != null ? !rnum.equals(vinflxt.rnum) : vinflxt.rnum != null) return false;
        if (rtxt != null ? !rtxt.equals(vinflxt.rtxt) : vinflxt.rtxt != null) return false;
        if (seq != null ? !seq.equals(vinflxt.seq) : vinflxt.seq != null) return false;
        if (src != null ? !src.equals(vinflxt.src) : vinflxt.src != null) return false;
        if (unap != null ? !unap.equals(vinflxt.unap) : vinflxt.unap != null) return false;
        if (w0 != null ? !w0.equals(vinflxt.w0) : vinflxt.w0 != null) return false;
        if (w1 != null ? !w1.equals(vinflxt.w1) : vinflxt.w1 != null) return false;
        if (w2 != null ? !w2.equals(vinflxt.w2) : vinflxt.w2 != null) return false;
        if (w3 != null ? !w3.equals(vinflxt.w3) : vinflxt.w3 != null) return false;

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
        result = 31 * result + (w0 != null ? w0.hashCode() : 0);
        result = 31 * result + (w1 != null ? w1.hashCode() : 0);
        result = 31 * result + (w2 != null ? w2.hashCode() : 0);
        result = 31 * result + (w3 != null ? w3.hashCode() : 0);
        return result;
    }
}
