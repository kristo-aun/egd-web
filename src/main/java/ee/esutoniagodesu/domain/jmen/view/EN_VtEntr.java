package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_entr", schema = "jmen", catalog = "egd")
public final class EN_VtEntr implements Serializable {

    private static final long serialVersionUID = -7432977713930816756L;
    private Integer id;
    private Short src;
    private Short stat;
    private Long seq;
    private Integer dfrm;
    private Boolean unap;
    private String srcnote;
    private String notes;
    private String rtxt;
    private String ktxt;
    private String stxt;
    private Long nsens;
    private Boolean p;

    @Basic
    @Column(name = "dfrm", nullable = true, insertable = true, updatable = true)
    public Integer getDfrm() {
        return dfrm;
    }

    public void setDfrm(Integer dfrm) {
        this.dfrm = dfrm;
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
    @Column(name = "ktxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getKtxt() {
        return ktxt;
    }

    public void setKtxt(String ktxt) {
        this.ktxt = ktxt;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "nsens", nullable = true, insertable = true, updatable = true)
    public Long getNsens() {
        return nsens;
    }

    public void setNsens(Long nsens) {
        this.nsens = nsens;
    }

    @Basic
    @Column(name = "p", nullable = true, insertable = true, updatable = true)
    public Boolean getP() {
        return p;
    }

    public void setP(Boolean p) {
        this.p = p;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
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
    @Column(name = "srcnote", nullable = true, insertable = true, updatable = true, length = 255)
    public String getSrcnote() {
        return srcnote;
    }

    public void setSrcnote(String srcnote) {
        this.srcnote = srcnote;
    }

    @Basic
    @Column(name = "stat", nullable = true, insertable = true, updatable = true)
    public Short getStat() {
        return stat;
    }

    public void setStat(Short stat) {
        this.stat = stat;
    }

    @Basic
    @Column(name = "stxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getStxt() {
        return stxt;
    }

    public void setStxt(String stxt) {
        this.stxt = stxt;
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

        EN_VtEntr vtEntr = (EN_VtEntr) o;

        if (dfrm != null ? !dfrm.equals(vtEntr.dfrm) : vtEntr.dfrm != null) return false;
        if (id != null ? !id.equals(vtEntr.id) : vtEntr.id != null) return false;
        if (ktxt != null ? !ktxt.equals(vtEntr.ktxt) : vtEntr.ktxt != null) return false;
        if (notes != null ? !notes.equals(vtEntr.notes) : vtEntr.notes != null) return false;
        if (nsens != null ? !nsens.equals(vtEntr.nsens) : vtEntr.nsens != null) return false;
        if (p != null ? !p.equals(vtEntr.p) : vtEntr.p != null) return false;
        if (rtxt != null ? !rtxt.equals(vtEntr.rtxt) : vtEntr.rtxt != null) return false;
        if (seq != null ? !seq.equals(vtEntr.seq) : vtEntr.seq != null) return false;
        if (src != null ? !src.equals(vtEntr.src) : vtEntr.src != null) return false;
        if (srcnote != null ? !srcnote.equals(vtEntr.srcnote) : vtEntr.srcnote != null) return false;
        if (stat != null ? !stat.equals(vtEntr.stat) : vtEntr.stat != null) return false;
        if (stxt != null ? !stxt.equals(vtEntr.stxt) : vtEntr.stxt != null) return false;
        if (unap != null ? !unap.equals(vtEntr.unap) : vtEntr.unap != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (dfrm != null ? dfrm.hashCode() : 0);
        result = 31 * result + (unap != null ? unap.hashCode() : 0);
        result = 31 * result + (srcnote != null ? srcnote.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        result = 31 * result + (stxt != null ? stxt.hashCode() : 0);
        result = 31 * result + (nsens != null ? nsens.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        return result;
    }
}
