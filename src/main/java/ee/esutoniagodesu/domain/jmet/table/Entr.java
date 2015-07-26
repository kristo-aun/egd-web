package ee.esutoniagodesu.domain.jmet.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Entr", schema = "jmet")
public final class Entr implements Serializable {

    private static final long serialVersionUID = 9199636571604968007L;

    private int id;
    private int src;
    private int stat;
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
    private Chr chr;
    @JsonIgnore
    private Entr entrByDfrm;
    @JsonIgnore
    private Collection<Entr> entrs;
    @JsonIgnore
    private Kwsrc kwsrcBySrc;
    @JsonIgnore
    private Kwstat kwstatByStat;
    @JsonIgnore
    private Collection<Entrsnd> entrsnds;
    @JsonIgnore
    private Collection<Grp> grps;
    @JsonIgnore
    private Collection<Hist> hists;

    private Collection<Kanj> kanjs;
    @JsonIgnore
    private Collection<Kresolv> kresolvs;

    private Collection<Rdng> rdngs;
    private Collection<Sens> senses;

    @OneToOne(mappedBy = "entrByEntr")
    public Chr getChr() {
        return chr;
    }

    public void setChr(Chr chr) {
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
    public Entr getEntrByDfrm() {
        return entrByDfrm;
    }

    public void setEntrByDfrm(Entr entrByDfrm) {
        this.entrByDfrm = entrByDfrm;
    }

    @OneToMany(mappedBy = "entrByDfrm")
    public Collection<Entr> getEntrs() {
        return entrs;
    }

    public void setEntrs(Collection<Entr> entrs) {
        this.entrs = entrs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<Entrsnd> getEntrsnds() {
        return entrsnds;
    }

    public void setEntrsnds(Collection<Entrsnd> entrsnds) {
        this.entrsnds = entrsnds;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<Grp> getGrps() {
        return grps;
    }

    public void setGrps(Collection<Grp> grps) {
        this.grps = grps;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<Hist> getHists() {
        return hists;
    }

    public void setHists(Collection<Hist> hists) {
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
    public Collection<Kanj> getKanjs() {
        return kanjs;
    }

    public void setKanjs(Collection<Kanj> kanjs) {
        this.kanjs = kanjs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<Kresolv> getKresolvs() {
        return kresolvs;
    }

    public void setKresolvs(Collection<Kresolv> kresolvs) {
        this.kresolvs = kresolvs;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "src", referencedColumnName = "id", nullable = false)
    public Kwsrc getKwsrcBySrc() {
        return kwsrcBySrc;
    }

    public void setKwsrcBySrc(Kwsrc kwsrcBySrc) {
        this.kwsrcBySrc = kwsrcBySrc;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "stat", referencedColumnName = "id", nullable = false)
    public Kwstat getKwstatByStat() {
        return kwstatByStat;
    }

    public void setKwstatByStat(Kwstat kwstatByStat) {
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
    public Collection<Rdng> getRdngs() {
        return rdngs;
    }

    public void setRdngs(Collection<Rdng> rdngs) {
        this.rdngs = rdngs;
    }

    @OneToMany(mappedBy = "entrByEntr")
    public Collection<Sens> getSenses() {
        return senses;
    }

    public void setSenses(Collection<Sens> senses) {
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
    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
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
    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
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

        Entr entr = (Entr) o;

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
        result = 31 * result + src;
        result = 31 * result + stat;
        result = 31 * result + (int) (seq ^ (seq >>> 32));
        result = 31 * result + (dfrm != null ? dfrm.hashCode() : 0);
        result = 31 * result + (unap ? 1 : 0);
        result = 31 * result + (srcnote != null ? srcnote.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
