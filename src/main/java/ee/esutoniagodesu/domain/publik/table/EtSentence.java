package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "et_sentence", schema = "public", catalog = "egd")
@Entity
public final class EtSentence implements Serializable {

    private static final long serialVersionUID = 8726953717623209450L;

    private Integer id;
    private String etSentence;
    @JsonIgnore
    private Audio audio;
    @JsonIgnore
    private CfOrigin cfOrigin;
    @JsonIgnore
    private JpSentence jpSentence;

    @ManyToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @ManyToOne
    @JoinColumn(name = "cf_origin_for_et_sentence", referencedColumnName = "id", nullable = false)
    public CfOrigin getCfOrigin() {
        return cfOrigin;
    }

    public void setCfOrigin(CfOrigin cfOrigin) {
        this.cfOrigin = cfOrigin;
    }

    @Column(name = "et_sentence", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getEtSentence() {
        return etSentence;
    }

    public void setEtSentence(String etSentence) {
        this.etSentence = etSentence;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "jp_sentence_id", referencedColumnName = "id")
    public JpSentence getJpSentence() {
        return jpSentence;
    }

    public void setJpSentence(JpSentence jpSentence) {
        this.jpSentence = jpSentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EtSentence that = (EtSentence) o;

        if (id != that.id) return false;
        if (etSentence != null ? !etSentence.equals(that.etSentence) : that.etSentence != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = etSentence != null ? etSentence.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
