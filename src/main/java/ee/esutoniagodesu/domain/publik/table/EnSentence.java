package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "en_sentence", schema = "public", catalog = "egd")
@Entity
public final class EnSentence implements Serializable {

    private static final long serialVersionUID = -4346672419698648265L;

    private Integer id;
    private String en;
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
    @JoinColumn(name = "cf_origin_for_en_sentence", referencedColumnName = "id", nullable = false)
    public CfOrigin getCfOrigin() {
        return cfOrigin;
    }

    public void setCfOrigin(CfOrigin cfOrigin) {
        this.cfOrigin = cfOrigin;
    }

    @Column(name = "en", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @SequenceGenerator(name = "seq", sequenceName = "public.en_sentence_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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

        EnSentence that = (EnSentence) o;

        if (id != that.id) return false;
        if (en != null ? !en.equals(that.en) : that.en != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (en != null ? en.hashCode() : 0);
        return result;
    }
}
