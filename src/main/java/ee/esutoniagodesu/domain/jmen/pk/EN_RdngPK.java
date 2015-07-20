package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_RdngPK implements Serializable {

    private static final long serialVersionUID = 7725212440764940444L;
    private int entr;
    private short rdng;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_RdngPK rdngPK = (EN_RdngPK) o;

        if (entr != rdngPK.entr) return false;
        if (rdng != rdngPK.rdng) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) rdng;
        return result;
    }
}
