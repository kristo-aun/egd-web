package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_StagkPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "stagk", schema = "jmen", catalog = "egd")
@IdClass(EN_StagkPK.class)
public final class EN_Stagk implements Serializable {

    private static final long serialVersionUID = -885400893917244703L;
    private int entr;
    private short sens;
    private short kanj;
    private EN_Kanj kanj_0;
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

        EN_Stagk stagk = (EN_Stagk) o;

        if (entr != stagk.entr) return false;
        if (kanj != stagk.kanj) return false;
        if (sens != stagk.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) kanj;
        return result;
    }
}
