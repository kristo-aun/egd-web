package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.KinfPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "kinf", schema = "jmet")
@IdClass(KinfPK.class)
public final class Kinf implements Serializable {

    private static final long serialVersionUID = 7828108989997341322L;
    private int entr;
    private short kanj;
    private short ord;
    private short kw;
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
    public short getKanj() {
        return kanj;
    }

    public void setKanj(short kanj) {
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
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
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
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
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
        result = 31 * result + (int) kanj;
        result = 31 * result + (int) ord;
        result = 31 * result + (int) kw;
        return result;
    }
}
