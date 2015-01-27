package ee.esutoniagodesu.domain.core.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Table(name = "v_core_stats", schema = "core", catalog = "egd")
@Immutable
@Entity
public class VCoreStats {

    private String resource;
    private Integer wordCount;
    private Integer wordsWithKanjiCount;
    private Integer maxKanjiCount;
    private Double avgKanjiCount;
    private Integer withJmdictCount;
    private Double withJmdictShare;

    @Column(name = "resource", insertable = false, updatable = false)
    @Basic
    @Id
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Column(name = "word_count", insertable = false, updatable = false, columnDefinition = "int8")
    @Basic
    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    @Column(name = "words_with_kanji_count", insertable = false, updatable = false, columnDefinition = "int8")
    @Basic
    public Integer getWordsWithKanjiCount() {
        return wordsWithKanjiCount;
    }

    public void setWordsWithKanjiCount(Integer wordsWithKanjiCount) {
        this.wordsWithKanjiCount = wordsWithKanjiCount;
    }

    @Column(name = "max_kanji_count", insertable = false, updatable = false, columnDefinition = "int8")
    @Basic
    public Integer getMaxKanjiCount() {
        return maxKanjiCount;
    }

    public void setMaxKanjiCount(Integer maxKanjiCount) {
        this.maxKanjiCount = maxKanjiCount;
    }

    @Column(name = "avg_kanji_count", length = 10, precision = 2, columnDefinition = "numeric", insertable = false, updatable = false)
    @Basic
    public Double getAvgKanjiCount() {
        return avgKanjiCount;
    }

    public void setAvgKanjiCount(Double avgKanjiCount) {
        this.avgKanjiCount = avgKanjiCount;
    }

    @Column(name = "with_jmdict_count", insertable = false, updatable = false, columnDefinition = "int8")
    @Basic
    public Integer getWithJmdictCount() {
        return withJmdictCount;
    }

    public void setWithJmdictCount(Integer withJmdictCount) {
        this.withJmdictCount = withJmdictCount;
    }

    @Column(name = "with_jmdict_share", length = 10, precision = 2, columnDefinition = "numeric", insertable = false, updatable = false)
    @Basic
    public Double getWithJmdictShare() {
        return withJmdictShare;
    }

    public void setWithJmdictShare(Double withJmdictShare) {
        this.withJmdictShare = withJmdictShare;
    }
}
