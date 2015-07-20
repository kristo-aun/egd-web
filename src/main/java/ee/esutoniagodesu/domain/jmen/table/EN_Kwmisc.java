package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwmisc", schema = "jmen", catalog = "egd")
public final class EN_Kwmisc implements Serializable {

    private static final long serialVersionUID = -3319809769257038960L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Misc> miscsById;

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

    @OneToMany(mappedBy = "kwmiscByKw")
    public Collection<EN_Misc> getMiscsById() {
        return miscsById;
    }

    public void setMiscsById(Collection<EN_Misc> miscsById) {
        this.miscsById = miscsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Kwmisc kwmisc = (EN_Kwmisc) o;

        if (id != kwmisc.id) return false;
        if (descr != null ? !descr.equals(kwmisc.descr) : kwmisc.descr != null) return false;
        if (kw != null ? !kw.equals(kwmisc.kw) : kwmisc.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
