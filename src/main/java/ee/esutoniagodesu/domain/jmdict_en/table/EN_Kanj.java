package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_KanjPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kanj", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_KanjPK.class)
public final class EN_Kanj implements Serializable {

    private static final long serialVersionUID = 7082462087936702622L;
    private int entr;
    private short kanj;
    private String txt;
    private Collection<EN_Freq> freqs;
    private EN_Entr entrByEntr;
    private Collection<EN_Kinf> kinfs;
    private Collection<EN_Restr> restrs;
    private Collection<EN_Stagk> stagks;
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

    @OneToMany(mappedBy = "kanj_0")
    public Collection<EN_Freq> getFreqs() {
        return freqs;
    }

    public void setFreqs(Collection<EN_Freq> freqs) {
        this.freqs = freqs;
    }

    @Id
    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    public short getKanj() {
        return kanj;
    }

    public void setKanj(short kanj) {
        this.kanj = kanj;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<EN_Kinf> getKinfs() {
        return kinfs;
    }

    public void setKinfs(Collection<EN_Kinf> kinfs) {
        this.kinfs = kinfs;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<EN_Restr> getRestrs() {
        return restrs;
    }

    public void setRestrs(Collection<EN_Restr> restrs) {
        this.restrs = restrs;
    }

    @OneToMany(mappedBy = "kanj_0")
    public Collection<EN_Stagk> getStagks() {
        return stagks;
    }

    public void setStagks(Collection<EN_Stagk> stagks) {
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
    public Collection<EN_Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<EN_Xref> xrefs) {
        this.xrefs = xrefs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Kanj kanj1 = (EN_Kanj) o;

        if (entr != kanj1.entr) return false;
        if (kanj != kanj1.kanj) return false;
        if (txt != null ? !txt.equals(kanj1.txt) : kanj1.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) kanj;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
