package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_ConjoPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "conjo", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_ConjoPK.class)
public final class EN_Conjo implements Serializable {

    private static final long serialVersionUID = 3993228012131845553L;
    private short pos;
    private short conj;
    private boolean neg;
    private boolean fml;
    private short onum;
    private Short stem;
    private String okuri;
    private String euphr;
    private String euphk;
    private Short pos2;
    private EN_Conj conjByConj;
    private EN_Kwpos kwposByPos2;
    private EN_Kwpos kwposByPos;
    private Collection<EN_ConjoNotes> conjoNoteses;

    @Id
    @Column(name = "conj", nullable = false, insertable = true, updatable = true)
    public short getConj() {
        return conj;
    }

    public void setConj(short conj) {
        this.conj = conj;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "conj", referencedColumnName = "id", nullable = false)
    public EN_Conj getConjByConj() {
        return conjByConj;
    }

    public void setConjByConj(EN_Conj conjByConj) {
        this.conjByConj = conjByConj;
    }

    @OneToMany(mappedBy = "conjo")
    public Collection<EN_ConjoNotes> getConjoNoteses() {
        return conjoNoteses;
    }

    public void setConjoNoteses(Collection<EN_ConjoNotes> conjoNoteses) {
        this.conjoNoteses = conjoNoteses;
    }

    @Basic
    @Column(name = "euphk", nullable = true, insertable = true, updatable = true, length = 50)
    public String getEuphk() {
        return euphk;
    }

    public void setEuphk(String euphk) {
        this.euphk = euphk;
    }

    @Basic
    @Column(name = "euphr", nullable = true, insertable = true, updatable = true, length = 50)
    public String getEuphr() {
        return euphr;
    }

    public void setEuphr(String euphr) {
        this.euphr = euphr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "pos", referencedColumnName = "id", nullable = false)
    public EN_Kwpos getKwposByPos() {
        return kwposByPos;
    }

    public void setKwposByPos(EN_Kwpos kwposByPos) {
        this.kwposByPos = kwposByPos;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "pos2", referencedColumnName = "id")
    public EN_Kwpos getKwposByPos2() {
        return kwposByPos2;
    }

    public void setKwposByPos2(EN_Kwpos kwposByPos2) {
        this.kwposByPos2 = kwposByPos2;
    }

    @Basic
    @Column(name = "okuri", nullable = false, insertable = true, updatable = true, length = 50)
    public String getOkuri() {
        return okuri;
    }

    public void setOkuri(String okuri) {
        this.okuri = okuri;
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

    @Basic
    @Column(name = "pos2", nullable = true, insertable = true, updatable = true)
    public Short getPos2() {
        return pos2;
    }

    public void setPos2(Short pos2) {
        this.pos2 = pos2;
    }

    @Basic
    @Column(name = "stem", nullable = true, insertable = true, updatable = true)
    public Short getStem() {
        return stem;
    }

    public void setStem(Short stem) {
        this.stem = stem;
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

        EN_Conjo conjo = (EN_Conjo) o;

        if (conj != conjo.conj) return false;
        if (fml != conjo.fml) return false;
        if (neg != conjo.neg) return false;
        if (onum != conjo.onum) return false;
        if (pos != conjo.pos) return false;
        if (euphk != null ? !euphk.equals(conjo.euphk) : conjo.euphk != null) return false;
        if (euphr != null ? !euphr.equals(conjo.euphr) : conjo.euphr != null) return false;
        if (okuri != null ? !okuri.equals(conjo.okuri) : conjo.okuri != null) return false;
        if (pos2 != null ? !pos2.equals(conjo.pos2) : conjo.pos2 != null) return false;
        if (stem != null ? !stem.equals(conjo.stem) : conjo.stem != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) pos;
        result = 31 * result + (int) conj;
        result = 31 * result + (neg ? 1 : 0);
        result = 31 * result + (fml ? 1 : 0);
        result = 31 * result + (int) onum;
        result = 31 * result + (stem != null ? stem.hashCode() : 0);
        result = 31 * result + (okuri != null ? okuri.hashCode() : 0);
        result = 31 * result + (euphr != null ? euphr.hashCode() : 0);
        result = 31 * result + (euphk != null ? euphk.hashCode() : 0);
        result = 31 * result + (pos2 != null ? pos2.hashCode() : 0);
        return result;
    }
}
