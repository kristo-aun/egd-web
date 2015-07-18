package ee.esutoniagodesu.domain.library.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import ee.esutoniagodesu.web.rest.dto.View;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@Embeddable
@Entity
@Table(name = "reading_page", schema = "library")
public class ReadingPage implements Serializable {

    @JsonView(View.Detailed.class)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(View.Detailed.class)
    @Column(name = "body")
    private String body;

    @JsonView(View.Detailed.class)
    @Column(name = "transcript")
    private String transcript;

    @JsonView(View.Detailed.class)
    @Column(name = "audio_sha")
    private String audioSha;

    @JsonView(View.Detailed.class)
    @Column(name = "page")
    private Integer page;

    @JsonView(View.Detailed.class)
    @Column(name = "reading_id")
    private Integer readingId;

    @JsonIgnore
    @Transient
    private MultipartFile audioFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAudioSha() {
        return audioSha;
    }

    public void setAudioSha(String audioSha) {
        this.audioSha = audioSha;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getReadingId() {
        return readingId;
    }

    public void setReadingId(Integer readingId) {
        this.readingId = readingId;
    }

    public MultipartFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(MultipartFile audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReadingPage that = (ReadingPage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (audioSha != null ? !audioSha.equals(that.audioSha) : that.audioSha != null) return false;
        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        return !(readingId != null ? !readingId.equals(that.readingId) : that.readingId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (audioSha != null ? audioSha.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (readingId != null ? readingId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReadingPage{" +
            "id=" + id +
            ", body.length='" + (body != null ? body.length() : null) + '\'' +
            ", transcript.length='" + (transcript != null ? transcript.length() : null) + '\'' +
            ", audioSha='" + audioSha + '\'' +
            ", page=" + page +
            ", readingId=" + readingId +
            '}';
    }
}
