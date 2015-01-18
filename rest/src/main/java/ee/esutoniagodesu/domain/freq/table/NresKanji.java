package ee.esutoniagodesu.domain.freq.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "nres_kanji", schema = "freq", catalog = "egd")
@Entity
public final class NresKanji implements Serializable {

    private static final long serialVersionUID = -217544201397489531L;
    private Integer id;
    private String kanji;
    private Collection<MtmNresKanji> mtmNresKanjis;

    @SequenceGenerator(name = "seq", sequenceName = "freq.nres_kanji_id_seq", allocationSize = 1)
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

    @OneToMany(mappedBy = "nresKanji")
    public Collection<MtmNresKanji> getMtmNresKanjis() {
        return mtmNresKanjis;
    }

    public void setMtmNresKanjis(Collection<MtmNresKanji> mtmNresKanjis) {
        this.mtmNresKanjis = mtmNresKanjis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NresKanji nresKanji = (NresKanji) o;

        if (id != nresKanji.id) return false;
        if (kanji != null ? !kanji.equals(nresKanji.kanji) : nresKanji.kanji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        return result;
    }
}
