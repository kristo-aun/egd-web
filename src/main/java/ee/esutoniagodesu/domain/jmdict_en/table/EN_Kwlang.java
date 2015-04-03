package ee.esutoniagodesu.domain.jmdict_en.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwlang", schema = "jmdict_en", catalog = "egd")
public final class EN_Kwlang implements Serializable {

    private static final long serialVersionUID = -7879099640748297121L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Gloss> glossesById;
    private Collection<EN_Lsrc> lsrcsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwlangByLang")
    public Collection<EN_Gloss> getGlossesById() {
        return glossesById;
    }

    public void setGlossesById(Collection<EN_Gloss> glossesById) {
        this.glossesById = glossesById;
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

    @OneToMany(mappedBy = "kwlangByLang")
    public Collection<EN_Lsrc> getLsrcsById() {
        return lsrcsById;
    }

    public void setLsrcsById(Collection<EN_Lsrc> lsrcsById) {
        this.lsrcsById = lsrcsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Kwlang kwlang = (EN_Kwlang) o;

        if (id != kwlang.id) return false;
        if (descr != null ? !descr.equals(kwlang.descr) : kwlang.descr != null) return false;
        if (kw != null ? !kw.equals(kwlang.kw) : kwlang.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
