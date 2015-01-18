package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class XrefPK implements Serializable {

    private static final long serialVersionUID = 7113247440311444471L;
    private int entr;
    private int sens;
    private int xref;
    private int xentr;
    private int xsens;

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

    @Column(name = "xentr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getXentr() {
        return xentr;
    }

    public void setXentr(int xentr) {
        this.xentr = xentr;
    }

    @Column(name = "xref", nullable = false, insertable = true, updatable = true)
    @Id
    public int getXref() {
        return xref;
    }

    public void setXref(int xref) {
        this.xref = xref;
    }

    @Column(name = "xsens", nullable = false, insertable = true, updatable = true)
    @Id
    public int getXsens() {
        return xsens;
    }

    public void setXsens(int xsens) {
        this.xsens = xsens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XrefPK xrefPK = (XrefPK) o;

        if (entr != xrefPK.entr) return false;
        if (sens != xrefPK.sens) return false;
        if (xentr != xrefPK.xentr) return false;
        if (xref != xrefPK.xref) return false;
        if (xsens != xrefPK.xsens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + xref;
        result = 31 * result + xentr;
        result = 31 * result + xsens;
        return result;
    }
}
