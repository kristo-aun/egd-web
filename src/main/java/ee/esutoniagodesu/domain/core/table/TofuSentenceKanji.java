package ee.esutoniagodesu.domain.core.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "tofu_sentence_kanji", schema = "core", catalog = "egd")
@Entity
public class TofuSentenceKanji implements Serializable {

    private static final long serialVersionUID = 856122524713834732L;
    private Integer id;
    private String kanji;

    public TofuSentenceKanji() {
    }

    public TofuSentenceKanji(String kanji) {
        this.kanji = kanji;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "kanji", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentenceKanji that = (TofuSentenceKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kanji != null ? !kanji.equals(that.kanji) : that.kanji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        return result;
    }
}
