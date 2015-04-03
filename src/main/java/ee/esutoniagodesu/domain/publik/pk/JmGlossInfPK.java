package ee.esutoniagodesu.domain.publik.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class JmGlossInfPK implements Serializable {

    private static final long serialVersionUID = -1127669388929874569L;

    private int entr;
    private int sens;
    private int gloss;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Id
    @Column(name = "gloss", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public int getGloss() {
        return gloss;
    }

    public void setGloss(int gloss) {
        this.gloss = gloss;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmGlossInfPK that = (JmGlossInfPK) o;

        if (entr != that.entr) return false;
        if (gloss != that.gloss) return false;
        if (sens != that.sens) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + gloss;
        return result;
    }
}
