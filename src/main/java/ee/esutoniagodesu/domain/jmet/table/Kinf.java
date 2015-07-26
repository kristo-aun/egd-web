package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.KinfPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(KinfPK.class)
@Table(name = "Kinf", schema = "jmet")
public final class Kinf implements Serializable {

    private static final long serialVersionUID = -126420594763605226L;
    private int entr;
    private int kanj;
    private int ord;
    private int kw;
    private Kanj kanj_0;
    private Kwkinf kwkinfByKw;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Id
    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    public int getKanj() {
        return kanj;
    }

    public void setKanj(int kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj", nullable = false)})
    public Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(Kanj kanj_0) {
        this.kanj_0 = kanj_0;
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
    public Kwkinf getKwkinfByKw() {
        return kwkinfByKw;
    }

    public void setKwkinfByKw(Kwkinf kwkinfByKw) {
        this.kwkinfByKw = kwkinfByKw;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kinf kinf = (Kinf) o;

        if (entr != kinf.entr) return false;
        if (kanj != kinf.kanj) return false;
        if (kw != kinf.kw) return false;
        if (ord != kinf.ord) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kanj;
        result = 31 * result + ord;
        result = 31 * result + kw;
        return result;
    }
}
