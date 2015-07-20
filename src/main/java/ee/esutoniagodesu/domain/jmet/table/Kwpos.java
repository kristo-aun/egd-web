package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwpos", schema = "jmet", catalog = "egd")
public final class Kwpos implements Serializable {

    private static final long serialVersionUID = 6446427306013368829L;
    private short id;
    private String kw;
    private String descr;
    private Collection<Conjo> conjosById;
    private Collection<Conjo> conjosById_0;
    private Collection<Pos> posesById;

    @OneToMany(mappedBy = "kwposByPos2")
    public Collection<Conjo> getConjosById() {
        return conjosById;
    }

    public void setConjosById(Collection<Conjo> conjosById) {
        this.conjosById = conjosById;
    }

    @OneToMany(mappedBy = "kwposByPos")
    public Collection<Conjo> getConjosById_0() {
        return conjosById_0;
    }

    public void setConjosById_0(Collection<Conjo> conjosById_0) {
        this.conjosById_0 = conjosById_0;
    }

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
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
