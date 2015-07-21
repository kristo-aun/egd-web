package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_rdng", schema = "jmet")
public final class VtRdng implements Serializable {

    private static final long serialVersionUID = 3819482710039474228L;
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

        VtRdng vtRdng = (VtRdng) o;

        if (entr != null ? !entr.equals(vtRdng.entr) : vtRdng.entr != null) return false;
        if (rtxt != null ? !rtxt.equals(vtRdng.rtxt) : vtRdng.rtxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        return result;
    }
}
