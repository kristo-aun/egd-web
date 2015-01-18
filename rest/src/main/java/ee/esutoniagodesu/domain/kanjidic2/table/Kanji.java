package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "kanji", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class Kanji implements Serializable {

    private static final long serialVersionUID = -6403578905457983407L;

    private Integer id;
    private int freq;
    private int grade;
    private int jlpt;
    private String literal;

    private int strokeCount;
    private int ucpDec;
    private Integer gradeIdx;
    private Collection<Codepoint> codepoints;
    private Collection<DicNumber> dicNumbers;

    private Jinmei jinmei;
    private Jouyou jouyou;
    private Collection<KanjiMeaning> kanjiMeanings;
    private Collection<KanjiRadical> kanjiRadicals;
    private Collection<KanjiVariant> kanjiVariants;

    private Collection<MtmKanjiImage> mtmKanjiImages;
    private Collection<MtmKanjiPrimitive> mtmKanjiPrimitives;
    private Collection<MtmKanjiReading> mtmKanjiReadings;
    private Collection<QueryCode> queryCodes;

    private String radicalHint;//not in db

    @OneToMany(mappedBy = "kanji")
    public Collection<Codepoint> getCodepoints() {
        return codepoints;
    }

    public void setCodepoints(Collection<Codepoint> codepoints) {
        this.codepoints = codepoints;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<DicNumber> getDicNumbers() {
        return dicNumbers;
    }

    public void setDicNumbers(Collection<DicNumber> dicNumbers) {
        this.dicNumbers = dicNumbers;
    }

    @Column(name = "freq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Column(name = "grade", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Column(name = "grade_idx", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getGradeIdx() {
        return gradeIdx;
    }

    public void setGradeIdx(Integer gradeIdx) {
        this.gradeIdx = gradeIdx;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "kanji")
    public Jinmei getJinmei() {
        return jinmei;
    }

    public void setJinmei(Jinmei jinmei) {
        this.jinmei = jinmei;
    }

    @Column(name = "jlpt", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getJlpt() {
        return jlpt;
    }

    public void setJlpt(int jlpt) {
        this.jlpt = jlpt;
    }

    @OneToOne(mappedBy = "kanji")
    public Jouyou getJouyou() {
        return jouyou;
    }

    public void setJouyou(Jouyou jouyou) {
        this.jouyou = jouyou;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<KanjiMeaning> getKanjiMeanings() {
        return kanjiMeanings;
    }

    public void setKanjiMeanings(Collection<KanjiMeaning> kanjiMeanings) {
        this.kanjiMeanings = kanjiMeanings;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<KanjiRadical> getKanjiRadicals() {
        return kanjiRadicals;
    }

    public void setKanjiRadicals(Collection<KanjiRadical> kanjiRadicals) {
        this.kanjiRadicals = kanjiRadicals;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<KanjiVariant> getKanjiVariants() {
        return kanjiVariants;
    }

    public void setKanjiVariants(Collection<KanjiVariant> kanjiVariants) {
        this.kanjiVariants = kanjiVariants;
    }

    @Column(name = "literal", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<MtmKanjiImage> getMtmKanjiImages() {
        return mtmKanjiImages;
    }

    public void setMtmKanjiImages(Collection<MtmKanjiImage> mtmKanjiImages) {
        this.mtmKanjiImages = mtmKanjiImages;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<MtmKanjiPrimitive> getMtmKanjiPrimitives() {
        return mtmKanjiPrimitives;
    }

    public void setMtmKanjiPrimitives(Collection<MtmKanjiPrimitive> mtmKanjiPrimitives) {
        this.mtmKanjiPrimitives = mtmKanjiPrimitives;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<MtmKanjiReading> getMtmKanjiReadings() {
        return mtmKanjiReadings;
    }

    public void setMtmKanjiReadings(Collection<MtmKanjiReading> mtmKanjiReadings) {
        this.mtmKanjiReadings = mtmKanjiReadings;
    }

    @OneToMany(mappedBy = "kanji")
    public Collection<QueryCode> getQueryCodes() {
        return queryCodes;
    }

    public void setQueryCodes(Collection<QueryCode> queryCodes) {
        this.queryCodes = queryCodes;
    }

    @Column(name = "stroke_count", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }

    @Column(name = "ucp_dec", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getUcpDec() {
        return ucpDec;
    }

    public void setUcpDec(int ucpDec) {
        this.ucpDec = ucpDec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kanji kanji = (Kanji) o;

        if (freq != kanji.freq) return false;
        if (grade != kanji.grade) return false;
        if (jlpt != kanji.jlpt) return false;
        if (strokeCount != kanji.strokeCount) return false;
        if (ucpDec != kanji.ucpDec) return false;
        if (gradeIdx != null ? !gradeIdx.equals(kanji.gradeIdx) : kanji.gradeIdx != null) return false;
        if (id != null ? !id.equals(kanji.id) : kanji.id != null) return false;
        if (literal != null ? !literal.equals(kanji.literal) : kanji.literal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + freq;
        result = 31 * result + grade;
        result = 31 * result + jlpt;
        result = 31 * result + (literal != null ? literal.hashCode() : 0);
        result = 31 * result + strokeCount;
        result = 31 * result + ucpDec;
        result = 31 * result + (gradeIdx != null ? gradeIdx.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Kanji{" +
                "id=" + id +
                ", freq=" + freq +
                ", grade=" + grade +
                ", jlpt=" + jlpt +
                ", literal='" + literal + '\'' +
                ", strokeCount=" + strokeCount +
                '}';
    }

    public void setRadicalHint(String radicalHint) {
        this.radicalHint = radicalHint;
    }

    @Transient
    public String getRadicalHint() {
        return radicalHint;
    }
}
