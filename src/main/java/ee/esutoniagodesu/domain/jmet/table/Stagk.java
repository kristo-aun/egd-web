package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.StagkPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(StagkPK.class)
@Table(name = "Stagk", schema = "jmet")
public final class Stagk implements Serializable {

    private static final long serialVersionUID = -1350467517606934055L;
    private int entr;
    private int sens;
    private int kanj;
    private Kanj kanj_0;
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
    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    public int getKanj() {
        return kanj;
    }

    public void setKanj(int kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj", nullable = false)})
    public Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(Kanj kanj_0) {
        this.kanj_0 = kanj_0;
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
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false),
        @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(Sens sens_0) {
        this.sens_0 = sens_0;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stagk stagk = (Stagk) o;

        if (entr != stagk.entr) return false;
        if (kanj != stagk.kanj) return false;
        if (sens != stagk.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + kanj;
        return result;
    }
}
