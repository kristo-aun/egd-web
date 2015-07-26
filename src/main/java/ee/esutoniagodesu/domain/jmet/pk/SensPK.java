package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class SensPK implements Serializable {

    private static final long serialVersionUID = 2627256529634588610L;
    private int entr;
    private int sens;

    public SensPK() {
    }

    public SensPK(int entr, int sens) {
        this.entr = entr;
        this.sens = sens;
    }

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensPK sensPK = (SensPK) o;

        if (entr != sensPK.entr) return false;
        if (sens != sensPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        return result;
    }
}
