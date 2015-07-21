package ee.esutoniagodesu.domain.core.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Immutable
@Table(name = "mtm_core_10k_kanji", schema = "core")
@Entity
public final class MtmCore10KKanji implements Serializable {

    private static final long serialVersionUID = 2491361553647364212L;
    private Integer id;
    private Core10K core10K;
    private Core10KKanji core10KKanji;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "core_10k_id", referencedColumnName = "id", nullable = false)
    public Core10K getCore10K() {
        return core10K;
    }

    public void setCore10K(Core10K core10K) {
        this.core10K = core10K;
    }

    @ManyToOne
    @JoinColumn(name = "core_10k_kanji_id", referencedColumnName = "id", nullable = false)
    public Core10KKanji getCore10KKanji() {
        return core10KKanji;
    }

    public void setCore10KKanji(Core10KKanji core10KKanji) {
        this.core10KKanji = core10KKanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmCore10KKanji that = (MtmCore10KKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
