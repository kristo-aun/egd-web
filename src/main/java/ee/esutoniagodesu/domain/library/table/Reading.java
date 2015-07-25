package ee.esutoniagodesu.domain.library.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import ee.esutoniagodesu.domain.AbstractAuditingEntity;
import ee.esutoniagodesu.util.iso.ISO6391;
import ee.esutoniagodesu.web.rest.dto.View;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "reading", schema = "library")
public class Reading extends AbstractAuditingEntity implements Serializable {

    @JsonView(View.Basic.class)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(View.Basic.class)
    @Column(name = "author")
    private String author;

    @JsonView(View.Detailed.class)
    @Column(name = "copyright")
    private String copyright;

    @JsonView(View.Basic.class)
    @Column(name = "title")
    private String title;

    @JsonView(View.Basic.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "body_lang")
    private ISO6391 bodyLang;

    @JsonView(View.Basic.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "transcript_lang")
    private ISO6391 transcriptLang;

    @JsonView(View.Basic.class)
    @Column(name = "summary")
    private String summary;

    @JsonView(View.Detailed.class)
    @Column(name = "shared")
    private boolean shared;

    @JsonView(View.Basic.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(schema = "library",
        name = "reading_tags",
        joinColumns = @JoinColumn(name = "reading_id", referencedColumnName = "id"))
    @Column(name = "tag")
    private List<String> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ISO6391 getBodyLang() {
        return bodyLang;
    }

    public void setBodyLang(ISO6391 bodyLang) {
        this.bodyLang = bodyLang;
    }

    public ISO6391 getTranscriptLang() {
        return transcriptLang;
    }

    public void setTranscriptLang(ISO6391 transcriptLang) {
        this.transcriptLang = transcriptLang;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reading reading = (Reading) o;

        if (shared != reading.shared) return false;
        if (id != null ? !id.equals(reading.id) : reading.id != null) return false;
        if (author != null ? !author.equals(reading.author) : reading.author != null) return false;
        if (copyright != null ? !copyright.equals(reading.copyright) : reading.copyright != null) return false;
        if (title != null ? !title.equals(reading.title) : reading.title != null) return false;
        if (bodyLang != reading.bodyLang) return false;
        if (transcriptLang != reading.transcriptLang) return false;
        return !(summary != null ? !summary.equals(reading.summary) : reading.summary != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (copyright != null ? copyright.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (bodyLang != null ? bodyLang.hashCode() : 0);
        result = 31 * result + (transcriptLang != null ? transcriptLang.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (shared ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reading{" +
            "id=" + id +
            ", author='" + author + '\'' +
            ", copyright='" + copyright + '\'' +
            ", title='" + title + '\'' +
            ", bodyLang=" + bodyLang +
            ", transcriptLang=" + transcriptLang +
            ", summary='" + summary + '\'' +
            ", shared=" + shared +
            "} " + super.toString();
    }
}
