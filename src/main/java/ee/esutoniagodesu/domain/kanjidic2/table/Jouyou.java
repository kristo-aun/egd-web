package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "jouyou", schema = "kanjidic2")
@Entity
@Immutable
public final class Jouyou implements Serializable {

    private static final long serialVersionUID = 3946301493287345019L;
    private int grade;
    private int gradeSeq;
    private String meaningEn;
    private String oldKanji;
    private String radical;
    private Integer yearAdded;
    private int kanjiId;
    private Integer jouyouId;
    private Kanji kanji;

    @Column(name = "kanji_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getKanjiId() {
        return kanjiId;
    }

    public void setKanjiId(int kanjiId) {
        this.kanjiId = kanjiId;
    }

    @Column(name = "grade", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Column(name = "grade_seq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getGradeSeq() {
        return gradeSeq;
    }

    public void setGradeSeq(int gradeSeq) {
        this.gradeSeq = gradeSeq;
    }

    @Column(name = "jouyou_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getJouyouId() {
        return jouyouId;
    }

    public void setJouyouId(Integer jouyouId) {
        this.jouyouId = jouyouId;
    }

    @OneToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "meaning_en", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMeaningEn() {
        return meaningEn;
    }

    public void setMeaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
    }

    @Column(name = "old_kanji", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getOldKanji() {
        return oldKanji;
    }

    public void setOldKanji(String oldKanji) {
        this.oldKanji = oldKanji;
    }

    @Column(name = "radical", nullable = true, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public String getRadical() {
        return radical;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    @Column(name = "year_added", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getYearAdded() {
        return yearAdded;
    }

    public void setYearAdded(Integer yearAdded) {
        this.yearAdded = yearAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jouyou jouyou = (Jouyou) o;

        if (grade != jouyou.grade) return false;
        if (gradeSeq != jouyou.gradeSeq) return false;
        if (kanjiId != jouyou.kanjiId) return false;
        if (jouyouId != null ? !jouyouId.equals(jouyou.jouyouId) : jouyou.jouyouId != null) return false;
        if (meaningEn != null ? !meaningEn.equals(jouyou.meaningEn) : jouyou.meaningEn != null) return false;
        if (oldKanji != null ? !oldKanji.equals(jouyou.oldKanji) : jouyou.oldKanji != null) return false;
        if (radical != null ? !radical.equals(jouyou.radical) : jouyou.radical != null) return false;
        if (yearAdded != null ? !yearAdded.equals(jouyou.yearAdded) : jouyou.yearAdded != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = grade;
        result = 31 * result + gradeSeq;
        result = 31 * result + (meaningEn != null ? meaningEn.hashCode() : 0);
        result = 31 * result + (oldKanji != null ? oldKanji.hashCode() : 0);
        result = 31 * result + (radical != null ? radical.hashCode() : 0);
        result = 31 * result + (yearAdded != null ? yearAdded.hashCode() : 0);
        result = 31 * result + kanjiId;
        result = 31 * result + (jouyouId != null ? jouyouId.hashCode() : 0);
        return result;
    }
}
