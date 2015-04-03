package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class KinfPK implements Serializable {

    private static final long serialVersionUID = -4851015017428441448L;
    private int entr;
    private int kanj;
    private int kw;

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

    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    @Id
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KinfPK kinfPK = (KinfPK) o;

        if (entr != kinfPK.entr) return false;
        if (kanj != kinfPK.kanj) return false;
        if (kw != kinfPK.kw) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kanj;
        result = 31 * result + kw;
        return result;
    }
}
