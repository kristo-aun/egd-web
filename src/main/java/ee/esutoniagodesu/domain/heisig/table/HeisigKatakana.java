package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "heisig_katakana", schema = "heisig")
@Entity
public final class HeisigKatakana implements Serializable {

    private static final long serialVersionUID = 8680439419336596959L;

    private String kana;
    private int lesson;
    private int lessonSeq;
    private String myStory;

    @Column(name = "kana", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Id
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @Column(name = "lesson", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    @Column(name = "lesson_seq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getLessonSeq() {
        return lessonSeq;
    }

    public void setLessonSeq(int lessonSeq) {
        this.lessonSeq = lessonSeq;
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

        HeisigKatakana that = (HeisigKatakana) o;

        if (lesson != that.lesson) return false;
        if (lessonSeq != that.lessonSeq) return false;
        if (kana != null ? !kana.equals(that.kana) : that.kana != null) return false;
        if (myStory != null ? !myStory.equals(that.myStory) : that.myStory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kana != null ? kana.hashCode() : 0;
        result = 31 * result + lesson;
        result = 31 * result + lessonSeq;
        result = 31 * result + (myStory != null ? myStory.hashCode() : 0);
        return result;
    }
}
