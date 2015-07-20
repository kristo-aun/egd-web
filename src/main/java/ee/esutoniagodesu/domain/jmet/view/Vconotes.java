package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vconotes", schema = "jmet", catalog = "egd")
public final class Vconotes implements Serializable {

    private static final long serialVersionUID = 8318273060458458067L;
    private Short pos;
    private String ptxt;
    private Integer id;
    private String txt;

    @Id
    @Basic
    @Column(name = "id", nullable = true, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pos", nullable = true, insertable = true, updatable = true)
    public Short getPos() {
        return pos;
    }

    public void setPos(Short pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "ptxt", nullable = true, insertable = true, updatable = true, length = 20)
    public String getPtxt() {
        return ptxt;
    }

    public void setPtxt(String ptxt) {
        this.ptxt = ptxt;
    }

    @Basic
    @Column(name = "txt", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vconotes vconotes = (Vconotes) o;

        if (id != null ? !id.equals(vconotes.id) : vconotes.id != null) return false;
        if (pos != null ? !pos.equals(vconotes.pos) : vconotes.pos != null) return false;
        if (ptxt != null ? !ptxt.equals(vconotes.ptxt) : vconotes.ptxt != null) return false;
        if (txt != null ? !txt.equals(vconotes.txt) : vconotes.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + (ptxt != null ? ptxt.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
