package ee.esutoniagodesu.pojo.dto;

import ee.esutoniagodesu.domain.AbstractAuditingEntity;

import java.io.Serializable;

/**
 * Kasutusel ainult artiklite nimekirja transportimisel, et vältida paragrahvide serialiseerimist.
 * */
public class ArticleDTO extends AbstractAuditingEntity implements Serializable {

    private Integer id;
    private String author;
    private String copyright;
    private String title;
    private String transcriptLang;
    private boolean shared;

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        ArticleDTO article = (ArticleDTO) o;

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
                ", shared=" + shared +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
