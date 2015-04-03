package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "cf_origin", schema = "public", catalog = "egd")
@Entity
public final class CfOrigin implements Serializable {

    private static final long serialVersionUID = 21179159725642627L;

    private Integer id;
    private Classifier classifier;
    @JsonIgnore
    private Collection<EnSentence> enSentences;
    @JsonIgnore
    private Collection<EtSentence> etSentences;
    @JsonIgnore
    private Collection<Image> images;

    @JsonIgnore
    private Collection<JmGlossInf> jmGlossInfs;
    @JsonIgnore
    private Collection<JpSentence> jpSentences;
    @JsonIgnore
    private Collection<PhraseEt> phraseEts;

    @ManyToOne
    @JoinColumn(name = "classifier_id", referencedColumnName = "id", nullable = false)
    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<EnSentence> getEnSentences() {
        return enSentences;
    }

    public void setEnSentences(Collection<EnSentence> enSentences) {
        this.enSentences = enSentences;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<EtSentence> getEtSentences() {
        return etSentences;
    }

    public void setEtSentences(Collection<EtSentence> etSentences) {
        this.etSentences = etSentences;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<JmGlossInf> getJmGlossInfs() {
        return jmGlossInfs;
    }

    public void setJmGlossInfs(Collection<JmGlossInf> jmGlossInfs) {
        this.jmGlossInfs = jmGlossInfs;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<JpSentence> getJpSentences() {
        return jpSentences;
    }

    public void setJpSentences(Collection<JpSentence> jpSentences) {
        this.jpSentences = jpSentences;
    }

    @OneToMany(mappedBy = "cfOrigin")
    public Collection<PhraseEt> getPhraseEts() {
        return phraseEts;
    }

    public void setPhraseEts(Collection<PhraseEt> phraseEts) {
        this.phraseEts = phraseEts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfOrigin cfOrigin = (CfOrigin) o;

        if (id != cfOrigin.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
