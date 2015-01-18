package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_PosPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "pos", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_PosPK.class)
public final class EN_Pos implements Serializable {

    private static final long serialVersionUID = 1471939619350073826L;
    private int entr;
    private short sens;
    private short ord;
    private short kw;
    private EN_Kwpos kwposByKw;
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
    public EN_Kwpos getKwposByKw() {
        return kwposByKw;
    }

    public void setKwposByKw(EN_Kwpos kwposByKw) {
        this.kwposByKw = kwposByKw;
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

        EN_Pos pos = (EN_Pos) o;

        if (entr != pos.entr) return false;
        if (kw != pos.kw) return false;
        if (ord != pos.ord) return false;
        if (sens != pos.sens) return false;

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
