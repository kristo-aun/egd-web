package ee.esutoniagodesu.domain.jmdict_en.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_XrefPK implements Serializable {

    private static final long serialVersionUID = 7643361902646235155L;
    private int entr;
    private short sens;
    private short xref;
    private int xentr;
    private short xsens;

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
    public short getXref() {
        return xref;
    }

    public void setXref(short xref) {
        this.xref = xref;
    }

    @Column(name = "xsens", nullable = false, insertable = true, updatable = true)
    @Id
    public short getXsens() {
        return xsens;
    }

    public void setXsens(short xsens) {
        this.xsens = xsens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_XrefPK xrefPK = (EN_XrefPK) o;

        if (entr != xrefPK.entr) return false;
        if (sens != xrefPK.sens) return false;
        if (xentr != xrefPK.xentr) return false;
        if (xref != xrefPK.xref) return false;
        if (xsens != xrefPK.xsens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) xref;
        result = 31 * result + xentr;
        result = 31 * result + (int) xsens;
        return result;
    }
}
