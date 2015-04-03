package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "heisig6", schema = "heisig", catalog = "egd")
@Entity
public final class Heisig6 implements Serializable {

    private static final long serialVersionUID = 6564862196517947544L;

    private Integer id;
    private String constituents;
    private String heisigComment;
    private String heisigStory;
    private String keywordEn;

    private String keywordEt;
    private String keywordJp;
    private int lessonNo;
    private String myStory;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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

    @Column(name = "keyword_en", insertable = true, updatable = true, length = 2147483647, precision = 0)
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

    @Column(name = "keyword_jp", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKeywordJp() {
        return keywordJp;
    }

    public void setKeywordJp(String keywordJp) {
        this.keywordJp = keywordJp;
    }

    @Column(name = "lesson_no", insertable = true, updatable = true, length = 10, precision = 0)
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

        Heisig6 heisig6 = (Heisig6) o;

        if (lessonNo != heisig6.lessonNo) return false;
        if (constituents != null ? !constituents.equals(heisig6.constituents) : heisig6.constituents != null)
            return false;
        if (heisigComment != null ? !heisigComment.equals(heisig6.heisigComment) : heisig6.heisigComment != null)
            return false;
        if (heisigStory != null ? !heisigStory.equals(heisig6.heisigStory) : heisig6.heisigStory != null) return false;
        if (id != null ? !id.equals(heisig6.id) : heisig6.id != null) return false;
        if (keywordEn != null ? !keywordEn.equals(heisig6.keywordEn) : heisig6.keywordEn != null) return false;
        if (keywordEt != null ? !keywordEt.equals(heisig6.keywordEt) : heisig6.keywordEt != null) return false;
        if (keywordJp != null ? !keywordJp.equals(heisig6.keywordJp) : heisig6.keywordJp != null) return false;
        if (myStory != null ? !myStory.equals(heisig6.myStory) : heisig6.myStory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = constituents != null ? constituents.hashCode() : 0;
        result = 31 * result + (heisigComment != null ? heisigComment.hashCode() : 0);
        result = 31 * result + (heisigStory != null ? heisigStory.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (keywordEn != null ? keywordEn.hashCode() : 0);
        result = 31 * result + (keywordEt != null ? keywordEt.hashCode() : 0);
        result = 31 * result + (keywordJp != null ? keywordJp.hashCode() : 0);
        result = 31 * result + lessonNo;
        result = 31 * result + (myStory != null ? myStory.hashCode() : 0);
        return result;
    }
}
