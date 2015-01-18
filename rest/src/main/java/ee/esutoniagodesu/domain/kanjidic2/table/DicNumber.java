package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "dic_number", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class DicNumber implements Serializable {

    private static final long serialVersionUID = 7662709475756433824L;
    private Integer id;
    private String dicRef;
    private String drType;
    private Kanji kanji;

    @Column(name = "dic_ref", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getDicRef() {
        return dicRef;
    }

    public void setDicRef(String dicRef) {
        this.dicRef = dicRef;
    }

    @Column(name = "dr_type", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getDrType() {
        return drType;
    }

    public void setDrType(String drType) {
        this.drType = drType;
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

        DicNumber dicNumber = (DicNumber) o;

        if (dicRef != null ? !dicRef.equals(dicNumber.dicRef) : dicNumber.dicRef != null) return false;
        if (drType != null ? !drType.equals(dicNumber.drType) : dicNumber.drType != null) return false;
        if (id != null ? !id.equals(dicNumber.id) : dicNumber.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dicRef != null ? dicRef.hashCode() : 0);
        result = 31 * result + (drType != null ? drType.hashCode() : 0);
        return result;
    }
}
