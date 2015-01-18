package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwpos", schema = "jmdict", catalog = "egd")
public final class Kwpos implements Serializable {

    private static final long serialVersionUID = 7566893943289710607L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Pos> posesById;

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

    @OneToMany(mappedBy = "kwposByKw")
    public Collection<Pos> getPosesById() {
        return posesById;
    }

    public void setPosesById(Collection<Pos> posesById) {
        this.posesById = posesById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwpos kwpos = (Kwpos) o;

        if (id != kwpos.id) return false;
        if (descr != null ? !descr.equals(kwpos.descr) : kwpos.descr != null) return false;
        if (kw != null ? !kw.equals(kwpos.kw) : kwpos.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
