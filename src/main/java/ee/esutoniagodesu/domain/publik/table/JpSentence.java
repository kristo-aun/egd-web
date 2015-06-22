package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "jp_sentence", schema = "public", catalog = "egd")
@Entity
public final class JpSentence implements Serializable {

    private static final long serialVersionUID = -785254611818960480L;

    private Integer id;
    private String jp;
    private String asKana;
    private String asRomaji;
    private String withFurigana;

    private EnSentence enSentence;
    private EtSentence etSentence;
    private Audio audio;
    @JsonIgnore
    private CfOrigin cfOrigin;
    @JsonIgnore
    private Collection<MtmJmSensJpSentence> mtmJmSensJpSentences;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "as_kana", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getAsKana() {
        return asKana;
    }

    public void setAsKana(String asKana) {
        this.asKana = asKana;
    }

    @Column(name = "as_romaji", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getAsRomaji() {
        return asRomaji;
    }

    public void setAsRomaji(String asRomaji) {
        this.asRomaji = asRomaji;
    }

    @ManyToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @ManyToOne
    @JoinColumn(name = "cf_origin_for_jp_sentence", referencedColumnName = "id", nullable = false)
    public CfOrigin getCfOrigin() {
        return cfOrigin;
    }

    public void setCfOrigin(CfOrigin cfOrigin) {
        this.cfOrigin = cfOrigin;
    }

    @OneToOne(mappedBy = "jpSentence", fetch = FetchType.EAGER)
    public EnSentence getEnSentence() {
        return enSentence;
    }

    public void setEnSentence(EnSentence enSentence) {
        this.enSentence = enSentence;
    }

    @OneToOne(mappedBy = "jpSentence", fetch = FetchType.EAGER)
    public EtSentence getEtSentence() {
        return etSentence;
    }

    public void setEtSentence(EtSentence etSentence) {
        this.etSentence = etSentence;
    }

    @Column(name = "jp", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    @OneToMany(mappedBy = "jpSentence")
    public Collection<MtmJmSensJpSentence> getMtmJmSensJpSentences() {
        return mtmJmSensJpSentences;
    }

    public void setMtmJmSensJpSentences(Collection<MtmJmSensJpSentence> mtmJmSensJpSentences) {
        this.mtmJmSensJpSentences = mtmJmSensJpSentences;
    }

    @Column(name = "with_furigana", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWithFurigana() {
        return withFurigana;
    }

    public void setWithFurigana(String withFurigana) {
        this.withFurigana = withFurigana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpSentence that = (JpSentence) o;

        if (id != that.id) return false;
        if (asKana != null ? !asKana.equals(that.asKana) : that.asKana != null) return false;
        if (asRomaji != null ? !asRomaji.equals(that.asRomaji) : that.asRomaji != null) return false;
        if (jp != null ? !jp.equals(that.jp) : that.jp != null) return false;
        if (withFurigana != null ? !withFurigana.equals(that.withFurigana) : that.withFurigana != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jp != null ? jp.hashCode() : 0;
        result = 31 * result + (asKana != null ? asKana.hashCode() : 0);
        result = 31 * result + (asRomaji != null ? asRomaji.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (withFurigana != null ? withFurigana.hashCode() : 0);
        return result;
    }
}
