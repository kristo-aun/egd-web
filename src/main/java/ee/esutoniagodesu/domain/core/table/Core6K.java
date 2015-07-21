package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Immutable
@Table(name = "core_6k", schema = "core")
@Entity
public final class Core6K implements IHasCoreWord, IHasCoreSentence, Serializable {

    private static final long serialVersionUID = 451950149966213116L;

    private Integer id;
    private String word;
    private String wordReading;
    private String wordFurigana;
    private String wordRomaji;

    private String wordTranslation;
    private String wordPos;
    private String wordAudio;
    private String sentence;
    private String sentenceReading;

    private String sentenceFurigana;
    private String sentenceRomaji;
    private String sentenceTranslation;
    private String sentenceAudio;
    private String sentencePicture;

    private boolean withJmdict;
    private Collection<MtmCore6KKanji> mtmCore6KKanjis;

    private int wordKanjiCount;

    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "core6K")
    public Collection<MtmCore6KKanji> getMtmCore6KKanjis() {
        return mtmCore6KKanjis;
    }

    public void setMtmCore6KKanjis(Collection<MtmCore6KKanji> mtmCore6KKanjis) {
        this.mtmCore6KKanjis = mtmCore6KKanjis;
    }

    @Column(name = "sentence", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Column(name = "sentence_audio", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceAudio() {
        return sentenceAudio;
    }

    public void setSentenceAudio(String sentenceAudio) {
        this.sentenceAudio = sentenceAudio;
    }

    @Column(name = "sentence_furigana", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceFurigana() {
        return sentenceFurigana;
    }

    public void setSentenceFurigana(String sentenceFurigana) {
        this.sentenceFurigana = sentenceFurigana;
    }

    @Column(name = "sentence_picture", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentencePicture() {
        return sentencePicture;
    }

    public void setSentencePicture(String sentencePicture) {
        this.sentencePicture = sentencePicture;
    }

    @Column(name = "sentence_reading", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceReading() {
        return sentenceReading;
    }

    public void setSentenceReading(String sentenceReading) {
        this.sentenceReading = sentenceReading;
    }

    @Column(name = "sentence_romaji", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceRomaji() {
        return sentenceRomaji;
    }

    public void setSentenceRomaji(String sentenceRomaji) {
        this.sentenceRomaji = sentenceRomaji;
    }

    @Column(name = "sentence_translation", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceTranslation() {
        return sentenceTranslation;
    }

    public void setSentenceTranslation(String sentenceTranslation) {
        this.sentenceTranslation = sentenceTranslation;
    }

    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "word_audio", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(String wordAudio) {
        this.wordAudio = wordAudio;
    }

    @Column(name = "word_furigana", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordFurigana() {
        return wordFurigana;
    }

    public void setWordFurigana(String wordFurigana) {
        this.wordFurigana = wordFurigana;
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

        Core6K core6K = (Core6K) o;

        if (withJmdict != core6K.withJmdict) return false;
        if (id != null ? !id.equals(core6K.id) : core6K.id != null) return false;
        if (sentence != null ? !sentence.equals(core6K.sentence) : core6K.sentence != null) return false;
        if (sentenceAudio != null ? !sentenceAudio.equals(core6K.sentenceAudio) : core6K.sentenceAudio != null)
            return false;
        if (sentenceFurigana != null ? !sentenceFurigana.equals(core6K.sentenceFurigana) : core6K.sentenceFurigana != null)
            return false;
        if (sentencePicture != null ? !sentencePicture.equals(core6K.sentencePicture) : core6K.sentencePicture != null)
            return false;
        if (sentenceReading != null ? !sentenceReading.equals(core6K.sentenceReading) : core6K.sentenceReading != null)
            return false;
        if (sentenceRomaji != null ? !sentenceRomaji.equals(core6K.sentenceRomaji) : core6K.sentenceRomaji != null)
            return false;
        if (sentenceTranslation != null ? !sentenceTranslation.equals(core6K.sentenceTranslation) : core6K.sentenceTranslation != null)
            return false;
        if (word != null ? !word.equals(core6K.word) : core6K.word != null) return false;
        if (wordAudio != null ? !wordAudio.equals(core6K.wordAudio) : core6K.wordAudio != null) return false;
        if (wordFurigana != null ? !wordFurigana.equals(core6K.wordFurigana) : core6K.wordFurigana != null)
            return false;
        if (wordPos != null ? !wordPos.equals(core6K.wordPos) : core6K.wordPos != null) return false;
        if (wordReading != null ? !wordReading.equals(core6K.wordReading) : core6K.wordReading != null) return false;
        if (wordRomaji != null ? !wordRomaji.equals(core6K.wordRomaji) : core6K.wordRomaji != null) return false;
        if (wordTranslation != null ? !wordTranslation.equals(core6K.wordTranslation) : core6K.wordTranslation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (wordReading != null ? wordReading.hashCode() : 0);
        result = 31 * result + (wordFurigana != null ? wordFurigana.hashCode() : 0);
        result = 31 * result + (wordRomaji != null ? wordRomaji.hashCode() : 0);
        result = 31 * result + (wordTranslation != null ? wordTranslation.hashCode() : 0);
        result = 31 * result + (wordPos != null ? wordPos.hashCode() : 0);
        result = 31 * result + (wordAudio != null ? wordAudio.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        result = 31 * result + (sentenceReading != null ? sentenceReading.hashCode() : 0);
        result = 31 * result + (sentenceFurigana != null ? sentenceFurigana.hashCode() : 0);
        result = 31 * result + (sentenceRomaji != null ? sentenceRomaji.hashCode() : 0);
        result = 31 * result + (sentenceTranslation != null ? sentenceTranslation.hashCode() : 0);
        result = 31 * result + (sentenceAudio != null ? sentenceAudio.hashCode() : 0);
        result = 31 * result + (sentencePicture != null ? sentencePicture.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (withJmdict ? 1 : 0);
        return result;
    }
}
