package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.KanjPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@IdClass(KanjPK.class)
@Table(name = "Kanj", schema = "jmdict", catalog = "egd")
public final class Kanj implements Serializable {

    private static final long serialVersionUID = 3093564061974227569L;
    private int entr;
    private int kanj;
    private String txt;
    private Collection<Freq> freqs;
    private Entr entrByEntr;
    private Collection<Kinf> kinfs;
    private Collection<Restr> restrs;
    private Collection<Stagk> stagks;
    private Collection<Xref> xrefs;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "id", nullable = false)
    public Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<Freq> getFreqs() {
        return freqs;
    }

    public void setFreqs(Collection<Freq> freqs) {
        this.freqs = freqs;
    }

    @Id
    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    public int getKanj() {
        return kanj;
    }

    public void setKanj(int kanj) {
        this.kanj = kanj;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<Kinf> getKinfs() {
        return kinfs;
    }

    public void setKinfs(Collection<Kinf> kinfs) {
        this.kinfs = kinfs;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<Restr> getRestrs() {
        return restrs;
    }

    public void setRestrs(Collection<Restr> restrs) {
        this.restrs = restrs;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<Stagk> getStagks() {
        return stagks;
    }

    public void setStagks(Collection<Stagk> stagks) {
        this.stagks = stagks;
    }

    @Basic
    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 2048)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<Xref> xrefs) {
        this.xrefs = xrefs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kanj kanj1 = (Kanj) o;

        if (entr != kanj1.entr) return false;
        if (kanj != kanj1.kanj) return false;
        if (txt != null ? !txt.equals(kanj1.txt) : kanj1.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kanj;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
