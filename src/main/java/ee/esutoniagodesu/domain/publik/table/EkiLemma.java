package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "eki_lemma", schema = "public", catalog = "egd")
@Entity
public final class EkiLemma implements Serializable {

    private static final long serialVersionUID = 3062401860004947363L;

    private Integer id;
    private String lemma;

    @SequenceGenerator(name = "seq", sequenceName = "public.eki_lemma_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "lemma", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EkiLemma ekiLemma = (EkiLemma) o;

        if (id != ekiLemma.id) return false;
        if (lemma != null ? !lemma.equals(ekiLemma.lemma) : ekiLemma.lemma != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lemma != null ? lemma.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
