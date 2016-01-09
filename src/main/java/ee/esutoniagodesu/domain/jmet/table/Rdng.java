package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.RdngPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "rdng", schema = "jmet")
@IdClass(RdngPK.class)
public final class Rdng implements Serializable {

    private static final long serialVersionUID = -1410532913674610196L;
    private int entr;
    private short rdng;
    private String txt;
    private Collection<Freq> freqs;
    private Entr entrByEntr;
    private Collection<Rdngsnd> rdngsnds;
    private Collection<Restr> restrs;
    private Collection<Rinf> rinfs;
    private Collection<Stagr> stagrs;
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

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Freq> getFreqs() {
        return freqs;
    }

    public void setFreqs(Collection<Freq> freqs) {
        this.freqs = freqs;
    }

    @Id
    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    public short getRdng() {
        return rdng;
    }

    public void setRdng(short rdng) {
        this.rdng = rdng;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Rdngsnd> getRdngsnds() {
        return rdngsnds;
    }

    public void setRdngsnds(Collection<Rdngsnd> rdngsnds) {
        this.rdngsnds = rdngsnds;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Restr> getRestrs() {
        return restrs;
    }

    public void setRestrs(Collection<Restr> restrs) {
        this.restrs = restrs;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Rinf> getRinfs() {
        return rinfs;
    }

    public void setRinfs(Collection<Rinf> rinfs) {
        this.rinfs = rinfs;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Stagr> getStagrs() {
        return stagrs;
    }

    public void setStagrs(Collection<Stagr> stagrs) {
        this.stagrs = stagrs;
    }

    @Basic
    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 2048)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<Xref> xrefs) {
        this.xrefs = xrefs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rdng rdng1 = (Rdng) o;

        if (entr != rdng1.entr) return false;
        if (rdng != rdng1.rdng) return false;
        if (txt != null ? !txt.equals(rdng1.txt) : rdng1.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) rdng;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
