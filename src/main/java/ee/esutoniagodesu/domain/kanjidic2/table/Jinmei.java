package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "jinmei", schema = "kanjidic2")
@Entity
@Immutable
public final class Jinmei implements Serializable {

    private static final long serialVersionUID = 3667322557848819445L;
    private int jinmeiSeq;
    private int kanjiId;
    private Kanji kanji;

    @Column(name = "jinmei_seq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getJinmeiSeq() {
        return jinmeiSeq;
    }

    public void setJinmeiSeq(int jinmeiSeq) {
        this.jinmeiSeq = jinmeiSeq;
    }

    @OneToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "kanji_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getKanjiId() {
        return kanjiId;
    }

    public void setKanjiId(int kanjiId) {
        this.kanjiId = kanjiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jinmei jinmei = (Jinmei) o;

        if (jinmeiSeq != jinmei.jinmeiSeq) return false;
        if (kanjiId != jinmei.kanjiId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jinmeiSeq;
        result = 31 * result + kanjiId;
        return result;
    }
}
