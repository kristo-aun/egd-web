package ee.esutoniagodesu.domain.jmdict_en.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class EN_GrpPK implements Serializable {

    private static final long serialVersionUID = 937308102072673652L;
    private int entr;
    private int kw;

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
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_GrpPK grpPK = (EN_GrpPK) o;

        if (entr != grpPK.entr) return false;
        if (kw != grpPK.kw) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kw;
        return result;
    }
}
