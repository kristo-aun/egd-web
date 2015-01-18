package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_XrefPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "xref", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_XrefPK.class)
public final class EN_Xref implements Serializable {

    private static final long serialVersionUID = -5275300412997264918L;
    private int entr;
    private short sens;
    private short xref;
    private short typ;
    private int xentr;
    private short xsens;
    private Short rdng;
    private Short kanj;
    private String notes;
    private EN_Kanj kanj_0;
    private EN_Kwxref kwxrefByTyp;
    private EN_Rdng rdng_0;
    private EN_Sens sensByXrefEntrFkey;
    private EN_Sens sensByXrefXentrFkey;

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
    public Short getKanj() {
        return kanj;
    }

    public void setKanj(Short kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj")})
    public EN_Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(EN_Kanj kanj_0) {
        this.kanj_0 = kanj_0;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "typ", referencedColumnName = "id", nullable = false)
    public EN_Kwxref getKwxrefByTyp() {
        return kwxrefByTyp;
    }

    public void setKwxrefByTyp(EN_Kwxref kwxrefByTyp) {
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
    public Short getRdng() {
        return rdng;
    }

    public void setRdng(Short rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng")})
    public EN_Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(EN_Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public EN_Sens getSensByXrefEntrFkey() {
        return sensByXrefEntrFkey;
    }

    public void setSensByXrefEntrFkey(EN_Sens sensByXrefEntrFkey) {
        this.sensByXrefEntrFkey = sensByXrefEntrFkey;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "xentr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "xsens", referencedColumnName = "sens", nullable = false)})
    public EN_Sens getSensByXrefXentrFkey() {
        return sensByXrefXentrFkey;
    }

    public void setSensByXrefXentrFkey(EN_Sens sensByXrefXentrFkey) {
        this.sensByXrefXentrFkey = sensByXrefXentrFkey;
    }

    @Basic
    @Column(name = "typ", nullable = false, insertable = true, updatable = true)
    public short getTyp() {
        return typ;
    }

    public void setTyp(short typ) {
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
    public short getXref() {
        return xref;
    }

    public void setXref(short xref) {
        this.xref = xref;
    }

    @Id
    @Column(name = "xsens", nullable = false, insertable = true, updatable = true)
    public short getXsens() {
        return xsens;
    }

    public void setXsens(short xsens) {
        this.xsens = xsens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Xref xref1 = (EN_Xref) o;

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
        result = 31 * result + (int) sens;
        result = 31 * result + (int) xref;
        result = 31 * result + (int) typ;
        result = 31 * result + xentr;
        result = 31 * result + (int) xsens;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
