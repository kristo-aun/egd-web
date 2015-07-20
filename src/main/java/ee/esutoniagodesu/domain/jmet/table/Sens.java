package ee.esutoniagodesu.domain.jmet.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.esutoniagodesu.domain.jmet.pk.SensPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "sens", schema = "jmet", catalog = "egd")
@IdClass(SensPK.class)
public final class Sens implements Serializable {

    private static final long serialVersionUID = 6286155174803964320L;

    @JsonIgnore
    private int entr;
    @JsonIgnore
    private short sens;
    @JsonIgnore
    private String notes;
    @JsonIgnore
    private Collection<Dial> dials;
    @JsonIgnore
    private Collection<Fld> flds;
    private Collection<Gloss> glosses;
    private Collection<Lsrc> lsrcs;
    private Collection<Misc> miscs;
    private Collection<Pos> poses;
    private Entr entrByEntr;
    private Collection<Stagk> stagks;
    private Collection<Stagr> stagrs;
    private Collection<Xref> xrefs;
    private Collection<Xref> xrefs_0;
    private Collection<Xresolv> xresolvs;

    @OneToMany(mappedBy = "sens_0")
    public Collection<Dial> getDials() {
        return dials;
    }

    public void setDials(Collection<Dial> dials) {
        this.dials = dials;
    }

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "id", nullable = false)
    public Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Fld> getFlds() {
        return flds;
    }

    public void setFlds(Collection<Fld> flds) {
        this.flds = flds;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Gloss> getGlosses() {
        return glosses;
    }

    public void setGlosses(Collection<Gloss> glosses) {
        this.glosses = glosses;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Lsrc> getLsrcs() {
        return lsrcs;
    }

    public void setLsrcs(Collection<Lsrc> lsrcs) {
        this.lsrcs = lsrcs;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Misc> getMiscs() {
        return miscs;
    }

    public void setMiscs(Collection<Misc> miscs) {
        this.miscs = miscs;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Pos> getPoses() {
        return poses;
    }

    public void setPoses(Collection<Pos> poses) {
        this.poses = poses;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Stagk> getStagks() {
        return stagks;
    }

    public void setStagks(Collection<Stagk> stagks) {
        this.stagks = stagks;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Stagr> getStagrs() {
        return stagrs;
    }

    public void setStagrs(Collection<Stagr> stagrs) {
        this.stagrs = stagrs;
    }

    @OneToMany(mappedBy = "sensByXrefEntrFkey")
    public Collection<Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<Xref> xrefs) {
        this.xrefs = xrefs;
    }

    @OneToMany(mappedBy = "sensByXrefXentrFkey")
    public Collection<Xref> getXrefs_0() {
        return xrefs_0;
    }

    public void setXrefs_0(Collection<Xref> xrefs_0) {
        this.xrefs_0 = xrefs_0;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<Xresolv> getXresolvs() {
        return xresolvs;
    }

    public void setXresolvs(Collection<Xresolv> xresolvs) {
        this.xresolvs = xresolvs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sens sens1 = (Sens) o;

        if (entr != sens1.entr) return false;
        if (sens != sens1.sens) return false;
        if (notes != null ? !notes.equals(sens1.notes) : sens1.notes != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
