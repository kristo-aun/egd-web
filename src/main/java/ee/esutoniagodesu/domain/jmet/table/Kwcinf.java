package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwcinf", schema = "jmet", catalog = "egd")
public final class Kwcinf implements Serializable {

    private static final long serialVersionUID = -3036403527490309187L;
    private short id;
    private String kw;
    private String descr;
    private Collection<Cinf> cinfsById;

    @OneToMany(mappedBy = "kwcinfByKw")
    public Collection<Cinf> getCinfsById() {
        return cinfsById;
    }

    public void setCinfsById(Collection<Cinf> cinfsById) {
        this.cinfsById = cinfsById;
    }

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 250)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true, length = 50)
    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwcinf kwcinf = (Kwcinf) o;

        if (id != kwcinf.id) return false;
        if (descr != null ? !descr.equals(kwcinf.descr) : kwcinf.descr != null) return false;
        if (kw != null ? !kw.equals(kwcinf.kw) : kwcinf.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
