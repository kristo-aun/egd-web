package ee.esutoniagodesu.domain.freq.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "tofu_sentence_translation", schema = "freq", catalog = "egd")
@Entity
public class TofuSentenceTranslation implements Serializable {

    private Integer id;
    private String lang;
    private String translation;
    private String createdBy;
    @JsonIgnore
    private TofuSentence tofuSentence;

    @SequenceGenerator(name = "seq", sequenceName = "freq.tofu_sentence_translation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @CreatedBy
    @NotNull
    @Column(name = "created_by", length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne
    @JoinColumn(name = "tofu_sentence_id", referencedColumnName = "id", nullable = false)
    public TofuSentence getTofuSentence() {
        return tofuSentence;
    }

    public void setTofuSentence(TofuSentence tofuSentence) {
        this.tofuSentence = tofuSentence;
    }

    @Column(name = "lang", length = 2)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column(name = "translation")
    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentenceTranslation that = (TofuSentenceTranslation) o;

        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;

        return true;
    }

    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }
}
