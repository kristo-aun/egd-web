package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "kanji_reading", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class KanjiReading implements Serializable {

    private static final long serialVersionUID = -8080943749705748396L;
    private Integer id;
    private String literal;
    private String romaji;
    private CfReadingType cfReadingType;
    private Collection<MtmKanjiReading> mtmKanjiReadingsById;

    @ManyToOne
    @JoinColumn(name = "cf_reading_type_id", referencedColumnName = "id", nullable = false)
    public CfReadingType getCfReadingType() {
        return cfReadingType;
    }

    public void setCfReadingType(CfReadingType cfReadingType) {
        this.cfReadingType = cfReadingType;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "literal", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @OneToMany(mappedBy = "kanjiReading")
    public Collection<MtmKanjiReading> getMtmKanjiReadingsById() {
        return mtmKanjiReadingsById;
    }

    public void setMtmKanjiReadingsById(Collection<MtmKanjiReading> mtmKanjiReadingsById) {
        this.mtmKanjiReadingsById = mtmKanjiReadingsById;
    }

    @Column(name = "romaji", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiReading that = (KanjiReading) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (literal != null ? !literal.equals(that.literal) : that.literal != null) return false;
        if (romaji != null ? !romaji.equals(that.romaji) : that.romaji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (literal != null ? literal.hashCode() : 0);
        result = 31 * result + (romaji != null ? romaji.hashCode() : 0);
        return result;
    }
}
