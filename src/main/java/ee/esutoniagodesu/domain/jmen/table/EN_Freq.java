package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "freq", schema = "jmen", catalog = "egd")
public final class EN_Freq implements Serializable {

    private static final long serialVersionUID = -1747128126304841385L;
    private int entr;
    private Short rdng;
    private Short kanj;
    private short kw;
    private Integer value;
    private EN_Kanj kanj_0;
    private EN_Kwfreq kwfreqByKw;
    private EN_Rdng rdng_0;
    private Integer id;//ise tehtud

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Short getKanj() {
        return kanj;
    }

    public void setKanj(Short kanj) {
        this.kanj = kanj;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "kanj", referencedColumnName = "kanj")})
    public EN_Kanj getKanj_0() {
        return kanj_0;
    }

    public void setKanj_0(EN_Kanj kanj_0) {
        this.kanj_0 = kanj_0;
    }

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    public short getKw() {
        return kw;
    }

    public void setKw(short kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "kw", referencedColumnName = "id", nullable = false)
    public EN_Kwfreq getKwfreqByKw() {
        return kwfreqByKw;
    }

    public void setKwfreqByKw(EN_Kwfreq kwfreqByKw) {
        this.kwfreqByKw = kwfreqByKw;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Short getRdng() {
        return rdng;
    }

    public void setRdng(Short rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr"),
        @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng")})
    public EN_Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(EN_Rdng rdng_0) {
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

        EN_Freq freq = (EN_Freq) o;

        if (entr != freq.entr) return false;
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
        result = 31 * result + (int) kw;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
