package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "kanji_variant", schema = "kanjidic2")
@Entity
@Immutable
public final class KanjiVariant implements Serializable {

    private static final long serialVersionUID = 6486346679640021562L;
    private Integer id;
    private String varType;
    private String variant;
    private Kanji kanji;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id")
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "var_type", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    @Column(name = "variant", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiVariant that = (KanjiVariant) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (varType != null ? !varType.equals(that.varType) : that.varType != null) return false;
        if (variant != null ? !variant.equals(that.variant) : that.variant != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (varType != null ? varType.hashCode() : 0);
        result = 31 * result + (variant != null ? variant.hashCode() : 0);
        return result;
    }
}
