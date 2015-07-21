package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwfreq", schema = "jmen")
public final class EN_Kwfreq implements Serializable {

    private static final long serialVersionUID = -3843506943541346324L;
    private short id;
    private String kw;
    private String descr;
    private Collection<EN_Freq> freqsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwfreqByKw")
    public Collection<EN_Freq> getFreqsById() {
        return freqsById;
    }

    public void setFreqsById(Collection<EN_Freq> freqsById) {
        this.freqsById = freqsById;
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

        EN_Kwfreq kwfreq = (EN_Kwfreq) o;

        if (id != kwfreq.id) return false;
        if (descr != null ? !descr.equals(kwfreq.descr) : kwfreq.descr != null) return false;
        if (kw != null ? !kw.equals(kwfreq.kw) : kwfreq.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
