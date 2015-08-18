package ee.esutoniagodesu.domain.heisig.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "v_heisig6_custom", schema = "heisig")
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

    private String myKeyword;
    @JsonIgnore
    private String jpWord;
    @JsonIgnore
    private String jpWordReading;
    @JsonIgnore
    private byte[] jpWordAudio;
    @JsonIgnore
    private String jpWordAudioFileName;

    @JsonIgnore
    private String jpWordTranslation;
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
    private String jpWordAudioHtml;
    private Integer jouyou;
    private Integer jlpt;

    private String keywordEnAudioFileName;

    @Transient
    public String getKeywordEnAudioFileName() {
        return keywordEnAudioFileName;
    }

    public void setKeywordEnAudioFileName(String keywordEnAudioFileName) {
        this.keywordEnAudioFileName = keywordEnAudioFileName;
    }

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
    public String getJpWordAudioHtml() {
        return jpWordAudioHtml;
    }

    public void setJpWordAudioHtml(String jpWordAudioHtml) {
        this.jpWordAudioHtml = jpWordAudioHtml;
    }

    @Transient
    public Integer getJouyou() {
        return jouyou;
    }

    public void setJouyou(Integer jouyou) {
        this.jouyou = jouyou;
    }

    @Transient
    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
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
    public String getMyKeyword() {
        return myKeyword;
    }

    public void setMyKeyword(String myKeyword) {
        this.myKeyword = myKeyword;
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
    public String getJpWord() {
        return jpWord;
    }

    public void setJpWord(String jpWord) {
        this.jpWord = jpWord;
    }

    @Column(name = "word_reading", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJpWordReading() {
        return jpWordReading;
    }

    public void setJpWordReading(String jpWordReading) {
        this.jpWordReading = jpWordReading;
    }

    @Column(name = "word_audio", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getJpWordAudio() {
        return jpWordAudio;
    }

    public void setJpWordAudio(byte[] jpWordAudio) {
        this.jpWordAudio = jpWordAudio;
    }

    @Column(name = "word_audio_file_name", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJpWordAudioFileName() {
        return jpWordAudioFileName;
    }

    public void setJpWordAudioFileName(String jpWordAudioFileName) {
        this.jpWordAudioFileName = jpWordAudioFileName;
    }

    @Column(name = "word_translation", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJpWordTranslation() {
        return jpWordTranslation;
    }

    public void setJpWordTranslation(String jpWordTranslation) {
        this.jpWordTranslation = jpWordTranslation;
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
