package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.XrefPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(XrefPK.class)
@Table(name = "Xref", schema = "jmdict", catalog = "egd")
public final class Xref implements Serializable {

    private static final long serialVersionUID = 7036779536265441697L;
    private int entr;
    private int sens;
    private int xref;
    private int typ;
    private int xentr;
    private int xsens;
    private Integer rdng;
    private Integer kanj;
    private String notes;
    private Kanj kanj_0;
    private Kwxref kwxrefByTyp;
    private Rdng rdng_0;
    private Sens sensByXrefEntrFkey;
    private Sens sensByXrefXentrFkey;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Integer getKanj() {
        return kanj;
    }

    public void setKanj(Integer kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj")})
    public Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(Kanj kanj_0) {
        this.kanj_0 = kanj_0;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "typ", referencedColumnName = "id", nullable = false)
    public Kwxref getKwxrefByTyp() {
        return kwxrefByTyp;
    }

    public void setKwxrefByTyp(Kwxref kwxrefByTyp) {
        this.kwxrefByTyp = kwxrefByTyp;
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
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Integer getRdng() {
        return rdng;
    }

    public void setRdng(Integer rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng")})
    public Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public Sens getSensByXrefEntrFkey() {
        return sensByXrefEntrFkey;
    }

    public void setSensByXrefEntrFkey(Sens sensByXrefEntrFkey) {
        this.sensByXrefEntrFkey = sensByXrefEntrFkey;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "xsens", referencedColumnName = "sens", nullable = false)})
    public Sens getSensByXrefXentrFkey() {
        return sensByXrefXentrFkey;
    }

    public void setSensByXrefXentrFkey(Sens sensByXrefXentrFkey) {
        this.sensByXrefXentrFkey = sensByXrefXentrFkey;
    }

    @Basic
    @Column(name = "typ", nullable = false, insertable = true, updatable = true)
    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    @Id
    @Column(name = "xentr", nullable = false, insertable = true, updatable = true)
    public int getXentr() {
        return xentr;
    }

    public void setXentr(int xentr) {
        this.xentr = xentr;
    }

    @Id
    @Column(name = "xref", nullable = false, insertable = true, updatable = true)
    public int getXref() {
        return xref;
    }

    public void setXref(int xref) {
        this.xref = xref;
    }

    @Id
    @Column(name = "xsens", nullable = false, insertable = true, updatable = true)
    public int getXsens() {
        return xsens;
    }

    public void setXsens(int xsens) {
        this.xsens = xsens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Xref xref1 = (Xref) o;

        if (entr != xref1.entr) return false;
        if (sens != xref1.sens) return false;
        if (typ != xref1.typ) return false;
        if (xentr != xref1.xentr) return false;
        if (xref != xref1.xref) return false;
        if (xsens != xref1.xsens) return false;
        if (kanj != null ? !kanj.equals(xref1.kanj) : xref1.kanj != null) return false;
        if (notes != null ? !notes.equals(xref1.notes) : xref1.notes != null) return false;
        if (rdng != null ? !rdng.equals(xref1.rdng) : xref1.rdng != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + xref;
        result = 31 * result + typ;
        result = 31 * result + xentr;
        result = 31 * result + xsens;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
