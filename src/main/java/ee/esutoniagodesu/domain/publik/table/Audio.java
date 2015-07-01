package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "audio", schema = "public", catalog = "egd")
public final class Audio implements Serializable {

    private static final long serialVersionUID = -3156175773691906287L;

    private Integer id;
    @JsonIgnore
    private byte[] audioFile;
    @JsonIgnore
    private String fileName;
    @JsonIgnore
    private String copyright;

    @JsonIgnore
    private CfAudioQuality cfAudioQuality;

    @JsonIgnore
    private Collection<EnSentence> enSentences;
    @JsonIgnore
    private Collection<EtSentence> etSentences;
    @JsonIgnore
    private Collection<Hiragana> hiraganas;
    @JsonIgnore
    private Collection<JpSentence> jpSentences;
    @JsonIgnore
    private Collection<Katakana> katakanas;

    @JsonIgnore
    private Collection<PhraseEt> phraseEts;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "audio_file", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile) {
        this.audioFile = audioFile;
    }

    @ManyToOne
    @JoinColumn(name = "cf_audio_quality", referencedColumnName = "id", nullable = false)
    public CfAudioQuality getCfAudioQuality() {
        return cfAudioQuality;
    }

    public void setCfAudioQuality(CfAudioQuality cfAudioQuality) {
        this.cfAudioQuality = cfAudioQuality;
    }

    @Column(name = "copyright", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @OneToMany(mappedBy = "audio")
    public Collection<EnSentence> getEnSentences() {
        return enSentences;
    }

    public void setEnSentences(Collection<EnSentence> enSentences) {
        this.enSentences = enSentences;
    }

    @OneToMany(mappedBy = "audio")
    public Collection<EtSentence> getEtSentences() {
        return etSentences;
    }

    public void setEtSentences(Collection<EtSentence> etSentences) {
        this.etSentences = etSentences;
    }

    @Column(name = "file_name", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @OneToMany(mappedBy = "audio")
    public Collection<Hiragana> getHiraganas() {
        return hiraganas;
    }

    public void setHiraganas(Collection<Hiragana> hiraganas) {
        this.hiraganas = hiraganas;
    }

    @OneToMany(mappedBy = "audio")
    public Collection<JpSentence> getJpSentences() {
        return jpSentences;
    }

    public void setJpSentences(Collection<JpSentence> jpSentences) {
        this.jpSentences = jpSentences;
    }

    @OneToMany(mappedBy = "audio")
    public Collection<Katakana> getKatakanas() {
        return katakanas;
    }

    public void setKatakanas(Collection<Katakana> katakanas) {
        this.katakanas = katakanas;
    }

    @OneToMany(mappedBy = "audio")
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

        Audio audio = (Audio) o;

        if (id != audio.id) return false;
        if (!Arrays.equals(audioFile, audio.audioFile)) return false;
        if (copyright != null ? !copyright.equals(audio.copyright) : audio.copyright != null) return false;
        if (fileName != null ? !fileName.equals(audio.fileName) : audio.fileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (audioFile != null ? Arrays.hashCode(audioFile) : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (copyright != null ? copyright.hashCode() : 0);
        return result;
    }
}
