package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "sr_valid", schema = "jmet")
public final class SrValid implements Serializable {

    private static final long serialVersionUID = -8366053880487129885L;
    private Integer entr;
    private Integer sens;
    private Integer rdng;
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
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Integer getRdng() {
        return rdng;
    }

    public void setRdng(Integer rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
    }

    @Basic
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SrValid srValid = (SrValid) o;

        if (entr != null ? !entr.equals(srValid.entr) : srValid.entr != null) return false;
        if (rdng != null ? !rdng.equals(srValid.rdng) : srValid.rdng != null) return false;
        if (rtxt != null ? !rtxt.equals(srValid.rtxt) : srValid.rtxt != null) return false;
        if (sens != null ? !sens.equals(srValid.sens) : srValid.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        return result;
    }
}
