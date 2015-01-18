package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.KresolvPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(KresolvPK.class)
@Table(name = "Kresolv", schema = "jmdict", catalog = "egd")
public final class Kresolv implements Serializable {

    private static final long serialVersionUID = -1256896413669210226L;
    private int entr;
    private int kw;
    private String value;
    private Entr entrByEntr;

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

    @Id
    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    @Id
    @Column(name = "value", nullable = false, insertable = true, updatable = true, length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kresolv kresolv = (Kresolv) o;

        if (entr != kresolv.entr) return false;
        if (kw != kresolv.kw) return false;
        if (value != null ? !value.equals(kresolv.value) : kresolv.value != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kw;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
