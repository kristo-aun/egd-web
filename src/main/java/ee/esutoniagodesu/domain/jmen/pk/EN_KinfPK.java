package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class EN_KinfPK implements Serializable {

    private static final long serialVersionUID = -3832790023667169877L;
    private int entr;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_KinfPK kinfPK = (EN_KinfPK) o;

        if (entr != kinfPK.entr) return false;
        if (kanj != kinfPK.kanj) return false;
        if (kw != kinfPK.kw) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) kanj;
        result = 31 * result + (int) kw;
        return result;
    }
}
