package ee.esutoniagodesu.domain.freq.table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tofu_sentence", schema = "freq", catalog = "egd")
public final class TofuSentence implements Serializable {

    private static final long serialVersionUID = -1044326675149260942L;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    private Integer id;
    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String word;
    @Column(name = "sentence", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String sentence;
    @Transient
    @JsonIgnore
    private TofuSentenceTranslation translation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonGetter
    public TofuSentenceTranslation getTranslation() {
        return translation;
    }

    @JsonSetter
    public void setTranslation(TofuSentenceTranslation translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentence that = (TofuSentence) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sentence != null ? !sentence.equals(that.sentence) : that.sentence != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        return result;
    }
}
