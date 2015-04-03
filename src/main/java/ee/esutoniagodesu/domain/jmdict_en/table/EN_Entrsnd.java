package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_EntrsndPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "entrsnd", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_EntrsndPK.class)
public final class EN_Entrsnd implements Serializable {

    private static final long serialVersionUID = -4256581444890716302L;
    private int entr;
    private short ord;
    private int snd;
    private EN_Entr entrByEntr;
    private EN_Snd sndBySnd;

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
    public EN_Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(EN_Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
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

        EN_Entrsnd entrsnd = (EN_Entrsnd) o;

        if (entr != entrsnd.entr) return false;
        if (ord != entrsnd.ord) return false;
        if (snd != entrsnd.snd) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) ord;
        result = 31 * result + snd;
        return result;
    }
}
