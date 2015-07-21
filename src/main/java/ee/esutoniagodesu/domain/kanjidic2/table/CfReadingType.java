package ee.esutoniagodesu.domain.kanjidic2.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "cf_reading_type", schema = "kanjidic2")
@Entity
@Immutable
public final class CfReadingType implements Serializable {

    private static final long serialVersionUID = -1894824579691701283L;
    private Integer id;
    @JsonIgnore
    private Collection<KanjiReading> kanjiReadings;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "cfReadingType")
    public Collection<KanjiReading> getKanjiReadings() {
        return kanjiReadings;
    }

    public void setKanjiReadings(Collection<KanjiReading> kanjiReadings) {
        this.kanjiReadings = kanjiReadings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfReadingType that = (CfReadingType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
