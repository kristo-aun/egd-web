package ee.esutoniagodesu.domain.heisig.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "v_heisig6_custom", schema = "heisig", catalog = "egd")
@Immutable
@Entity
public final class VHeisig6Custom implements Serializable {

    private static final long serialVersionUID = 3095886777739678591L;

    private Integer id;
    private String kanji;
    @JsonIgnore
    private String heisigStory;
    @JsonIgnore
    private String heisigComment;
    private String keywordEn;

    private String keywordEt;
    @JsonIgnore
    private String word;
    @JsonIgnore
    private String wordReading;
    @JsonIgnore
    private byte[] wordAudio;
    @JsonIgnore
    private String wordAudioFileName;

    @JsonIgnore
    private String wordTranslation;
    @JsonIgnore
    private String myStory;
    private Integer strokeCount;
    private String imageSha;

    private String constituents;
    private Integer lessonNo;
    private String onYomi;
    private String kunYomi;

    @JsonIgnore
    private String koohiiStory1;
    @JsonIgnore
    private String koohiiStory2;
    private Integer frameNo4;

    //transient
    @JsonIgnore
    private List<String[]> exampleWords;
    @JsonIgnore
    private String strokeImageHtml;
    @JsonIgnore
    private String exampleWordsHtml;

    @JsonIgnore
    private String wordAudioHtml;
    private int jouyou;
    private int jlpt;

    @Transient
    public List<String[]> getExampleWords() {
        return exampleWords;
    }

    public void setExampleWords(List<String[]> exampleWords) {
        this.exampleWords = exampleWords;
    }

    @Transient
    public String getStrokeImageHtml() {
        return strokeImageHtml;
    }

    public void setStrokeImageHtml(String strokeImageHtml) {
        this.strokeImageHtml = strokeImageHtml;
    }

    @Transient
    public String getExampleWordsHtml() {
        return exampleWordsHtml;
    }

    public void setExampleWordsHtml(String exampleWordsHtml) {
        this.exampleWordsHtml = exampleWordsHtml;
    }

    @Transient
    public String getWordAudioHtml() {
        return wordAudioHtml;
    }

    public void setWordAudioHtml(String wordAudioHtml) {
        this.wordAudioHtml = wordAudioHtml;
    }

    @Transient
    public int getJouyou() {
        return jouyou;
    }

    public void setJouyou(int jouyou) {
        this.jouyou = jouyou;
    }

    @Transient
    public int getJlpt() {
        return jlpt;
    }

    public void setJlpt(int jlpt) {
        this.jlpt = jlpt;
    }

    @Column(name = "id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "constituents", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getConstituents() {
        return constituents;
    }

    public void setConstituents(String constituents) {
        this.constituents = constituents;
    }

    @Column(name = "frame_no4", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getFrameNo4() {
        return frameNo4;
    }

    public void setFrameNo4(Integer frameNo4) {
        this.frameNo4 = frameNo4;
    }

    @Column(name = "heisig_comment", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getHeisigComment() {
        return heisigComment;
    }

    public void setHeisigComment(String heisigComment) {
        this.heisigComment = heisigComment;
    }

    @Column(name = "heisig_story", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getHeisigStory() {
        return heisigStory;
    }

    public void setHeisigStory(String heisigStory) {
        this.heisigStory = heisigStory;
    }

    @Column(name = "kanji", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Column(name = "keyword_en", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKeywordEn() {
        return keywordEn;
    }

    public void setKeywordEn(String keywordEn) {
        this.keywordEn = keywordEn;
    }

    @Column(name = "keyword_et", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKeywordEt() {
        return keywordEt;
    }

    public void setKeywordEt(String keywordEt) {
        this.keywordEt = keywordEt;
    }

    @Column(name = "koohii_story_1", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKoohiiStory1() {
        return koohiiStory1;
    }

    public void setKoohiiStory1(String koohiiStory1) {
        this.koohiiStory1 = koohiiStory1;
    }

    @Column(name = "koohii_story_2", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKoohiiStory2() {
        return koohiiStory2;
    }

    public void setKoohiiStory2(String koohiiStory2) {
        this.koohiiStory2 = koohiiStory2;
    }

    @Column(name = "kun_yomi", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKunYomi() {
        return kunYomi;
    }

    public void setKunYomi(String kunYomi) {
        this.kunYomi = kunYomi;
    }

    @Column(name = "lesson_no", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getLessonNo() {
        return lessonNo;
    }

    public void setLessonNo(Integer lessonNo) {
        this.lessonNo = lessonNo;
    }

    @Column(name = "my_story", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMyStory() {
        return myStory;
    }

    public void setMyStory(String myStory) {
        this.myStory = myStory;
    }

    @Column(name = "on_yomi", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getOnYomi() {
        return onYomi;
    }

    public void setOnYomi(String onYomi) {
        this.onYomi = onYomi;
    }

    @Column(name = "stroke_count", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(Integer strokeCount) {
        this.strokeCount = strokeCount;
    }

    @Column(name = "image_sha")
    @Basic
    public String getImageSha() {
        return imageSha;
    }

    public void setImageSha(String imageSha) {
        this.imageSha = imageSha;
    }

    @Column(name = "word", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "word_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(byte[] wordAudio) {
        this.wordAudio = wordAudio;
    }

    @Column(name = "word_audio_file_name", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordAudioFileName() {
        return wordAudioFileName;
    }

    public void setWordAudioFileName(String wordAudioFileName) {
        this.wordAudioFileName = wordAudioFileName;
    }

    @Column(name = "word_reading", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordReading() {
        return wordReading;
    }

    public void setWordReading(String wordReading) {
        this.wordReading = wordReading;
    }

    @Column(name = "word_translation", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(String wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VHeisig6Custom that = (VHeisig6Custom) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
