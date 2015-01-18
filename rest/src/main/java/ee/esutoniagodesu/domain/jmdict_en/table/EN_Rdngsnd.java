package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_RdngsndPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "rdngsnd", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_RdngsndPK.class)
public final class EN_Rdngsnd implements Serializable {

    private static final long serialVersionUID = 2930732502502004295L;
    private int entr;
    private int rdng;
    private short ord;
    private int snd;
    private EN_Rdng rdng_0;
    private EN_Snd sndBySnd;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
        this.ord = ord;
    }

    @Id
    @Column(name = "rdng", nullable = false, insertable = true, updatable = true)
    public int getRdng() {
        return rdng;
    }

    public void setRdng(int rdng) {
        this.rdng = rdng;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "rdng", referencedColumnName = "rdng", nullable = false)})
    public EN_Rdng getRdng_0() {
        return rdng_0;
    }

    public void setRdng_0(EN_Rdng rdng_0) {
        this.rdng_0 = rdng_0;
    }

    @Id
    @Column(name = "snd", nullable = false, insertable = true, updatable = true)
    public int getSnd() {
        return snd;
    }

    public void setSnd(int snd) {
        this.snd = snd;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "snd", referencedColumnName = "id", nullable = false)
    public EN_Snd getSndBySnd() {
        return sndBySnd;
    }

    public void setSndBySnd(EN_Snd sndBySnd) {
        this.sndBySnd = sndBySnd;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Rdngsnd rdngsnd = (EN_Rdngsnd) o;

        if (entr != rdngsnd.entr) return false;
        if (ord != rdngsnd.ord) return false;
        if (rdng != rdngsnd.rdng) return false;
        if (snd != rdngsnd.snd) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + rdng;
        result = 31 * result + (int) ord;
        result = 31 * result + snd;
        return result;
    }
}
