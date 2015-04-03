package ee.esutoniagodesu.domain.jmdict_en.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwkinf", schema = "jmdict_en", catalog = "egd")
public final class EN_Kwkinf implements Serializable {

    private static final long serialVersionUID = 1269184533206213268L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Kinf> kinfsById;

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
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "kwkinfByKw")
    public Collection<EN_Kinf> getKinfsById() {
        return kinfsById;
    }

    public void setKinfsById(Collection<EN_Kinf> kinfsById) {
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

        EN_Kwkinf kwkinf = (EN_Kwkinf) o;

        if (id != kwkinf.id) return false;
        if (descr != null ? !descr.equals(kwkinf.descr) : kwkinf.descr != null) return false;
        if (kw != null ? !kw.equals(kwkinf.kw) : kwkinf.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
