package ee.esutoniagodesu.domain.freq.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "tofu_sentence", schema = "freq", catalog = "egd")
@Immutable
@Entity
public class TofuSentence implements Serializable {

    private Integer id;
    private String word;
    private String sentence;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "word", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "sentence", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentence that = (TofuSentence) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sentence != null ? !sentence.equals(that.sentence) : that.sentence != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (sentence != null ? sentence.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TofuSentence{" +
            "id=" + id +
            ", word='" + word + '\'' +
            ", sentence='" + sentence + '\'' +
            '}';
    }
}
