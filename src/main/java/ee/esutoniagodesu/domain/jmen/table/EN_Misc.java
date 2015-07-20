package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_MiscPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "misc", schema = "jmen", catalog = "egd")
@IdClass(EN_MiscPK.class)
public final class EN_Misc implements Serializable {

    private static final long serialVersionUID = -5538613735013258327L;
    private int entr;
    private short sens;
    private short ord;
    private short kw;
    private EN_Kwmisc kwmiscByKw;
    private EN_Sens sens_0;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Id
    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "kw", referencedColumnName = "id", nullable = false)
    public EN_Kwmisc getKwmiscByKw() {
        return kwmiscByKw;
    }

    public void setKwmiscByKw(EN_Kwmisc kwmiscByKw) {
        this.kwmiscByKw = kwmiscByKw;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
        this.ord = ord;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Misc misc = (EN_Misc) o;

        if (entr != misc.entr) return false;
        if (kw != misc.kw) return false;
        if (ord != misc.ord) return false;
        if (sens != misc.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) ord;
        result = 31 * result + (int) kw;
        return result;
    }
}
