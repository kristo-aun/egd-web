package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwgrp", schema = "jmet")
public final class Kwgrp implements Serializable {

    private static final long serialVersionUID = 8961119975274168272L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Grp> grpsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwgrpByKw")
    public Collection<Grp> getGrpsById() {
        return grpsById;
    }

    public void setGrpsById(Collection<Grp> grpsById) {
        this.grpsById = grpsById;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwgrp kwgrp = (Kwgrp) o;

        if (id != kwgrp.id) return false;
        if (descr != null ? !descr.equals(kwgrp.descr) : kwgrp.descr != null) return false;
        if (kw != null ? !kw.equals(kwgrp.kw) : kwgrp.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
