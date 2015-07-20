package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class RdngsndPK implements Serializable {

    private static final long serialVersionUID = -8633312833329775148L;
    private int entr;
    private int rdng;
    private int snd;

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

    @Column(name = "snd", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSnd() {
        return snd;
    }

    public void setSnd(int snd) {
        this.snd = snd;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RdngsndPK rdngsndPK = (RdngsndPK) o;

        if (entr != rdngsndPK.entr) return false;
        if (rdng != rdngsndPK.rdng) return false;
        if (snd != rdngsndPK.snd) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + rdng;
        result = 31 * result + snd;
        return result;
    }
}
