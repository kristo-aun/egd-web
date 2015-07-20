package ee.esutoniagodesu.domain.jmen.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_KresolvPK implements Serializable {

    private static final long serialVersionUID = 6001046013859465531L;
    private int entr;
    private short kw;
    private String value;

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

        EN_KresolvPK kresolvPK = (EN_KresolvPK) o;

        if (entr != kresolvPK.entr) return false;
        if (kw != kresolvPK.kw) return false;
        if (value != null ? !value.equals(kresolvPK.value) : kresolvPK.value != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) kw;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
