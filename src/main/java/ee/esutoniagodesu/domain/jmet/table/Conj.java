package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "conj", schema = "jmet", catalog = "egd")
public final class Conj implements Serializable {

    private static final long serialVersionUID = -8678330636371387530L;
    private short id;
    private String name;
    private Collection<Conjo> conjosById;

    @OneToMany(mappedBy = "conjByConj")
    public Collection<Conjo> getConjosById() {
        return conjosById;
    }

    public void setConjosById(Collection<Conjo> conjosById) {
        this.conjosById = conjosById;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conj conj = (Conj) o;

        if (id != conj.id) return false;
        if (name != null ? !name.equals(conj.name) : conj.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
