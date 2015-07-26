package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class KanjPK implements Serializable {

    private static final long serialVersionUID = -4710609579041084109L;
    private int entr;
    private int kanj;

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
    public int getKanj() {
        return kanj;
    }

    public void setKanj(int kanj) {
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
        result = 31 * result + kanj;
        return result;
    }
}
