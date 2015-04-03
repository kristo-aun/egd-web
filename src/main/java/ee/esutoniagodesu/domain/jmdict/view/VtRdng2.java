package ee.esutoniagodesu.domain.jmdict.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_rdng2", schema = "jmdict", catalog = "egd")
public final class VtRdng2 implements Serializable {

    private static final long serialVersionUID = -9147810209935148764L;

    private Integer entr;
    private String rtxt;

    @Id
    @Basic
    @Column(name = "entr", nullable = true, insertable = true, updatable = true)
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VtRdng2 vtRdng2 = (VtRdng2) o;

        if (entr != null ? !entr.equals(vtRdng2.entr) : vtRdng2.entr != null) return false;
        if (rtxt != null ? !rtxt.equals(vtRdng2.rtxt) : vtRdng2.rtxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        return result;
    }
}
