package ee.esutoniagodesu.domain.core.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_tofu_sentence_kanji", schema = "core", catalog = "egd")
@Entity
public final class MtmTofuSentenceKanji implements Serializable {

    private static final long serialVersionUID = 2491361553647364212L;
    private Integer id;
    private TofuSentence tofuSentence;
    private TofuSentenceKanji tofuSentenceKanji;

    public MtmTofuSentenceKanji() {
    }

    public MtmTofuSentenceKanji(TofuSentence tofuSentence, TofuSentenceKanji tofuSentenceKanji) {
        this.tofuSentence = tofuSentence;
        this.tofuSentenceKanji = tofuSentenceKanji;
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

    @ManyToOne
    @JoinColumn(name = "tofu_sentence_id", referencedColumnName = "id", nullable = false)
    public TofuSentence getTofuSentence() {
        return tofuSentence;
    }

    public void setTofuSentence(TofuSentence tofuSentence) {
        this.tofuSentence = tofuSentence;
    }

    @ManyToOne
    @JoinColumn(name = "tofu_sentence_kanji_id", referencedColumnName = "id", nullable = false)
    public TofuSentenceKanji getTofuSentenceKanji() {
        return tofuSentenceKanji;
    }

    public void setTofuSentenceKanji(TofuSentenceKanji tofuSentenceKanji) {
        this.tofuSentenceKanji = tofuSentenceKanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmTofuSentenceKanji that = (MtmTofuSentenceKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
