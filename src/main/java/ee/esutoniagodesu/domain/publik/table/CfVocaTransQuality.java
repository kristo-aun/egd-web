package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "cf_voca_trans_quality", schema = "public", catalog = "egd")
@Entity
public final class CfVocaTransQuality implements Serializable {

    private static final long serialVersionUID = -689543831340950708L;

    private Integer id;
    private Classifier classifier;

    @ManyToOne
    @JoinColumn(name = "classifier_id", referencedColumnName = "id", nullable = false)
    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfVocaTransQuality that = (CfVocaTransQuality) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
