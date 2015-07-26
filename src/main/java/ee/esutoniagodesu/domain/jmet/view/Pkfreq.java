package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "pkfreq", schema = "jmet")
public final class Pkfreq implements Serializable {

    private static final long serialVersionUID = -5461415315437369864L;
    private Integer entr;
    private Integer kanj;
    private String txt;
    private Boolean p;

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
    public Integer getKanj() {
        return kanj;
    }

    public void setKanj(Integer kanj) {
        this.kanj = kanj;
    }

    @Basic
    @Column(name = "p", nullable = true, insertable = true, updatable = true)
    public Boolean getP() {
        return p;
    }

    public void setP(Boolean p) {
        this.p = p;
    }

    @Basic
    @Column(name = "txt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pkfreq pkfreq = (Pkfreq) o;

        if (entr != null ? !entr.equals(pkfreq.entr) : pkfreq.entr != null) return false;
        if (kanj != null ? !kanj.equals(pkfreq.kanj) : pkfreq.kanj != null) return false;
        if (p != null ? !p.equals(pkfreq.p) : pkfreq.p != null) return false;
        if (txt != null ? !txt.equals(pkfreq.txt) : pkfreq.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        return result;
    }
}
