package ee.esutoniagodesu.domain.jmen.table;

import ee.esutoniagodesu.domain.jmen.pk.EN_SensPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "sens", schema = "jmen", catalog = "egd")
@IdClass(EN_SensPK.class)
public final class EN_Sens implements Serializable {

    private static final long serialVersionUID = 6286155174803964320L;
    private int entr;
    private short sens;
    private String notes;
    private Collection<EN_Dial> dials;
    private Collection<EN_Fld> flds;
    private Collection<EN_Gloss> glosses;
    private Collection<EN_Lsrc> lsrcs;
    private Collection<EN_Misc> miscs;
    private Collection<EN_Pos> poses;
    private EN_Entr entrByEntr;
    private Collection<EN_Stagk> stagks;
    private Collection<EN_Stagr> stagrs;
    private Collection<EN_Xref> xrefs;
    private Collection<EN_Xref> xrefs_0;
    private Collection<EN_Xresolv> xresolvs;

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Dial> getDials() {
        return dials;
    }

    public void setDials(Collection<EN_Dial> dials) {
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
    public EN_Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(EN_Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Fld> getFlds() {
        return flds;
    }

    public void setFlds(Collection<EN_Fld> flds) {
        this.flds = flds;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Gloss> getGlosses() {
        return glosses;
    }

    public void setGlosses(Collection<EN_Gloss> glosses) {
        this.glosses = glosses;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Lsrc> getLsrcs() {
        return lsrcs;
    }

    public void setLsrcs(Collection<EN_Lsrc> lsrcs) {
        this.lsrcs = lsrcs;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Misc> getMiscs() {
        return miscs;
    }

    public void setMiscs(Collection<EN_Misc> miscs) {
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
    public Collection<EN_Pos> getPoses() {
        return poses;
    }

    public void setPoses(Collection<EN_Pos> poses) {
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
    public Collection<EN_Stagk> getStagks() {
        return stagks;
    }

    public void setStagks(Collection<EN_Stagk> stagks) {
        this.stagks = stagks;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Stagr> getStagrs() {
        return stagrs;
    }

    public void setStagrs(Collection<EN_Stagr> stagrs) {
        this.stagrs = stagrs;
    }

    @OneToMany(mappedBy = "sensByXrefEntrFkey")
    public Collection<EN_Xref> getXrefs() {
        return xrefs;
    }

    public void setXrefs(Collection<EN_Xref> xrefs) {
        this.xrefs = xrefs;
    }

    @OneToMany(mappedBy = "sensByXrefXentrFkey")
    public Collection<EN_Xref> getXrefs_0() {
        return xrefs_0;
    }

    public void setXrefs_0(Collection<EN_Xref> xrefs_0) {
        this.xrefs_0 = xrefs_0;
    }

    @OneToMany(mappedBy = "sens_0")
    public Collection<EN_Xresolv> getXresolvs() {
        return xresolvs;
    }

    public void setXresolvs(Collection<EN_Xresolv> xresolvs) {
        this.xresolvs = xresolvs;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Sens sens1 = (EN_Sens) o;

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
