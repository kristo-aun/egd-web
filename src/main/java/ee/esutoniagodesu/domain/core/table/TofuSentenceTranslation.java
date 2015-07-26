package ee.esutoniagodesu.domain.core.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.util.iso.ISO6391;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "tofu_sentence_translation", schema = "core")
@Entity
public class TofuSentenceTranslation implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private ISO6391 lang;

    @Column(name = "translation")
    private String translation;

    @JsonIgnore
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "tofu_sentence_id")
    private Integer tofuSentenceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getTofuSentenceId() {
        return tofuSentenceId;
    }

    public void setTofuSentenceId(Integer tofuSentenceId) {
        this.tofuSentenceId = tofuSentenceId;
    }

    public ISO6391 getLang() {
        return lang;
    }

    public void setLang(ISO6391 lang) {
        this.lang = lang;
    }


    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentenceTranslation that = (TofuSentenceTranslation) o;

        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TofuSentenceTranslation{" +
            "id=" + id +
            ", lang=" + lang +
            ", translation='" + translation + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", tofuSentenceId=" + tofuSentenceId +
            '}';
    }
}
