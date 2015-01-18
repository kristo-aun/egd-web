package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "kanji_radical", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class KanjiRadical implements Serializable {

    private static final long serialVersionUID = -1446928912487337897L;
    private Integer id;
    private String radType;
    private String radValue;
    private Kanji kanji;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "rad_type", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getRadType() {
        return radType;
    }

    public void setRadType(String radType) {
        this.radType = radType;
    }

    @Column(name = "rad_value", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getRadValue() {
        return radValue;
    }

    public void setRadValue(String radValue) {
        this.radValue = radValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiRadical that = (KanjiRadical) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (radType != null ? !radType.equals(that.radType) : that.radType != null) return false;
        if (radValue != null ? !radValue.equals(that.radValue) : that.radValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (radType != null ? radType.hashCode() : 0);
        result = 31 * result + (radValue != null ? radValue.hashCode() : 0);
        return result;
    }
}
