package ee.esutoniagodesu.domain.jmdict_en.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwstat", schema = "jmdict_en", catalog = "egd")
public final class EN_Kwstat implements Serializable {

    private static final long serialVersionUID = 1599727330196878741L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Entr> entrsById;
    private Collection<EN_Hist> histsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwstatByStat")
    public Collection<EN_Entr> getEntrsById() {
        return entrsById;
    }

    public void setEntrsById(Collection<EN_Entr> entrsById) {
        this.entrsById = entrsById;
    }

    @OneToMany(mappedBy = "kwstatByStat")
    public Collection<EN_Hist> getHistsById() {
        return histsById;
    }

    public void setHistsById(Collection<EN_Hist> histsById) {
        this.histsById = histsById;
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

        EN_Kwstat kwstat = (EN_Kwstat) o;

        if (id != kwstat.id) return false;
        if (descr != null ? !descr.equals(kwstat.descr) : kwstat.descr != null) return false;
        if (kw != null ? !kw.equals(kwstat.kw) : kwstat.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
