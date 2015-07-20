package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.FldPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "fld", schema = "jmet", catalog = "egd")
@IdClass(FldPK.class)
public final class Fld implements Serializable {

    private static final long serialVersionUID = -1797095969072461001L;
    private int entr;
    private short sens;
    private short ord;
    private short kw;
    private Kwfld kwfldByKw;
    private Sens sens_0;

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
    public Kwfld getKwfldByKw() {
        return kwfldByKw;
    }

    public void setKwfldByKw(Kwfld kwfldByKw) {
        this.kwfldByKw = kwfldByKw;
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
    public Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(Sens sens_0) {
        this.sens_0 = sens_0;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fld fld = (Fld) o;

        if (entr != fld.entr) return false;
        if (kw != fld.kw) return false;
        if (ord != fld.ord) return false;
        if (sens != fld.sens) return false;

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
