package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class CinfPK implements Serializable {

    private static final long serialVersionUID = -2393167315466742474L;
    private int entr;
    private short kw;
    private String value;
    private String mctype;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    @Id
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
        this.kw = kw;
    }

    @Column(name = "mctype", nullable = false, insertable = true, updatable = true, length = 50)
    @Id
    public String getMctype() {
        return mctype;
    }

    public void setMctype(String mctype) {
        this.mctype = mctype;
    }

    @Column(name = "value", nullable = false, insertable = true, updatable = true, length = 50)
    @Id
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CinfPK cinfPK = (CinfPK) o;

        if (entr != cinfPK.entr) return false;
        if (kw != cinfPK.kw) return false;
        if (mctype != null ? !mctype.equals(cinfPK.mctype) : cinfPK.mctype != null) return false;
        if (value != null ? !value.equals(cinfPK.value) : cinfPK.value != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) kw;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (mctype != null ? mctype.hashCode() : 0);
        return result;
    }
}
