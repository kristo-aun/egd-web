package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "snd", schema = "jmen")
public final class EN_Snd implements Serializable {

    private static final long serialVersionUID = -5158298313845740389L;
    private int id;
    private short file;
    private int strt;
    private int leng;
    private String trns;
    private String notes;
    private Collection<EN_Entrsnd> entrsndsById;
    private Collection<EN_Rdngsnd> rdngsndsById;
    private EN_Sndfile sndfileByFile;

    @OneToMany(mappedBy = "sndBySnd")
    public Collection<EN_Entrsnd> getEntrsndsById() {
        return entrsndsById;
    }

    public void setEntrsndsById(Collection<EN_Entrsnd> entrsndsById) {
        this.entrsndsById = entrsndsById;
    }

    @Basic
    @Column(name = "file", nullable = false, insertable = true, updatable = true)
    public short getFile() {
        return file;
    }

    public void setFile(short file) {
        this.file = file;
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
    @Column(name = "leng", nullable = false, insertable = true, updatable = true)
    public int getLeng() {
        return leng;
    }

    public void setLeng(int leng) {
        this.leng = leng;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @OneToMany(mappedBy = "sndBySnd")
    public Collection<EN_Rdngsnd> getRdngsndsById() {
        return rdngsndsById;
    }

    public void setRdngsndsById(Collection<EN_Rdngsnd> rdngsndsById) {
        this.rdngsndsById = rdngsndsById;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "file", referencedColumnName = "id", nullable = false)
    public EN_Sndfile getSndfileByFile() {
        return sndfileByFile;
    }

    public void setSndfileByFile(EN_Sndfile sndfileByFile) {
        this.sndfileByFile = sndfileByFile;
    }

    @Basic
    @Column(name = "strt", nullable = false, insertable = true, updatable = true)
    public int getStrt() {
        return strt;
    }

    public void setStrt(int strt) {
        this.strt = strt;
    }

    @Basic
    @Column(name = "trns", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getTrns() {
        return trns;
    }

    public void setTrns(String trns) {
        this.trns = trns;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Snd snd = (EN_Snd) o;

        if (file != snd.file) return false;
        if (id != snd.id) return false;
        if (leng != snd.leng) return false;
        if (strt != snd.strt) return false;
        if (notes != null ? !notes.equals(snd.notes) : snd.notes != null) return false;
        if (trns != null ? !trns.equals(snd.trns) : snd.trns != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id;
        result = 31 * result + (int) file;
        result = 31 * result + strt;
        result = 31 * result + leng;
        result = 31 * result + (trns != null ? trns.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
