package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_kanji_reading", schema = "kanjidic2")
@Entity
@Immutable
public final class MtmKanjiReading implements Serializable {

    private static final long serialVersionUID = -8470764096375218741L;
    private Integer id;
    private Kanji kanji;
    private KanjiReading kanjiReading;

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

    @ManyToOne
    @JoinColumn(name = "reading_id", referencedColumnName = "id", nullable = false)
    public KanjiReading getKanjiReading() {
        return kanjiReading;
    }

    public void setKanjiReading(KanjiReading kanjiReading) {
        this.kanjiReading = kanjiReading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmKanjiReading that = (MtmKanjiReading) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
