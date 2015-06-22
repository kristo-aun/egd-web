package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "heisig4", schema = "heisig", catalog = "egd")
@Entity
public final class Heisig4 implements Serializable {

    private static final long serialVersionUID = 8288115990997402660L;

    private Integer id;
    private String heisigComment;
    private String heisigStory;
    private String keywordEn;
    private int lessonNo;
    private String myStory;

    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Column(name = "keyword_en", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKeywordEn() {
        return keywordEn;
    }

    public void setKeywordEn(String keywordEn) {
        this.keywordEn = keywordEn;
    }

    @Column(name = "lesson_no", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getLessonNo() {
        return lessonNo;
    }

    public void setLessonNo(int lessonNo) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Heisig4 heisig4 = (Heisig4) o;

        if (lessonNo != heisig4.lessonNo) return false;
        if (heisigComment != null ? !heisigComment.equals(heisig4.heisigComment) : heisig4.heisigComment != null)
            return false;
        if (heisigStory != null ? !heisigStory.equals(heisig4.heisigStory) : heisig4.heisigStory != null) return false;
        if (id != null ? !id.equals(heisig4.id) : heisig4.id != null) return false;
        if (keywordEn != null ? !keywordEn.equals(heisig4.keywordEn) : heisig4.keywordEn != null) return false;
        if (myStory != null ? !myStory.equals(heisig4.myStory) : heisig4.myStory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = heisigComment != null ? heisigComment.hashCode() : 0;
        result = 31 * result + (heisigStory != null ? heisigStory.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (keywordEn != null ? keywordEn.hashCode() : 0);
        result = 31 * result + lessonNo;
        result = 31 * result + (myStory != null ? myStory.hashCode() : 0);
        return result;
    }
}
