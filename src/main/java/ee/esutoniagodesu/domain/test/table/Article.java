package ee.esutoniagodesu.domain.test.table;

import ee.esutoniagodesu.domain.AbstractAuditingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 * An Article.
 */
@Entity
@Table(name = "article", schema = "test")
public class Article extends AbstractAuditingEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "author")
    private String author;
    @Column(name = "copyright")
    private String copyright;
    @Column(name = "title")
    private String title;
    @Size(min = 2, max = 2)
    @Column(name = "transcript_lang")
    private String transcriptLang;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    @OrderBy("order")
    private Collection<ArticleParagraph> articleParagraphs;

    @Column(name = "shared")
    private boolean shared;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public Collection<ArticleParagraph> getArticleParagraphs() {
        return articleParagraphs;
    }

    public void setArticleParagraphs(Collection<ArticleParagraph> articleParagraphs) {
        this.articleParagraphs = articleParagraphs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranscriptLang() {
        return transcriptLang;
    }

    public void setTranscriptLang(String transcriptLang) {
        this.transcriptLang = transcriptLang;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (id != null ? !id.equals(article.id) : article.id != null) return false;

        return true;
    }

    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "Article{" +
            "id=" + id +
            ", author='" + author + '\'' +
            ", copyright='" + copyright + '\'' +
            ", title='" + title + '\'' +
            ", transcriptLang='" + transcriptLang + '\'' +
            ", articleParagraphs=" + articleParagraphs +
            ", shared=" + shared +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
