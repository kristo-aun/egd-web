package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwxref", schema = "jmdict", catalog = "egd")
public final class Kwxref implements Serializable {

    private static final long serialVersionUID = 2840140714719289687L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Xref> xrefsById;
    private Collection<Xresolv> xresolvsById;

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

    @OneToMany(mappedBy = "kwxrefByTyp")
    public Collection<Xref> getXrefsById() {
        return xrefsById;
    }

    public void setXrefsById(Collection<Xref> xrefsById) {
        this.xrefsById = xrefsById;
    }

    @OneToMany(mappedBy = "kwxrefByTyp")
    public Collection<Xresolv> getXresolvsById() {
        return xresolvsById;
    }

    public void setXresolvsById(Collection<Xresolv> xresolvsById) {
        this.xresolvsById = xresolvsById;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwxref kwxref = (Kwxref) o;

        if (id != kwxref.id) return false;
        if (descr != null ? !descr.equals(kwxref.descr) : kwxref.descr != null) return false;
        if (kw != null ? !kw.equals(kwxref.kw) : kwxref.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
