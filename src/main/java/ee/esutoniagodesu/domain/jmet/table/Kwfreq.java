package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Kwfreq", schema = "jmet")
public final class Kwfreq implements Serializable {

    private static final long serialVersionUID = -9130235984450430924L;
    private int id;
    private String kw;
    private String descr;
    private Collection<Freq> freqsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @OneToMany(mappedBy = "kwfreqByKw")
    public Collection<Freq> getFreqsById() {
        return freqsById;
    }

    public void setFreqsById(Collection<Freq> freqsById) {
        this.freqsById = freqsById;
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

        Kwfreq kwfreq = (Kwfreq) o;

        if (id != kwfreq.id) return false;
        if (descr != null ? !descr.equals(kwfreq.descr) : kwfreq.descr != null) return false;
        if (kw != null ? !kw.equals(kwfreq.kw) : kwfreq.kw != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        return result;
    }
}
