package ee.esutoniagodesu.domain.publik.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class JmGlossInfPK implements Serializable {

    private static final long serialVersionUID = -1127669388929874569L;

    private Integer entr;
    private Integer sens;
    private Integer gloss;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    @Id
    @Column(name = "gloss", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getGloss() {
        return gloss;
    }

    public void setGloss(Integer gloss) {
        this.gloss = gloss;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmGlossInfPK that = (JmGlossInfPK) o;

        if (entr != null ? !entr.equals(that.entr) : that.entr != null) return false;
        if (sens != null ? !sens.equals(that.sens) : that.sens != null) return false;
        return !(gloss != null ? !gloss.equals(that.gloss) : that.gloss != null);

    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        return result;
    }
}
