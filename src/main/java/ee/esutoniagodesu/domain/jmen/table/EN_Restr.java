package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_RestrPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "restr", schema = "jmen")
@IdClass(EN_RestrPK.class)
public final class EN_Restr implements Serializable {

    private static final long serialVersionUID = -8690998747710810136L;
    private int entr;
    private short rdng;
    private short kanj;
    private EN_Kanj kanj_0;
    private EN_Rdng rdng_0;

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
    public EN_Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(EN_Kanj kanj_0) {
        this.kanj_0 = kanj_0;
    }

    @Id
    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    public short getRdng() {
        return rdng;
    }

    public void setRdng(short rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng", nullable = false)})
    public EN_Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(EN_Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Restr restr = (EN_Restr) o;

        if (entr != restr.entr) return false;
        if (kanj != restr.kanj) return false;
        if (rdng != restr.rdng) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) rdng;
        result = 31 * result + (int) kanj;
        return result;
    }
}
