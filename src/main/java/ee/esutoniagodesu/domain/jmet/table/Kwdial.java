package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwdial", schema = "jmet", catalog = "egd")
public final class Kwdial implements Serializable {

    private static final long serialVersionUID = 5637536079656080175L;
    private short id;
    private String kw;
    private String descr;
    private Collection<Dial> dialsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwdialByKw")
    public Collection<Dial> getDialsById() {
        return dialsById;
    }

    public void setDialsById(Collection<Dial> dialsById) {
        this.dialsById = dialsById;
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

        Kwdial kwdial = (Kwdial) o;

        if (id != kwdial.id) return false;
        if (descr != null ? !descr.equals(kwdial.descr) : kwdial.descr != null) return false;
        if (kw != null ? !kw.equals(kwdial.kw) : kwdial.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
