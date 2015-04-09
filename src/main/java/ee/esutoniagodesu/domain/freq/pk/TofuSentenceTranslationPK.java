package ee.esutoniagodesu.domain.freq.pk;

import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TofuSentenceTranslationPK implements Serializable {

    private static final long serialVersionUID = -1127669388929874569L;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TofuSentenceTranslationPK that = (TofuSentenceTranslationPK) o;

        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (tofuSentence != null ? !tofuSentence.equals(that.tofuSentence) : that.tofuSentence != null) return false;

        return true;
    }

    public int hashCode() {
        int result = createdBy != null ? createdBy.hashCode() : 0;
        result = 31 * result + (tofuSentence != null ? tofuSentence.hashCode() : 0);
        return result;
    }
}
