package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.MiscPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(MiscPK.class)
@Table(name = "Misc", schema = "jmet")
public final class Misc implements Serializable {

    private static final long serialVersionUID = 2641645610809814864L;
    private int entr;
    private int sens;
    private int ord;
    private int kw;
    private Kwmisc kwmiscByKw;
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
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "kw", referencedColumnName = "id", nullable = false)
    public Kwmisc getKwmiscByKw() {
        return kwmiscByKw;
    }

    public void setKwmiscByKw(Kwmisc kwmiscByKw) {
        this.kwmiscByKw = kwmiscByKw;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
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

        Misc misc = (Misc) o;

        if (entr != misc.entr) return false;
        if (kw != misc.kw) return false;
        if (ord != misc.ord) return false;
        if (sens != misc.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + ord;
        result = 31 * result + kw;
        return result;
    }
}
