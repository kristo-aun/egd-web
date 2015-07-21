package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "ssum", schema = "jmen")
public final class EN_Ssum implements Serializable {

    private static final long serialVersionUID = -8110377213983047167L;
    private Integer entr;
    private Short sens;
    private String gloss;
    private String notes;

    @Id
    @Basic
    @Column(name = "entr", nullable = true, insertable = true, updatable = true)
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
    }

    @Basic
    @Column(name = "gloss", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Short getSens() {
        return sens;
    }

    public void setSens(Short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Ssum ssum = (EN_Ssum) o;

        if (entr != null ? !entr.equals(ssum.entr) : ssum.entr != null) return false;
        if (gloss != null ? !gloss.equals(ssum.gloss) : ssum.gloss != null) return false;
        if (notes != null ? !notes.equals(ssum.notes) : ssum.notes != null) return false;
        if (sens != null ? !sens.equals(ssum.sens) : ssum.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
