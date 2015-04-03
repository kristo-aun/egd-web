package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "freq", schema = "jmdict", catalog = "egd")
public final class Freq implements Serializable {

    private static final long serialVersionUID = -4117824177395254731L;
    private int entr;
    private Integer rdng;
    private Integer kanj;
    private int kw;
    private Integer value;
    private int id;
    private Kanj kanj_0;
    private Kwfreq kwfreqByKw;
    private Rdng rdng_0;

    @Basic
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Integer getKanj() {
        return kanj;
    }

    public void setKanj(Integer kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns(
        {
            @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr"),
            @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj")
        })
    public Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(Kanj kanj_0) {
        this.kanj_0 = kanj_0;
    }

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "kw", referencedColumnName = "id", nullable = false)
    public Kwfreq getKwfreqByKw() {
        return kwfreqByKw;
    }

    public void setKwfreqByKw(Kwfreq kwfreqByKw) {
        this.kwfreqByKw = kwfreqByKw;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Integer getRdng() {
        return rdng;
    }

    public void setRdng(Integer rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng")})
    public Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    @Basic
    @Column(name = "value", nullable = true, insertable = true, updatable = true)
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Freq freq = (Freq) o;

        if (entr != freq.entr) return false;
        if (id != freq.id) return false;
        if (kw != freq.kw) return false;
        if (kanj != null ? !kanj.equals(freq.kanj) : freq.kanj != null) return false;
        if (rdng != null ? !rdng.equals(freq.rdng) : freq.rdng != null) return false;
        if (value != null ? !value.equals(freq.value) : freq.value != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + kw;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
