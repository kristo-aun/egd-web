package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "koohii_story", schema = "heisig", catalog = "egd")
@Entity
public final class KoohiiStory implements Serializable {

    private static final long serialVersionUID = -3527169749303135108L;

    private Integer id;
    private String story;
    private Integer storySeq;

    @SequenceGenerator(name = "seq", sequenceName = "heisig.koohii_story_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "story", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Column(name = "story_seq", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getStorySeq() {
        return storySeq;
    }

    public void setStorySeq(Integer storySeq) {
        this.storySeq = storySeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KoohiiStory that = (KoohiiStory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (story != null ? !story.equals(that.story) : that.story != null) return false;
        if (storySeq != null ? !storySeq.equals(that.storySeq) : that.storySeq != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = story != null ? story.hashCode() : 0;
        result = 31 * result + (storySeq != null ? storySeq.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
