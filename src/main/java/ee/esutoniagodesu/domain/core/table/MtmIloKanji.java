package ee.esutoniagodesu.domain.core.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_ilo_kanji", schema = "core", catalog = "egd")
@Entity
public final class MtmIloKanji implements Serializable {

    private static final long serialVersionUID = -3884291962430361193L;
    private Integer id;
    private Ilo ilo;
    private IloKanji iloKanji;

    @SequenceGenerator(name = "seq", sequenceName = "core.mtm_ilo_kanji_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "ilo_id", referencedColumnName = "id", nullable = false)
    public Ilo getIlo() {
        return ilo;
    }

    public void setIlo(Ilo ilo) {
        this.ilo = ilo;
    }

    @ManyToOne
    @JoinColumn(name = "ilo_kanji_id", referencedColumnName = "id", nullable = false)
    public IloKanji getIloKanji() {
        return iloKanji;
    }

    public void setIloKanji(IloKanji iloKanji) {
        this.iloKanji = iloKanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmIloKanji that = (MtmIloKanji) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
