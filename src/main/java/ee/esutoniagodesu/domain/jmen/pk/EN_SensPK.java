package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_SensPK implements Serializable {

    private static final long serialVersionUID = 2688793812623168869L;
    private int entr;
    private short sens;

    public EN_SensPK() {
    }

    public EN_SensPK(int entr, short sens) {
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
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_SensPK sensPK = (EN_SensPK) o;

        if (entr != sensPK.entr) return false;
        if (sens != sensPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        return result;
    }
}
