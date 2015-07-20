package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class RestrPK implements Serializable {

    private static final long serialVersionUID = -2838046974660462260L;
    private int entr;
    private short rdng;
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

    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    @Id
    public short getRdng() {
        return rdng;
    }

    public void setRdng(short rdng) {
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
        result = 31 * result + (int) rdng;
        result = 31 * result + (int) kanj;
        return result;
    }
}
