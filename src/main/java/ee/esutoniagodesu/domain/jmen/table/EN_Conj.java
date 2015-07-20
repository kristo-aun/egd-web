package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "conj", schema = "jmen", catalog = "egd")
public final class EN_Conj implements Serializable {

    private static final long serialVersionUID = -8678330636371387530L;
    private short id;
    private String name;
    private Collection<EN_Conjo> conjosById;

    @OneToMany(mappedBy = "conjByConj")
    public Collection<EN_Conjo> getConjosById() {
        return conjosById;
    }

    public void setConjosById(Collection<EN_Conjo> conjosById) {
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

        EN_Conj conj = (EN_Conj) o;

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
