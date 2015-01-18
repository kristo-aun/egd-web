package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class HistPK implements Serializable {

    private static final long serialVersionUID = 2257283800959187918L;
    private int entr;
    private int hist;

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
    public int getHist() {
        return hist;
    }

    public void setHist(int hist) {
        this.hist = hist;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistPK histPK = (HistPK) o;

        if (entr != histPK.entr) return false;
        if (hist != histPK.hist) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + hist;
        return result;
    }
}
