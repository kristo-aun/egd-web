package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Immutable
@Table(name = "core_10k", schema = "core", catalog = "egd")
@Entity
public final class Core10K implements IHasCoreWord, IHasCoreSentence, Serializable {

    private static final long serialVersionUID = -7495908234924364441L;

    private Integer id;
    private String word;
    private String wordReading;
    private String sentence;
    private String sentenceAudio;

    private String wordAudio;
    private String wordTranslation;
    private String mnemonic;
    private String masterIndex;
    private String level;

    private String sentenceReading;
    private String sentenceTranslation;
    private String wordAltDef;
    private boolean withJmdict;
    private Collection<MtmCore10KKanji> mtmCore10KKanjis;

    private int wordKanjiCount;

    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "level", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "master_index", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMasterIndex() {
        return masterIndex;
    }

    public void setMasterIndex(String masterIndex) {
        this.masterIndex = masterIndex;
    }

    @Column(name = "mnemonic", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @OneToMany(mappedBy = "core10K")
    public Collection<MtmCore10KKanji> getMtmCore10KKanjis() {
        return mtmCore10KKanjis;
    }

    public void setMtmCore10KKanjis(Collection<MtmCore10KKanji> mtmCore10KKanjis) {
        this.mtmCore10KKanjis = mtmCore10KKanjis;
    }

    @Column(name = "sentence", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Column(name = "sentence_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceAudio() {
        return sentenceAudio;
    }

    public void setSentenceAudio(String sentenceAudio) {
        this.sentenceAudio = sentenceAudio;
    }

    @Column(name = "sentence_reading", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceReading() {
        return sentenceReading;
    }

    public void setSentenceReading(String sentenceReading) {
        this.sentenceReading = sentenceReading;
    }

    @Column(name = "sentence_translation", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentenceTranslation() {
        return sentenceTranslation;
    }

    public void setSentenceTranslation(String sentenceTranslation) {
        this.sentenceTranslation = sentenceTranslation;
    }

    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "word_alt_def", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordAltDef() {
        return wordAltDef;
    }

    public void setWordAltDef(String wordAltDef) {
        this.wordAltDef = wordAltDef;
    }

    @Column(name = "word_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(String wordAudio) {
        this.wordAudio = wordAudio;
    }

    @Column(name = "word_reading", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordReading() {
        return wordReading;
    }

    public void setWordReading(String wordReading) {
        this.wordReading = wordReading;
    }

    @Column(name = "word_translation", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
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

        Core10K core10K = (Core10K) o;

        if (withJmdict != core10K.withJmdict) return false;
        if (id != null ? !id.equals(core10K.id) : core10K.id != null) return false;
        if (level != null ? !level.equals(core10K.level) : core10K.level != null) return false;
        if (masterIndex != null ? !masterIndex.equals(core10K.masterIndex) : core10K.masterIndex != null) return false;
        if (mnemonic != null ? !mnemonic.equals(core10K.mnemonic) : core10K.mnemonic != null) return false;
        if (sentence != null ? !sentence.equals(core10K.sentence) : core10K.sentence != null) return false;
        if (sentenceAudio != null ? !sentenceAudio.equals(core10K.sentenceAudio) : core10K.sentenceAudio != null)
            return false;
        if (sentenceReading != null ? !sentenceReading.equals(core10K.sentenceReading) : core10K.sentenceReading != null)
            return false;
        if (sentenceTranslation != null ? !sentenceTranslation.equals(core10K.sentenceTranslation) : core10K.sentenceTranslation != null)
            return false;
        if (word != null ? !word.equals(core10K.word) : core10K.word != null) return false;
        if (wordAltDef != null ? !wordAltDef.equals(core10K.wordAltDef) : core10K.wordAltDef != null) return false;
        if (wordAudio != null ? !wordAudio.equals(core10K.wordAudio) : core10K.wordAudio != null) return false;
        if (wordReading != null ? !wordReading.equals(core10K.wordReading) : core10K.wordReading != null) return false;
        if (wordTranslation != null ? !wordTranslation.equals(core10K.wordTranslation) : core10K.wordTranslation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (wordReading != null ? wordReading.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        result = 31 * result + (sentenceAudio != null ? sentenceAudio.hashCode() : 0);
        result = 31 * result + (wordAudio != null ? wordAudio.hashCode() : 0);
        result = 31 * result + (wordTranslation != null ? wordTranslation.hashCode() : 0);
        result = 31 * result + (mnemonic != null ? mnemonic.hashCode() : 0);
        result = 31 * result + (masterIndex != null ? masterIndex.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (sentenceReading != null ? sentenceReading.hashCode() : 0);
        result = 31 * result + (sentenceTranslation != null ? sentenceTranslation.hashCode() : 0);
        result = 31 * result + (wordAltDef != null ? wordAltDef.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (withJmdict ? 1 : 0);
        return result;
    }
}
