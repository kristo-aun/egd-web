package ee.esutoniagodesu.domain.jmen.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "entr", schema = "jmen")
public final class EN_Entr implements Serializable {

    private static final long serialVersionUID = 932747108408957400L;
    private int id;
    private short src;
    private short stat;
    @JsonIgnore
    private long seq;
    private Integer dfrm;
    @JsonIgnore
    private boolean unap;
    @JsonIgnore
    private String srcnote;
    @JsonIgnore
    private String notes;
    @JsonIgnore
    private EN_Chr chr;
    @JsonIgnore
    private EN_Entr entrByDfrm;
    @JsonIgnore
    private Collection<EN_Entr> entrs;
    @JsonIgnore
    private EN_Kwsrc kwsrcBySrc;
    @JsonIgnore
    private EN_Kwstat kwstatByStat;
    @JsonIgnore
    private Collection<EN_Entrsnd> entrsnds;
    @JsonIgnore
    private Collection<EN_Grp> grps;
    @JsonIgnore
    private Collection<EN_Hist> hists;
    @JsonIgnore
    private Collection<EN_Kresolv> kresolvs;


    private Collection<EN_Kanj> kanjs;
    private Collection<EN_Rdng> rdngs;
    private Collection<EN_Sens> senses;

    @OneToOne(mappedBy = "entrByEntr")
    public EN_Chr getChr() {
        return chr;
    }

    public void setChr(EN_Chr chr) {
        this.chr = chr;
    }

    @Basic
    @Column(name = "dfrm", nullable = true, insertable = true, updatable = true)
    public Integer getDfrm() {
        return dfrm;
    }

    public void setDfrm(Integer dfrm) {
        this.dfrm = dfrm;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "dfrm", referencedColumnName = "id")
    public EN_Entr getEntrByDfrm() {
        return entrByDfrm;
    }

    public void setEntrByDfrm(EN_Entr entrByDfrm) {
        this.entrByDfrm = entrByDfrm;
    }

    @OneToMany(mappedBy = "entrByDfrm")
    public Collection<EN_Entr> getEntrs() {
        return entrs;
    }

    public void setEntrs(Collection<EN_Entr> entrs) {
        this.entrs = entrs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Entrsnd> getEntrsnds() {
        return entrsnds;
    }

    public void setEntrsnds(Collection<EN_Entrsnd> entrsnds) {
        this.entrsnds = entrsnds;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Grp> getGrps() {
        return grps;
    }

    public void setGrps(Collection<EN_Grp> grps) {
        this.grps = grps;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Hist> getHists() {
        return hists;
    }

    public void setHists(Collection<EN_Hist> hists) {
        this.hists = hists;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Kanj> getKanjs() {
        return kanjs;
    }

    public void setKanjs(Collection<EN_Kanj> kanjs) {
        this.kanjs = kanjs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Kresolv> getKresolvs() {
        return kresolvs;
    }

    public void setKresolvs(Collection<EN_Kresolv> kresolvs) {
        this.kresolvs = kresolvs;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "src", referencedColumnName = "id", nullable = false)
    public EN_Kwsrc getKwsrcBySrc() {
        return kwsrcBySrc;
    }

    public void setKwsrcBySrc(EN_Kwsrc kwsrcBySrc) {
        this.kwsrcBySrc = kwsrcBySrc;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "stat", referencedColumnName = "id", nullable = false)
    public EN_Kwstat getKwstatByStat() {
        return kwstatByStat;
    }

    public void setKwstatByStat(EN_Kwstat kwstatByStat) {
        this.kwstatByStat = kwstatByStat;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Rdng> getRdngs() {
        return rdngs;
    }

    public void setRdngs(Collection<EN_Rdng> rdngs) {
        this.rdngs = rdngs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<EN_Sens> getSenses() {
        return senses;
    }

    public void setSenses(Collection<EN_Sens> senses) {
        this.senses = senses;
    }

    @Basic
    @Column(name = "seq", nullable = false, insertable = true, updatable = true)
    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "src", nullable = false, insertable = true, updatable = true)
    public short getSrc() {
        return src;
    }

    public void setSrc(short src) {
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
    @Column(name = "stat", nullable = false, insertable = true, updatable = true)
    public short getStat() {
        return stat;
    }

    public void setStat(short stat) {
        this.stat = stat;
    }

    @Basic
    @Column(name = "unap", nullable = false, insertable = true, updatable = true)
    public boolean isUnap() {
        return unap;
    }

    public void setUnap(boolean unap) {
        this.unap = unap;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Entr entr = (EN_Entr) o;

        if (id != entr.id) return false;
        if (seq != entr.seq) return false;
        if (src != entr.src) return false;
        if (stat != entr.stat) return false;
        if (unap != entr.unap) return false;
        if (dfrm != null ? !dfrm.equals(entr.dfrm) : entr.dfrm != null) return false;
        if (notes != null ? !notes.equals(entr.notes) : entr.notes != null) return false;
        if (srcnote != null ? !srcnote.equals(entr.srcnote) : entr.srcnote != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (int) src;
        result = 31 * result + (int) stat;
        result = 31 * result + (int) (seq ^ (seq >>> 32));
        result = 31 * result + (dfrm != null ? dfrm.hashCode() : 0);
        result = 31 * result + (unap ? 1 : 0);
        result = 31 * result + (srcnote != null ? srcnote.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
