package ee.esutoniagodesu.domain.core.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "core_6k_kanji", schema = "core", catalog = "egd")
@Entity
public final class Core6KKanji implements Serializable {

    private static final long serialVersionUID = 6303472990004868172L;
    private Integer id;
    private String kanji;
    private Collection<MtmCore6KKanji> mtmCore6KKanjis;

    @SequenceGenerator(name = "seq", sequenceName = "core.core_6k_kanji_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "kanji", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @OneToMany(mappedBy = "core6KKanji")
    public Collection<MtmCore6KKanji> getMtmCore6KKanjis() {
        return mtmCore6KKanjis;
    }

    public void setMtmCore6KKanjis(Collection<MtmCore6KKanji> mtmCore6KKanjis) {
        this.mtmCore6KKanjis = mtmCore6KKanjis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Core6KKanji that = (Core6KKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kanji != null ? !kanji.equals(that.kanji) : that.kanji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        return result;
    }
}
