package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "phrase_et", schema = "public", catalog = "egd")
@Entity
public final class PhraseEt implements Serializable {

    private static final long serialVersionUID = -1726844750684736152L;

    private Integer id;
    private String et;
    private Audio audio;
    private CfOrigin cfOrigin;

    @ManyToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @ManyToOne
    @JoinColumn(name = "cf_origin_for_phrase_et", referencedColumnName = "id")
    public CfOrigin getCfOrigin() {
        return cfOrigin;
    }

    public void setCfOrigin(CfOrigin cfOrigin) {
        this.cfOrigin = cfOrigin;
    }

    @Column(name = "et", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhraseEt phraseEt = (PhraseEt) o;

        if (id != phraseEt.id) return false;
        if (et != null ? !et.equals(phraseEt.et) : phraseEt.et != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = et != null ? et.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
