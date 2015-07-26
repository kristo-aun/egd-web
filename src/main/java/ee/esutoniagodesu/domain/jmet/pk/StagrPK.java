package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class StagrPK implements Serializable {

    private static final long serialVersionUID = -7146478276213871378L;
    private int entr;
    private int sens;
    private int rdng;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    @Id
    public int getRdng() {
        return rdng;
    }

    public void setRdng(int rdng) {
        this.rdng = rdng;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StagrPK stagrPK = (StagrPK) o;

        if (entr != stagrPK.entr) return false;
        if (rdng != stagrPK.rdng) return false;
        if (sens != stagrPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + rdng;
        return result;
    }
}
