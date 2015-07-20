package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vt_rinf", schema = "jmen", catalog = "egd")
public final class EN_VtRinf implements Serializable {

    private static final long serialVersionUID = -5735710684014493995L;
    private Integer entr;
    private Short rdng;
    private String ritxt;

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
    public Short getRdng() {
        return rdng;
    }

    public void setRdng(Short rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "ritxt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getRitxt() {
        return ritxt;
    }

    public void setRitxt(String ritxt) {
        this.ritxt = ritxt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_VtRinf vtRinf = (EN_VtRinf) o;

        if (entr != null ? !entr.equals(vtRinf.entr) : vtRinf.entr != null) return false;
        if (rdng != null ? !rdng.equals(vtRinf.rdng) : vtRinf.rdng != null) return false;
        if (ritxt != null ? !ritxt.equals(vtRinf.ritxt) : vtRinf.ritxt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (ritxt != null ? ritxt.hashCode() : 0);
        return result;
    }
}
