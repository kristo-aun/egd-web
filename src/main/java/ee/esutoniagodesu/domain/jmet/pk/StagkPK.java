package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class StagkPK implements Serializable {

    private static final long serialVersionUID = -8855746657422190065L;
    private int entr;
    private short sens;
    private short kanj;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    @Id
    public short getKanj() {
        return kanj;
    }

    public void setKanj(short kanj) {
        this.kanj = kanj;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StagkPK stagkPK = (StagkPK) o;

        if (entr != stagkPK.entr) return false;
        if (kanj != stagkPK.kanj) return false;
        if (sens != stagkPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) kanj;
        return result;
    }
}
