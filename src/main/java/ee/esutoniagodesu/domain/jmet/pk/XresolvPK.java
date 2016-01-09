package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class XresolvPK implements Serializable {

    private static final long serialVersionUID = -6583085927737256522L;
    private int entr;
    private short sens;
    private short typ;
    private short ord;

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
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
        this.ord = ord;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    @Column(name = "typ", nullable = false, insertable = true, updatable = true)
    @Id
    public short getTyp() {
        return typ;
    }

    public void setTyp(short typ) {
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
        result = 31 * result + (int) sens;
        result = 31 * result + (int) typ;
        result = 31 * result + (int) ord;
        return result;
    }
}
