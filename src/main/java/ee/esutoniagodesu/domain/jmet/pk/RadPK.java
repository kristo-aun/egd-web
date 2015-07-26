package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class RadPK implements Serializable {

    private static final long serialVersionUID = -1217896413046347427L;
    private int num;
    private int var;

    @Column(name = "num", nullable = false, insertable = true, updatable = true)
    @Id
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Column(name = "var", nullable = false, insertable = true, updatable = true)
    @Id
    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadPK radPK = (RadPK) o;

        if (num != radPK.num) return false;
        if (var != radPK.var) return false;

        return true;
    }

    public int hashCode() {
        int result = num;
        result = 31 * result + var;
        return result;
    }
}
