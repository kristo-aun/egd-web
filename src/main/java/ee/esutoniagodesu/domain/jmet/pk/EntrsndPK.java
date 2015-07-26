package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class EntrsndPK implements Serializable {

    private static final long serialVersionUID = -4477155781780373313L;
    private int entr;
    private int snd;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
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

        EntrsndPK entrsndPK = (EntrsndPK) o;

        if (entr != entrsndPK.entr) return false;
        if (snd != entrsndPK.snd) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + snd;
        return result;
    }
}
