package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.EntrsndPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(EntrsndPK.class)
@Table(name = "Entrsnd", schema = "jmdict", catalog = "egd")
public final class Entrsnd implements Serializable {

    private static final long serialVersionUID = 151559942852047361L;
    private int entr;
    private int ord;
    private int snd;
    private Entr entrByEntr;
    private Snd sndBySnd;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "id", nullable = false)
    public Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
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
    public Snd getSndBySnd() {
        return sndBySnd;
    }

    public void setSndBySnd(Snd sndBySnd) {
        this.sndBySnd = sndBySnd;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entrsnd entrsnd = (Entrsnd) o;

        if (entr != entrsnd.entr) return false;
        if (ord != entrsnd.ord) return false;
        if (snd != entrsnd.snd) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + ord;
        result = 31 * result + snd;
        return result;
    }
}
