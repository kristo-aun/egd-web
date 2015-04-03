package ee.esutoniagodesu.domain.jmdict.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "Sndvol", schema = "jmdict", catalog = "egd")
public final class Sndvol implements Serializable {

    private static final long serialVersionUID = 7802504414046282941L;
    private int id;
    private String title;
    private String loc;
    private int type;
    private String idstr;
    private Integer corp;
    private String notes;
    private Collection<Sndfile> sndfilesById;
    private Kwsrc kwsrcByCorp;

    @Basic
    @Column(name = "corp", nullable = true, insertable = true, updatable = true)
    public Integer getCorp() {
        return corp;
    }

    public void setCorp(Integer corp) {
        this.corp = corp;
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
    @Column(name = "idstr", nullable = true, insertable = true, updatable = true, length = 100)
    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "corp", referencedColumnName = "id")
    public Kwsrc getKwsrcByCorp() {
        return kwsrcByCorp;
    }

    public void setKwsrcByCorp(Kwsrc kwsrcByCorp) {
        this.kwsrcByCorp = kwsrcByCorp;
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

    @OneToMany(mappedBy = "sndvolByVol")
    public Collection<Sndfile> getSndfilesById() {
        return sndfilesById;
    }

    public void setSndfilesById(Collection<Sndfile> sndfilesById) {
        this.sndfilesById = sndfilesById;
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
    @Column(name = "type", nullable = false, insertable = true, updatable = true)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sndvol sndvol = (Sndvol) o;

        if (id != sndvol.id) return false;
        if (type != sndvol.type) return false;
        if (corp != null ? !corp.equals(sndvol.corp) : sndvol.corp != null) return false;
        if (idstr != null ? !idstr.equals(sndvol.idstr) : sndvol.idstr != null) return false;
        if (loc != null ? !loc.equals(sndvol.loc) : sndvol.loc != null) return false;
        if (notes != null ? !notes.equals(sndvol.notes) : sndvol.notes != null) return false;
        if (title != null ? !title.equals(sndvol.title) : sndvol.title != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (idstr != null ? idstr.hashCode() : 0);
        result = 31 * result + (corp != null ? corp.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
