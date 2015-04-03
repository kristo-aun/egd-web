package ee.esutoniagodesu.domain.jmdict_en.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "xrefhw", schema = "jmdict_en", catalog = "egd")
public final class EN_Xrefhw implements Serializable {

    private static final long serialVersionUID = 226448006510406386L;
    private Integer entr;
    private Short sens;
    private String rtxt;
    private Short kanj;
    private String ktxt;

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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Short getKanj() {
        return kanj;
    }

    public void setKanj(Short kanj) {
        this.kanj = kanj;
    }

    @Basic
    @Column(name = "ktxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getKtxt() {
        return ktxt;
    }

    public void setKtxt(String ktxt) {
        this.ktxt = ktxt;
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
    public Short getSens() {
        return sens;
    }

    public void setSens(Short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Xrefhw xrefhw = (EN_Xrefhw) o;

        if (entr != null ? !entr.equals(xrefhw.entr) : xrefhw.entr != null) return false;
        if (kanj != null ? !kanj.equals(xrefhw.kanj) : xrefhw.kanj != null) return false;
        if (ktxt != null ? !ktxt.equals(xrefhw.ktxt) : xrefhw.ktxt != null) return false;
        if (rtxt != null ? !rtxt.equals(xrefhw.rtxt) : xrefhw.rtxt != null) return false;
        if (sens != null ? !sens.equals(xrefhw.sens) : xrefhw.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        return result;
    }
}
