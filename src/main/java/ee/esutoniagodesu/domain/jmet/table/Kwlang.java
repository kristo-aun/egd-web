package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwlang", schema = "jmet")
public final class Kwlang implements Serializable {

    private static final long serialVersionUID = 6668200911613146384L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Gloss> glossesById;
    private Collection<Lsrc> lsrcsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwlangByLang")
    public Collection<Gloss> getGlossesById() {
        return glossesById;
    }

    public void setGlossesById(Collection<Gloss> glossesById) {
        this.glossesById = glossesById;
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

    @OneToMany(mappedBy = "kwlangByLang")
    public Collection<Lsrc> getLsrcsById() {
        return lsrcsById;
    }

    public void setLsrcsById(Collection<Lsrc> lsrcsById) {
        this.lsrcsById = lsrcsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwlang kwlang = (Kwlang) o;

        if (id != kwlang.id) return false;
        if (descr != null ? !descr.equals(kwlang.descr) : kwlang.descr != null) return false;
        if (kw != null ? !kw.equals(kwlang.kw) : kwlang.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
