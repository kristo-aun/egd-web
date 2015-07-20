package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwgrp", schema = "jmen", catalog = "egd")
public final class EN_Kwgrp implements Serializable {

    private static final long serialVersionUID = 3172508267523159267L;
    private int id;
    private String kw;
    private String descr;
    private Collection<EN_Grp> grpsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwgrpByKw")
    public Collection<EN_Grp> getGrpsById() {
        return grpsById;
    }

    public void setGrpsById(Collection<EN_Grp> grpsById) {
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

        EN_Kwgrp kwgrp = (EN_Kwgrp) o;

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
