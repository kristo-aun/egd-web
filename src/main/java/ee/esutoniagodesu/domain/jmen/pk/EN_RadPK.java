package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_RadPK implements Serializable {

    private static final long serialVersionUID = 4456718199673353713L;
    private short num;
    private short var;

    @Column(name = "num", nullable = false, insertable = true, updatable = true)
    @Id
    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    @Column(name = "var", nullable = false, insertable = true, updatable = true)
    @Id
    public short getVar() {
        return var;
    }

    public void setVar(short var) {
        this.var = var;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_RadPK radPK = (EN_RadPK) o;

        if (num != radPK.num) return false;
        if (var != radPK.var) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) num;
        result = 31 * result + (int) var;
        return result;
    }
}
