package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "Kwginf", schema = "jmdict", catalog = "egd")
public final class Kwginf implements Serializable {

    private static final long serialVersionUID = -7881334882137449162L;
    private int id;
    private String kw;
    private String descr;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true, length = 20)
    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwginf kwginf = (Kwginf) o;

        if (id != kwginf.id) return false;
        if (descr != null ? !descr.equals(kwginf.descr) : kwginf.descr != null) return false;
        if (kw != null ? !kw.equals(kwginf.kw) : kwginf.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
