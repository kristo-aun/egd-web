package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "codepoint", schema = "kanjidic2")
@Entity
@Immutable
public final class Codepoint implements Serializable {

    private static final long serialVersionUID = -2657567526594075404L;
    private Integer id;
    private String cpType;
    private String cpValue;
    private Kanji kanji;

    @Column(name = "cp_type", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getCpType() {
        return cpType;
    }

    public void setCpType(String cpType) {
        this.cpType = cpType;
    }

    @Column(name = "cp_value", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getCpValue() {
        return cpValue;
    }

    public void setCpValue(String cpValue) {
        this.cpValue = cpValue;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Codepoint codepoint = (Codepoint) o;

        if (cpType != null ? !cpType.equals(codepoint.cpType) : codepoint.cpType != null) return false;
        if (cpValue != null ? !cpValue.equals(codepoint.cpValue) : codepoint.cpValue != null) return false;
        if (id != null ? !id.equals(codepoint.id) : codepoint.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cpType != null ? cpType.hashCode() : 0);
        result = 31 * result + (cpValue != null ? cpValue.hashCode() : 0);
        return result;
    }
}
