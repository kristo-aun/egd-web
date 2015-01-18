package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwkinf", schema = "jmdict", catalog = "egd")
public final class Kwkinf implements Serializable {

    private static final long serialVersionUID = -890540657293968583L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Kinf> kinfsById;

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

    @OneToMany(mappedBy = "kwkinfByKw")
    public Collection<Kinf> getKinfsById() {
        return kinfsById;
    }

    public void setKinfsById(Collection<Kinf> kinfsById) {
        this.kinfsById = kinfsById;
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

        Kwkinf kwkinf = (Kwkinf) o;

        if (id != kwkinf.id) return false;
        if (descr != null ? !descr.equals(kwkinf.descr) : kwkinf.descr != null) return false;
        if (kw != null ? !kw.equals(kwkinf.kw) : kwkinf.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
