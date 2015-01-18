package ee.esutoniagodesu.domain.estwn.table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "example", schema = "estwn", catalog = "egd")
public final class Example implements Serializable {

    private static final long serialVersionUID = -1482217751897062542L;
    private Integer id;
    private String example;
    private Variant variant;

    @SequenceGenerator(name = "seq", sequenceName = "estwn.example_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "example", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Example example1 = (Example) o;

        if (id != example1.id) return false;
        if (example != null ? !example.equals(example1.example) : example1.example != null) return false;

        return true;
    }

    public int hashCode() {
        int result = example != null ? example.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
