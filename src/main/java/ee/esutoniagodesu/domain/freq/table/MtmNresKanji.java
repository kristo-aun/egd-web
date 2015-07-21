package ee.esutoniagodesu.domain.freq.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_nres_kanji", schema = "freq")
@Entity
public final class MtmNresKanji implements Serializable {

    private static final long serialVersionUID = -3161266835578604718L;
    private Integer id;
    private Integer kanjiId;
    private NresBase nresBase;
    private NresKanji nresKanji;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "kanji_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getKanjiId() {
        return kanjiId;
    }

    public void setKanjiId(Integer kanjiId) {
        this.kanjiId = kanjiId;
    }

    @ManyToOne
    @JoinColumn(name = "nres_id", referencedColumnName = "id", nullable = false)
    public NresBase getNresBase() {
        return nresBase;
    }

    public void setNresBase(NresBase nresBase) {
        this.nresBase = nresBase;
    }

    @ManyToOne
    @JoinColumn(name = "nres_kanji_id", referencedColumnName = "id", nullable = false)
    public NresKanji getNresKanji() {
        return nresKanji;
    }

    public void setNresKanji(NresKanji nresKanji) {
        this.nresKanji = nresKanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmNresKanji that = (MtmNresKanji) o;

        if (id != that.id) return false;
        if (kanjiId != null ? !kanjiId.equals(that.kanjiId) : that.kanjiId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (kanjiId != null ? kanjiId.hashCode() : 0);
        return result;
    }
}
