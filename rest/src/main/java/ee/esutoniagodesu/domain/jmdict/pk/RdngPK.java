package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class RdngPK implements Serializable {

    private static final long serialVersionUID = -8873443828480054059L;
    private int entr;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RdngPK rdngPK = (RdngPK) o;

        if (entr != rdngPK.entr) return false;
        if (rdng != rdngPK.rdng) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + rdng;
        return result;
    }
}
