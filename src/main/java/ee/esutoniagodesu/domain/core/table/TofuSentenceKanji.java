package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Immutable
@Table(name = "tofu_sentence_kanji", schema = "core", catalog = "egd")
@Entity
public final class TofuSentenceKanji implements Serializable {

    private static final long serialVersionUID = 856122524713834732L;
    private Integer id;
    private String kanji;
    private Collection<MtmTofuSentenceKanji> mtmTofuSentenceKanjis;

    @SequenceGenerator(name = "seq", sequenceName = "core.tofu_sentence_kanji_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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

    @OneToMany(mappedBy = "tofuSentenceKanji")
    public Collection<MtmTofuSentenceKanji> getMtmTofuSentenceKanjis() {
        return mtmTofuSentenceKanjis;
    }

    public void setMtmTofuSentenceKanjis(Collection<MtmTofuSentenceKanji> mtmTofuSentenceKanjis) {
        this.mtmTofuSentenceKanjis = mtmTofuSentenceKanjis;
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
