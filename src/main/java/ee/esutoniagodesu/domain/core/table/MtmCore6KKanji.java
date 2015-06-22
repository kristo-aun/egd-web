package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Immutable
@Table(name = "mtm_core_6k_kanji", schema = "core", catalog = "egd")
@Entity
public final class MtmCore6KKanji implements Serializable {

    private static final long serialVersionUID = 2764896312154910286L;
    private Integer id;
    private Core6K core6K;
    private Core6KKanji core6KKanji;

    @ManyToOne
    @JoinColumn(name = "core_6k_id", referencedColumnName = "id", nullable = false)
    public Core6K getCore6K() {
        return core6K;
    }

    public void setCore6K(Core6K core6K) {
        this.core6K = core6K;
    }

    @ManyToOne
    @JoinColumn(name = "core_6k_kanji_id", referencedColumnName = "id", nullable = false)
    public Core6KKanji getCore6KKanji() {
        return core6KKanji;
    }

    public void setCore6KKanji(Core6KKanji core6KKanji) {
        this.core6KKanji = core6KKanji;
    }

    @SequenceGenerator(name = "seq", sequenceName = "core.mtm_core_6k_kanji_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmCore6KKanji that = (MtmCore6KKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
