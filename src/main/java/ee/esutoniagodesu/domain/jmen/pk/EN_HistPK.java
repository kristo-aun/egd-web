package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class EN_HistPK implements Serializable {

    private static final long serialVersionUID = -6204767370038627912L;
    private int entr;
    private short hist;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "hist", nullable = false, insertable = true, updatable = true)
    @Id
    public short getHist() {
        return hist;
    }

    public void setHist(short hist) {
        this.hist = hist;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_HistPK histPK = (EN_HistPK) o;

        if (entr != histPK.entr) return false;
        if (hist != histPK.hist) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) hist;
        return result;
    }
}
