package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_XresolvPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "xresolv", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_XresolvPK.class)
public final class EN_Xresolv implements Serializable {

    private static final long serialVersionUID = 6897164864713138066L;
    private int entr;
    private short sens;
    private short typ;
    private short ord;
    private String rtxt;
    private String ktxt;
    private Short tsens;
    private String notes;
    private Boolean prio;
    private EN_Kwxref kwxrefByTyp;
    private EN_Sens sens_0;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "ktxt", nullable = true, insertable = true, updatable = true, length = 250)
    public String getKtxt() {
        return ktxt;
    }

    public void setKtxt(String ktxt) {
        this.ktxt = ktxt;
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
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 250)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Id
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
        this.ord = ord;
    }

    @Basic
    @Column(name = "prio", nullable = true, insertable = true, updatable = true)
    public Boolean getPrio() {
        return prio;
    }

    public void setPrio(Boolean prio) {
        this.prio = prio;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 250)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
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
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public EN_Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(EN_Sens sens_0) {
        this.sens_0 = sens_0;
    }

    @Basic
    @Column(name = "tsens", nullable = true, insertable = true, updatable = true)
    public Short getTsens() {
        return tsens;
    }

    public void setTsens(Short tsens) {
        this.tsens = tsens;
    }

    @Id
    @Column(name = "typ", nullable = false, insertable = true, updatable = true)
    public short getTyp() {
        return typ;
    }

    public void setTyp(short typ) {
        this.typ = typ;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Xresolv xresolv = (EN_Xresolv) o;

        if (entr != xresolv.entr) return false;
        if (ord != xresolv.ord) return false;
        if (sens != xresolv.sens) return false;
        if (typ != xresolv.typ) return false;
        if (ktxt != null ? !ktxt.equals(xresolv.ktxt) : xresolv.ktxt != null) return false;
        if (notes != null ? !notes.equals(xresolv.notes) : xresolv.notes != null) return false;
        if (prio != null ? !prio.equals(xresolv.prio) : xresolv.prio != null) return false;
        if (rtxt != null ? !rtxt.equals(xresolv.rtxt) : xresolv.rtxt != null) return false;
        if (tsens != null ? !tsens.equals(xresolv.tsens) : xresolv.tsens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) typ;
        result = 31 * result + (int) ord;
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        result = 31 * result + (tsens != null ? tsens.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (prio != null ? prio.hashCode() : 0);
        return result;
    }
}
