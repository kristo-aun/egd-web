package ee.esutoniagodesu.domain.jmdict_en.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "conotes", schema = "jmdict_en", catalog = "egd")
public final class EN_Conotes implements Serializable {

    private static final long serialVersionUID = -4809008963018749272L;
    private int id;
    private String txt;
    private Collection<EN_ConjoNotes> conjoNotesesById;

    @OneToMany(mappedBy = "conotesByNote")
    public Collection<EN_ConjoNotes> getConjoNotesesById() {
        return conjoNotesesById;
    }

    public void setConjoNotesesById(Collection<EN_ConjoNotes> conjoNotesesById) {
        this.conjoNotesesById = conjoNotesesById;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Conotes conotes = (EN_Conotes) o;

        if (id != conotes.id) return false;
        if (txt != null ? !txt.equals(conotes.txt) : conotes.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
