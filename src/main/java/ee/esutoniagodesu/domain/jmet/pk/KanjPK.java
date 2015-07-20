package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class KanjPK implements Serializable {

    private static final long serialVersionUID = -882088041150044065L;
    private int entr;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjPK kanjPK = (KanjPK) o;

        if (entr != kanjPK.entr) return false;
        if (kanj != kanjPK.kanj) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) kanj;
        return result;
    }
}
