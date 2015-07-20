package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_StagrPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "stagr", schema = "jmen", catalog = "egd")
@IdClass(EN_StagrPK.class)
public final class EN_Stagr implements Serializable {

    private static final long serialVersionUID = -8827270477251502587L;
    private int entr;
    private short sens;
    private short rdng;
    private EN_Rdng rdng_0;
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

        EN_Stagr stagr = (EN_Stagr) o;

        if (entr != stagr.entr) return false;
        if (rdng != stagr.rdng) return false;
        if (sens != stagr.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) rdng;
        return result;
    }
}
