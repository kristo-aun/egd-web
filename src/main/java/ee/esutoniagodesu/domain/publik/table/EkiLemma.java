package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "eki_lemma", schema = "public", catalog = "egd")
@Entity
public final class EkiLemma implements Serializable {

    private static final long serialVersionUID = 3062401860004947363L;

    private Integer id;
    private String et;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "et", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getEt() {
        return et;
    }

    public void setEt(String lemma) {
        this.et = lemma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EkiLemma ekiLemma = (EkiLemma) o;

        if (id != ekiLemma.id) return false;
        if (et != null ? !et.equals(ekiLemma.et) : ekiLemma.et != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = et != null ? et.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
