package ee.esutoniagodesu.domain.core.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "core_10k_kanji", schema = "core", catalog = "egd")
@Entity
public final class Core10KKanji implements Serializable {

    private static final long serialVersionUID = 856122524713834732L;
    private Integer id;
    private String kanji;
    private Collection<MtmCore10KKanji> mtmCore10KKanjis;

    @SequenceGenerator(name = "seq", sequenceName = "core.core_10k_kanji_id_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "core10KKanji")
    public Collection<MtmCore10KKanji> getMtmCore10KKanjis() {
        return mtmCore10KKanjis;
    }

    public void setMtmCore10KKanjis(Collection<MtmCore10KKanji> mtmCore10KKanjis) {
        this.mtmCore10KKanjis = mtmCore10KKanjis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Core10KKanji that = (Core10KKanji) o;

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
