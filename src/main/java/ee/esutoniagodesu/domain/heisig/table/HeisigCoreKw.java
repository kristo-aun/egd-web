package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Table(name = "heisig_core_kw", schema = "heisig")
@Entity
public final class HeisigCoreKw implements Serializable {

    private static final long serialVersionUID = 345185031246740937L;

    private Integer id;
    private String kanji;
    private String keywordEn;
    private String word;
    private String wordReading;

    private String wordTranslation;
    private String audioAddr;
    private byte[] wordAudio;
    private String wordAudioFileName;

    private byte[] keywordEnAudio;
    private String keywordEnAudioSrc;

    @Column(name = "keyword_en_audio_src", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getKeywordEnAudioSrc() {
        return keywordEnAudioSrc;
    }

    public void setKeywordEnAudioSrc(String keywordEnAudioSrc) {
        this.keywordEnAudioSrc = keywordEnAudioSrc;
    }

    @Column(name = "keyword_en_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getKeywordEnAudio() {
        return keywordEnAudio;
    }

    public void setKeywordEnAudio(byte[] keywordEnAudio) {
        this.keywordEnAudio = keywordEnAudio;
    }

    @Column(name = "audio_addr", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getAudioAddr() {
        return audioAddr;
    }

    public void setAudioAddr(String audioAddr) {
        this.audioAddr = audioAddr;
    }

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

    @Column(name = "keyword_en", nullable = true, insertable = true, updatable = true, length = 2044, precision = 0)
    @Basic
    public String getKeywordEn() {
        return keywordEn;
    }

    public void setKeywordEn(String keywordEn) {
        this.keywordEn = keywordEn;
    }

    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "word_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(byte[] wordAudio) {
        this.wordAudio = wordAudio;
    }

    @Column(name = "word_audio_file_name", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordAudioFileName() {
        return wordAudioFileName;
    }

    public void setWordAudioFileName(String wordAudioFileName) {
        this.wordAudioFileName = wordAudioFileName;
    }

    @Column(name = "word_reading", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeisigCoreKw that = (HeisigCoreKw) o;

        if (audioAddr != null ? !audioAddr.equals(that.audioAddr) : that.audioAddr != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kanji != null ? !kanji.equals(that.kanji) : that.kanji != null) return false;
        if (keywordEn != null ? !keywordEn.equals(that.keywordEn) : that.keywordEn != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (!Arrays.equals(wordAudio, that.wordAudio)) return false;
        if (wordAudioFileName != null ? !wordAudioFileName.equals(that.wordAudioFileName) : that.wordAudioFileName != null)
            return false;
        if (wordReading != null ? !wordReading.equals(that.wordReading) : that.wordReading != null) return false;
        if (wordTranslation != null ? !wordTranslation.equals(that.wordTranslation) : that.wordTranslation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        result = 31 * result + (keywordEn != null ? keywordEn.hashCode() : 0);
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (wordReading != null ? wordReading.hashCode() : 0);
        result = 31 * result + (wordTranslation != null ? wordTranslation.hashCode() : 0);
        result = 31 * result + (audioAddr != null ? audioAddr.hashCode() : 0);
        result = 31 * result + (wordAudio != null ? Arrays.hashCode(wordAudio) : 0);
        result = 31 * result + (wordAudioFileName != null ? wordAudioFileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HeisigCoreKw{" +
            "id=" + id +
            '}';
    }
}
