package ee.esutoniagodesu.domain.test.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.domain.publik.table.Audio;
import ee.esutoniagodesu.pojo.dto.VocabularyDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "article_paragraph", schema = "test")
public final class ArticleParagraph {

    private Integer id;
    private String txt;
    private String transcript;
    @JsonIgnore
    private Audio audio;
    private int order;
    @JsonIgnore
    private Article article;

    @Id
    @SequenceGenerator(name = "seq", sequenceName = "test.article_paragraph_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @ManyToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @Basic
    @Column(name = "order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Column(name = "transcript")
    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    @Column(name = "txt")
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleParagraph that = (ArticleParagraph) o;

        if (id != that.id) return false;
        if (order != that.order) return false;
        if (transcript != null ? !transcript.equals(that.transcript) : that.transcript != null) return false;
        if (txt != null ? !txt.equals(that.txt) : that.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (transcript != null ? transcript.hashCode() : 0);
        result = 31 * result + order;
        return result;
    }
}
