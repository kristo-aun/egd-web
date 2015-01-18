package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_kanji_primitive", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class MtmKanjiPrimitive implements Serializable {

    private static final long serialVersionUID = 5642652090273155685L;
    private Integer id;
    private Kanji kanji;
    private KanjiPrimitive kanjiPrimitive;

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
    @JoinColumn(name = "primitive_id", referencedColumnName = "id", nullable = false)
    public KanjiPrimitive getKanjiPrimitive() {
        return kanjiPrimitive;
    }

    public void setKanjiPrimitive(KanjiPrimitive kanjiPrimitive) {
        this.kanjiPrimitive = kanjiPrimitive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmKanjiPrimitive that = (MtmKanjiPrimitive) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
