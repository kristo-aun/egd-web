package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwsrct", schema = "jmet", catalog = "egd")
public final class Kwsrct implements Serializable {

    private static final long serialVersionUID = -4148845167654975432L;
    private short id;
    private String kw;
    private String descr;
    private Collection<Kwsrc> kwsrcsById;

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

    @OneToMany(mappedBy = "kwsrctBySrct")
    public Collection<Kwsrc> getKwsrcsById() {
        return kwsrcsById;
    }

    public void setKwsrcsById(Collection<Kwsrc> kwsrcsById) {
        this.kwsrcsById = kwsrcsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwsrct kwsrct = (Kwsrct) o;

        if (id != kwsrct.id) return false;
        if (descr != null ? !descr.equals(kwsrct.descr) : kwsrct.descr != null) return false;
        if (kw != null ? !kw.equals(kwsrct.kw) : kwsrct.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
