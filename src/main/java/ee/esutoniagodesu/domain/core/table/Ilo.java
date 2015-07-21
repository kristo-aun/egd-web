package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Immutable
@Table(name = "ilo", schema = "core")
@Entity
public final class Ilo implements IHasCoreWord, Serializable {

    private static final long serialVersionUID = 3534462646792444497L;

    private Integer id;
    private String wordRomaji;
    private String word;
    private String wordPos;
    private String wordTranslation;

    private String comment;
    private String wordReading;
    private boolean withJmdict;
    private Collection<MtmIloKanji> mtmIloKanjis;
    private int wordKanjiCount;

    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "comment", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToMany(mappedBy = "ilo")
    public Collection<MtmIloKanji> getMtmIloKanjis() {
        return mtmIloKanjis;
    }

    public void setMtmIloKanjis(Collection<MtmIloKanji> mtmIloKanjis) {
        this.mtmIloKanjis = mtmIloKanjis;
    }

    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "word_pos", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordPos() {
        return wordPos;
    }

    public void setWordPos(String wordPos) {
        this.wordPos = wordPos;
    }

    @Column(name = "word_reading", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordReading() {
        return wordReading;
    }

    public void setWordReading(String wordReading) {
        this.wordReading = wordReading;
    }

    @Column(name = "word_romaji", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordRomaji() {
        return wordRomaji;
    }

    public void setWordRomaji(String wordRomaji) {
        this.wordRomaji = wordRomaji;
    }

    @Column(name = "word_translation", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(String wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    @Column(name = "with_jmdict", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public boolean isWithJmdict() {
        return withJmdict;
    }

    public void setWithJmdict(boolean withJmdict) {
        this.withJmdict = withJmdict;
    }

    @Transient
    public int getWordKanjiCount() {
        return wordKanjiCount;
    }

    public void setWordKanjiCount(int wordKanjiCount) {
        this.wordKanjiCount = wordKanjiCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ilo ilo = (Ilo) o;

        if (withJmdict != ilo.withJmdict) return false;
        if (comment != null ? !comment.equals(ilo.comment) : ilo.comment != null) return false;
        if (id != null ? !id.equals(ilo.id) : ilo.id != null) return false;
        if (word != null ? !word.equals(ilo.word) : ilo.word != null) return false;
        if (wordPos != null ? !wordPos.equals(ilo.wordPos) : ilo.wordPos != null) return false;
        if (wordReading != null ? !wordReading.equals(ilo.wordReading) : ilo.wordReading != null) return false;
        if (wordRomaji != null ? !wordRomaji.equals(ilo.wordRomaji) : ilo.wordRomaji != null) return false;
        if (wordTranslation != null ? !wordTranslation.equals(ilo.wordTranslation) : ilo.wordTranslation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wordRomaji != null ? wordRomaji.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (wordPos != null ? wordPos.hashCode() : 0);
        result = 31 * result + (wordTranslation != null ? wordTranslation.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (wordReading != null ? wordReading.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (withJmdict ? 1 : 0);
        return result;
    }
}
