package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_RdngPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "rdng", schema = "jmen", catalog = "egd")
@IdClass(EN_RdngPK.class)
public final class EN_Rdng implements Serializable {

    private static final long serialVersionUID = -1410532913674610196L;
    private int entr;
    private short rdng;
    private String txt;
    private Collection<EN_Freq> freqs;
    private EN_Entr entrByEntr;
    private Collection<EN_Rdngsnd> rdngsnds;
    private Collection<EN_Restr> restrs;
    private Collection<EN_Rinf> rinfs;
    private Collection<EN_Stagr> stagrs;
    private Collection<EN_Xref> xrefs;

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
    public EN_Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(EN_Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<EN_Freq> getFreqs() {
        return freqs;
    }

    public void setFreqs(Collection<EN_Freq> freqs) {
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
    public Collection<EN_Rdngsnd> getRdngsnds() {
        return rdngsnds;
    }

    public void setRdngsnds(Collection<EN_Rdngsnd> rdngsnds) {
        this.rdngsnds = rdngsnds;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<EN_Restr> getRestrs() {
        return restrs;
    }

    public void setRestrs(Collection<EN_Restr> restrs) {
        this.restrs = restrs;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<EN_Rinf> getRinfs() {
        return rinfs;
    }

    public void setRinfs(Collection<EN_Rinf> rinfs) {
        this.rinfs = rinfs;
    }

    @OneToMany(mappedBy = "rdng_0")
    public Collection<EN_Stagr> getStagrs() {
        return stagrs;
    }

    public void setStagrs(Collection<EN_Stagr> stagrs) {
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
    public Collection<EN_Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<EN_Xref> xrefs) {
        this.xrefs = xrefs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Rdng rdng1 = (EN_Rdng) o;

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
