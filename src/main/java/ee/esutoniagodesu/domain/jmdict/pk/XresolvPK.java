package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class XresolvPK implements Serializable {

    private static final long serialVersionUID = -3478479172633552407L;
    private int entr;
    private int sens;
    private int typ;
    private int ord;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    @Id
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @Column(name = "typ", nullable = false, insertable = true, updatable = true)
    @Id
    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XresolvPK xresolvPK = (XresolvPK) o;

        if (entr != xresolvPK.entr) return false;
        if (ord != xresolvPK.ord) return false;
        if (sens != xresolvPK.sens) return false;
        if (typ != xresolvPK.typ) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + typ;
        result = 31 * result + ord;
        return result;
    }
}
