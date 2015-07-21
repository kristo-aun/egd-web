package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwrinf", schema = "jmen")
public final class EN_Kwrinf implements Serializable {

    private static final long serialVersionUID = 4821386130011034221L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Rinf> rinfsById;

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

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true, length = 20)
    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    @OneToMany(mappedBy = "kwrinfByKw")
    public Collection<EN_Rinf> getRinfsById() {
        return rinfsById;
    }

    public void setRinfsById(Collection<EN_Rinf> rinfsById) {
        this.rinfsById = rinfsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Kwrinf kwrinf = (EN_Kwrinf) o;

        if (id != kwrinf.id) return false;
        if (descr != null ? !descr.equals(kwrinf.descr) : kwrinf.descr != null) return false;
        if (kw != null ? !kw.equals(kwrinf.kw) : kwrinf.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
