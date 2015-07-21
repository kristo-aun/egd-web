package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "kanji_primitive", schema = "kanjidic2")
@Entity
@Immutable
public final class KanjiPrimitive implements Serializable {

    private static final long serialVersionUID = 6747435769475707162L;
    private String primitive;
    private Integer id;
    private Collection<MtmKanjiPrimitive> mtmKanjiPrimitives;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "kanjiPrimitive")
    public Collection<MtmKanjiPrimitive> getMtmKanjiPrimitives() {
        return mtmKanjiPrimitives;
    }

    public void setMtmKanjiPrimitives(Collection<MtmKanjiPrimitive> mtmKanjiPrimitives) {
        this.mtmKanjiPrimitives = mtmKanjiPrimitives;
    }

    @Column(name = "primitive", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getPrimitive() {
        return primitive;
    }

    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiPrimitive that = (KanjiPrimitive) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (primitive != null ? !primitive.equals(that.primitive) : that.primitive != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = primitive != null ? primitive.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
