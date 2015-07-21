package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.ConjoNotesPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "conjo_notes", schema = "jmet")
@IdClass(ConjoNotesPK.class)
public final class ConjoNotes implements Serializable {

    private static final long serialVersionUID = -7232828608421909188L;
    private short pos;
    private short conj;
    private boolean neg;
    private boolean fml;
    private short onum;
    private short note;
    private Conjo conjo;
    private Conotes conotesByNote;

    @Id
    @Column(name = "conj", nullable = false, insertable = true, updatable = true)
    public short getConj() {
        return conj;
    }

    public void setConj(short conj) {
        this.conj = conj;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "pos", referencedColumnName = "pos", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "conj", referencedColumnName = "conj", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "neg", referencedColumnName = "neg", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "fml", referencedColumnName = "fml", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "onum", referencedColumnName = "onum", nullable = false)})
    public Conjo getConjo() {
        return conjo;
    }

    public void setConjo(Conjo conjo) {
        this.conjo = conjo;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "note", referencedColumnName = "id", nullable = false)
    public Conotes getConotesByNote() {
        return conotesByNote;
    }

    public void setConotesByNote(Conotes conotesByNote) {
        this.conotesByNote = conotesByNote;
    }

    @Id
    @Column(name = "note", nullable = false, insertable = true, updatable = true)
    public short getNote() {
        return note;
    }

    public void setNote(short note) {
        this.note = note;
    }

    @Id
    @Column(name = "onum", nullable = false, insertable = true, updatable = true)
    public short getOnum() {
        return onum;
    }

    public void setOnum(short onum) {
        this.onum = onum;
    }

    @Id
    @Column(name = "pos", nullable = false, insertable = true, updatable = true)
    public short getPos() {
        return pos;
    }

    public void setPos(short pos) {
        this.pos = pos;
    }

    @Id
    @Column(name = "fml", nullable = false, insertable = true, updatable = true)
    public boolean isFml() {
        return fml;
    }

    public void setFml(boolean fml) {
        this.fml = fml;
    }

    @Id
    @Column(name = "neg", nullable = false, insertable = true, updatable = true)
    public boolean isNeg() {
        return neg;
    }

    public void setNeg(boolean neg) {
        this.neg = neg;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConjoNotes that = (ConjoNotes) o;

        if (conj != that.conj) return false;
        if (fml != that.fml) return false;
        if (neg != that.neg) return false;
        if (note != that.note) return false;
        if (onum != that.onum) return false;
        if (pos != that.pos) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) pos;
        result = 31 * result + (int) conj;
        result = 31 * result + (neg ? 1 : 0);
        result = 31 * result + (fml ? 1 : 0);
        result = 31 * result + (int) onum;
        result = 31 * result + (int) note;
        return result;
    }
}
