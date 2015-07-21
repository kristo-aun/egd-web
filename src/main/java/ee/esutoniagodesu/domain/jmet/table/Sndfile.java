package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "sndfile", schema = "jmet")
public final class Sndfile implements Serializable {

    private static final long serialVersionUID = -481673765495198164L;
    private int id;
    private int vol;
    private String title;
    private String loc;
    private Short type;
    private String notes;
    private Collection<Snd> sndsById;
    private Sndvol sndvolByVol;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "loc", nullable = true, insertable = true, updatable = true, length = 500)
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @OneToMany(mappedBy = "sndfileByFile")
    public Collection<Snd> getSndsById() {
        return sndsById;
    }

    public void setSndsById(Collection<Snd> sndsById) {
        this.sndsById = sndsById;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "vol", referencedColumnName = "id", nullable = false)
    public Sndvol getSndvolByVol() {
        return sndvolByVol;
    }

    public void setSndvolByVol(Sndvol sndvolByVol) {
        this.sndvolByVol = sndvolByVol;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "type", nullable = true, insertable = true, updatable = true)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "vol", nullable = false, insertable = true, updatable = true)
    public int getVol() {
        return vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sndfile sndfile = (Sndfile) o;

        if (id != sndfile.id) return false;
        if (vol != sndfile.vol) return false;
        if (loc != null ? !loc.equals(sndfile.loc) : sndfile.loc != null) return false;
        if (notes != null ? !notes.equals(sndfile.notes) : sndfile.notes != null) return false;
        if (title != null ? !title.equals(sndfile.title) : sndfile.title != null) return false;
        if (type != null ? !type.equals(sndfile.type) : sndfile.type != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + vol;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
