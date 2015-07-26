package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class RestrPK implements Serializable {

    private static final long serialVersionUID = 8415887626487049028L;
    private int entr;
    private int rdng;
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

    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    @Id
    public int getRdng() {
        return rdng;
    }

    public void setRdng(int rdng) {
        this.rdng = rdng;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestrPK restrPK = (RestrPK) o;

        if (entr != restrPK.entr) return false;
        if (kanj != restrPK.kanj) return false;
        if (rdng != restrPK.rdng) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + rdng;
        result = 31 * result + kanj;
        return result;
    }
}
