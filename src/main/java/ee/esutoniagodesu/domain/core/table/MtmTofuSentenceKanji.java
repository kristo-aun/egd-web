package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Immutable
@Table(name = "mtm_tofu_sentence_kanji", schema = "core", catalog = "egd")
@Entity
public final class MtmTofuSentenceKanji implements Serializable {

    private static final long serialVersionUID = 2491361553647364212L;
    private Integer id;
    private TofuSentence tofuSentence;
    private TofuSentenceKanji tofuSentenceKanji;

    @SequenceGenerator(name = "seq", sequenceName = "core.mtm_tofu_sentence_kanji_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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
