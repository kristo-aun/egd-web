package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "prfreq", schema = "jmet")
public final class Prfreq implements Serializable {

    private static final long serialVersionUID = -18318733851271493L;
    private Integer entr;
    private Integer rdng;
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
    @Column(name = "p", nullable = true, insertable = true, updatable = true)
    public Boolean getP() {
        return p;
    }

    public void setP(Boolean p) {
        this.p = p;
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

        Prfreq prfreq = (Prfreq) o;

        if (entr != null ? !entr.equals(prfreq.entr) : prfreq.entr != null) return false;
        if (p != null ? !p.equals(prfreq.p) : prfreq.p != null) return false;
        if (rdng != null ? !rdng.equals(prfreq.rdng) : prfreq.rdng != null) return false;
        if (txt != null ? !txt.equals(prfreq.txt) : prfreq.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (p != null ? p.hashCode() : 0);
        return result;
    }
}
