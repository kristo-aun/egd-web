package ee.esutoniagodesu.domain.jmdict_en.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class EN_GlossPK implements Serializable {

    private static final long serialVersionUID = -7804433934591010448L;
    private int entr;
    private short sens;
    private short gloss;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "gloss", nullable = false, insertable = true, updatable = true)
    @Id
    public short getGloss() {
        return gloss;
    }

    public void setGloss(short gloss) {
        this.gloss = gloss;
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

        EN_GlossPK glossPK = (EN_GlossPK) o;

        if (entr != glossPK.entr) return false;
        if (gloss != glossPK.gloss) return false;
        if (sens != glossPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) gloss;
        return result;
    }
}
