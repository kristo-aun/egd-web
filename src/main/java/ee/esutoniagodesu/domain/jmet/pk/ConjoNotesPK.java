package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class ConjoNotesPK implements Serializable {

    private static final long serialVersionUID = -3767015313213458343L;
    private short pos;
    private short conj;
    private boolean neg;
    private boolean fml;
    private short onum;
    private short note;

    @Column(name = "conj", nullable = false, insertable = true, updatable = true)
    @Id
    public short getConj() {
        return conj;
    }

    public void setConj(short conj) {
        this.conj = conj;
    }

    @Column(name = "note", nullable = false, insertable = true, updatable = true)
    @Id
    public short getNote() {
        return note;
    }

    public void setNote(short note) {
        this.note = note;
    }

    @Column(name = "onum", nullable = false, insertable = true, updatable = true)
    @Id
    public short getOnum() {
        return onum;
    }

    public void setOnum(short onum) {
        this.onum = onum;
    }

    @Column(name = "pos", nullable = false, insertable = true, updatable = true)
    @Id
    public short getPos() {
        return pos;
    }

    public void setPos(short pos) {
        this.pos = pos;
    }

    @Column(name = "fml", nullable = false, insertable = true, updatable = true)
    @Id
    public boolean isFml() {
        return fml;
    }

    public void setFml(boolean fml) {
        this.fml = fml;
    }

    @Column(name = "neg", nullable = false, insertable = true, updatable = true)
    @Id
    public boolean isNeg() {
        return neg;
    }

    public void setNeg(boolean neg) {
        this.neg = neg;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConjoNotesPK that = (ConjoNotesPK) o;

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
