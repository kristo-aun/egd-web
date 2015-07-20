package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwfld", schema = "jmet", catalog = "egd")
public final class Kwfld implements Serializable {

    private static final long serialVersionUID = 3824214307195329456L;
    private short id;
    private String kw;
    private String descr;
    private Collection<Fld> fldsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwfldByKw")
    public Collection<Fld> getFldsById() {
        return fldsById;
    }

    public void setFldsById(Collection<Fld> fldsById) {
        this.fldsById = fldsById;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwfld kwfld = (Kwfld) o;

        if (id != kwfld.id) return false;
        if (descr != null ? !descr.equals(kwfld.descr) : kwfld.descr != null) return false;
        if (kw != null ? !kw.equals(kwfld.kw) : kwfld.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
