package ee.esutoniagodesu.domain.estwn.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "word_meaning", schema = "estwn")
public final class WordMeaning implements Serializable {

    private static final long serialVersionUID = 9002333005232027907L;
    private Integer id;
    private String pos;
    @JsonIgnore
    private Collection<Variant> variants;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pos", nullable = false, insertable = true, updatable = true, length = 1)
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @OneToMany(mappedBy = "wordMeaning")
    public Collection<Variant> getVariants() {
        return variants;
    }

    public void setVariants(Collection<Variant> variants) {
        this.variants = variants;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordMeaning that = (WordMeaning) o;

        if (id != that.id) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        return result;
    }
}
