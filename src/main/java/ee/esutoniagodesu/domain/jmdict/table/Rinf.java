package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.RinfPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(RinfPK.class)
@Table(name = "Rinf", schema = "jmdict", catalog = "egd")
public final class Rinf implements Serializable {

    private static final long serialVersionUID = 9069508606439952613L;
    private int entr;
    private int rdng;
    private int ord;
    private int kw;
    private Kwrinf kwrinfByKw;
    private Rdng rdng_0;

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
    public Kwrinf getKwrinfByKw() {
        return kwrinfByKw;
    }

    public void setKwrinfByKw(Kwrinf kwrinfByKw) {
        this.kwrinfByKw = kwrinfByKw;
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
    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    public int getRdng() {
        return rdng;
    }

    public void setRdng(int rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng", nullable = false)})
    public Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rinf rinf = (Rinf) o;

        if (entr != rinf.entr) return false;
        if (kw != rinf.kw) return false;
        if (ord != rinf.ord) return false;
        if (rdng != rinf.rdng) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + rdng;
        result = 31 * result + ord;
        result = 31 * result + kw;
        return result;
    }
}
