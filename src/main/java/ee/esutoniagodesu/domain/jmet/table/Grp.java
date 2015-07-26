package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.GrpPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(GrpPK.class)
@Table(name = "Grp", schema = "jmet")
public final class Grp implements Serializable {

    private static final long serialVersionUID = -8743548017509629324L;
    private int entr;
    private int kw;
    private int ord;
    private String notes;
    private Entr entrByEntr;
    private Kwgrp kwgrpByKw;

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

    @Id
    @Column(name = "kw", nullable = false, insertable = true, updatable = true)
    public int getKw() {
        return kw;
    }

    public void setKw(int kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "kw", referencedColumnName = "id", nullable = false)
    public Kwgrp getKwgrpByKw() {
        return kwgrpByKw;
    }

    public void setKwgrpByKw(Kwgrp kwgrpByKw) {
        this.kwgrpByKw = kwgrpByKw;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 250)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grp grp = (Grp) o;

        if (entr != grp.entr) return false;
        if (kw != grp.kw) return false;
        if (ord != grp.ord) return false;
        if (notes != null ? !notes.equals(grp.notes) : grp.notes != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + kw;
        result = 31 * result + ord;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
