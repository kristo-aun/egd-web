package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "kwsrc", schema = "jmet", catalog = "egd")
public final class Kwsrc implements Serializable {

    private static final long serialVersionUID = 3336223460442935287L;
    private short id;
    private String kw;
    private String descr;
    private Date dt;
    private String notes;
    private String seq;
    private Short sinc;
    private Long smin;
    private Long smax;
    private short srct;
    private Collection<Entr> entrsById;
    private Kwsrct kwsrctBySrct;
    private Collection<Sndvol> sndvolsById;

    @Basic
    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Basic
    @Column(name = "dt", nullable = true, insertable = true, updatable = true)
    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @OneToMany(mappedBy = "kwsrcBySrc")
    public Collection<Entr> getEntrsById() {
        return entrsById;
    }

    public void setEntrsById(Collection<Entr> entrsById) {
        this.entrsById = entrsById;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "kw", nullable = false, insertable = true, updatable = true, length = 20)
    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "srct", referencedColumnName = "id", nullable = false)
    public Kwsrct getKwsrctBySrct() {
        return kwsrctBySrct;
    }

    public void setKwsrctBySrct(Kwsrct kwsrctBySrct) {
        this.kwsrctBySrct = kwsrctBySrct;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "seq", nullable = false, insertable = true, updatable = true, length = 20)
    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "sinc", nullable = true, insertable = true, updatable = true)
    public Short getSinc() {
        return sinc;
    }

    public void setSinc(Short sinc) {
        this.sinc = sinc;
    }

    @Basic
    @Column(name = "smax", nullable = true, insertable = true, updatable = true)
    public Long getSmax() {
        return smax;
    }

    public void setSmax(Long smax) {
        this.smax = smax;
    }

    @Basic
    @Column(name = "smin", nullable = true, insertable = true, updatable = true)
    public Long getSmin() {
        return smin;
    }

    public void setSmin(Long smin) {
        this.smin = smin;
    }

    @OneToMany(mappedBy = "kwsrcByCorp")
    public Collection<Sndvol> getSndvolsById() {
        return sndvolsById;
    }

    public void setSndvolsById(Collection<Sndvol> sndvolsById) {
        this.sndvolsById = sndvolsById;
    }

    @Basic
    @Column(name = "srct", nullable = false, insertable = true, updatable = true)
    public short getSrct() {
        return srct;
    }

    public void setSrct(short srct) {
        this.srct = srct;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kwsrc kwsrc = (Kwsrc) o;

        if (id != kwsrc.id) return false;
        if (srct != kwsrc.srct) return false;
        if (descr != null ? !descr.equals(kwsrc.descr) : kwsrc.descr != null) return false;
        if (dt != null ? !dt.equals(kwsrc.dt) : kwsrc.dt != null) return false;
        if (kw != null ? !kw.equals(kwsrc.kw) : kwsrc.kw != null) return false;
        if (notes != null ? !notes.equals(kwsrc.notes) : kwsrc.notes != null) return false;
        if (seq != null ? !seq.equals(kwsrc.seq) : kwsrc.seq != null) return false;
        if (sinc != null ? !sinc.equals(kwsrc.sinc) : kwsrc.sinc != null) return false;
        if (smax != null ? !smax.equals(kwsrc.smax) : kwsrc.smax != null) return false;
        if (smin != null ? !smin.equals(kwsrc.smin) : kwsrc.smin != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (kw != null ? kw.hashCode() : 0);
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        result = 31 * result + (dt != null ? dt.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (sinc != null ? sinc.hashCode() : 0);
        result = 31 * result + (smin != null ? smin.hashCode() : 0);
        result = 31 * result + (smax != null ? smax.hashCode() : 0);
        result = 31 * result + (int) srct;
        return result;
    }
}
