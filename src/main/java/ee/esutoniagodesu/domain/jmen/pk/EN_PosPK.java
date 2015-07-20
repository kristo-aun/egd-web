package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_PosPK implements Serializable {

    private static final long serialVersionUID = 5897449749212683946L;
    private int entr;
    private short sens;
    private short kw;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    @Id
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
        this.kw = kw;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_PosPK posPK = (EN_PosPK) o;

        if (entr != posPK.entr) return false;
        if (kw != posPK.kw) return false;
        if (sens != posPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) kw;
        return result;
    }
}
