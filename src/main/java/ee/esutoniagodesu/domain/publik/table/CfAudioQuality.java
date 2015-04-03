package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "cf_audio_quality", schema = "public", catalog = "egd")
@Entity
public final class CfAudioQuality implements Serializable {

    private static final long serialVersionUID = 7454219193144584499L;
    
    private Integer id;
    @JsonIgnore
    private Collection<Audio> audios;
    private Classifier classifier;

    @OneToMany(mappedBy = "cfAudioQuality")
    public Collection<Audio> getAudios() {
        return audios;
    }

    public void setAudios(Collection<Audio> audios) {
        this.audios = audios;
    }

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

        CfAudioQuality that = (CfAudioQuality) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
