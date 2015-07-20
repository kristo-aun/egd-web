package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "esum", schema = "jmet", catalog = "egd")
public final class Esum implements Serializable {

    private static final long serialVersionUID = 5151580085635653399L;
    private Integer id;
    private Long seq;
    private Short stat;
    private Short src;
    private Integer dfrm;
    private Boolean unap;
    private String notes;
    private String srcnote;
    private String rdng;
    private String kanj;
    private String gloss;
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

    @Basic
    @Column(name = "gloss", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getKanj() {
        return kanj;
    }

    public void setKanj(String kanj) {
        this.kanj = kanj;
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
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRdng() {
        return rdng;
    }

    public void setRdng(String rdng) {
        this.rdng = rdng;
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

        Esum esum = (Esum) o;

        if (dfrm != null ? !dfrm.equals(esum.dfrm) : esum.dfrm != null) return false;
        if (gloss != null ? !gloss.equals(esum.gloss) : esum.gloss != null) return false;
        if (id != null ? !id.equals(esum.id) : esum.id != null) return false;
        if (kanj != null ? !kanj.equals(esum.kanj) : esum.kanj != null) return false;
        if (notes != null ? !notes.equals(esum.notes) : esum.notes != null) return false;
        if (nsens != null ? !nsens.equals(esum.nsens) : esum.nsens != null) return false;
        if (p != null ? !p.equals(esum.p) : esum.p != null) return false;
        if (rdng != null ? !rdng.equals(esum.rdng) : esum.rdng != null) return false;
        if (seq != null ? !seq.equals(esum.seq) : esum.seq != null) return false;
        if (src != null ? !src.equals(esum.src) : esum.src != null) return false;
        if (srcnote != null ? !srcnote.equals(esum.srcnote) : esum.srcnote != null) return false;
        if (stat != null ? !stat.equals(esum.stat) : esum.stat != null) return false;
        if (unap != null ? !unap.equals(esum.unap) : esum.unap != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (dfrm != null ? dfrm.hashCode() : 0);
        result = 31 * result + (unap != null ? unap.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (srcnote != null ? srcnote.hashCode() : 0);
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        result = 31 * result + (nsens != null ? nsens.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        return result;
    }
}
