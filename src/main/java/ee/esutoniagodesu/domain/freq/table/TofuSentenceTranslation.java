package ee.esutoniagodesu.domain.freq.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.domain.freq.pk.TofuSentenceTranslationPK;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "tofu_sentence_translation", schema = "freq", catalog = "egd")
@Entity
public class TofuSentenceTranslation implements Serializable {

    private String lang;
    private String translation;

    @JsonIgnore
    @EmbeddedId
    private TofuSentenceTranslationPK id;

    public TofuSentenceTranslationPK getId() {
        return id;
    }

    public void setId(TofuSentenceTranslationPK id) {
        this.id = id;
    }

    private String createdBy;
    private TofuSentence tofuSentence;

    @ManyToOne
    @JoinColumn(name = "tofu_sentence_id", referencedColumnName = "id", nullable = false)
    public TofuSentence getTofuSentence() {
        return tofuSentence;
    }

    public void setTofuSentence(TofuSentence tofuSentence) {
        this.tofuSentence = tofuSentence;
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;

        return true;
    }

    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "TofuSentenceTranslation{" +
            "lang='" + lang + '\'' +
            ", translation='" + translation + '\'' +
            ", id=" + id +
            '}';
    }
}
