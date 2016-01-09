package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class FreqPK implements Serializable {

    private static final long serialVersionUID = 4598012853035015266L;
    private int entr;
    private short rdng;
    private short kanj;
    private short kw;

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
    public short getRdng() {
        return rdng;
    }

    public void setRdng(short rdng) {
        this.rdng = rdng;
    }

    @Column(name = "kanj", nullable = false, insertable = true, updatable = true)
    @Id
    public short getKanj() {
        return kanj;
    }

    public void setKanj(short kanj) {
        this.kanj = kanj;
    }

    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    @Id
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
        this.kw = kw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreqPK freqPK = (FreqPK) o;

        if (entr != freqPK.entr) return false;
        if (rdng != freqPK.rdng) return false;
        if (kanj != freqPK.kanj) return false;
        return kw == freqPK.kw;

    }

    @Override
    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) rdng;
        result = 31 * result + (int) kanj;
        result = 31 * result + (int) kw;
        return result;
    }
}
