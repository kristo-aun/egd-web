package ee.esutoniagodesu.domain.jmen.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Immutable
@Table(name = "chr", schema = "jmen", catalog = "egd")
public final class EN_Chr implements Serializable {

    private static final long serialVersionUID = -7805714474582693424L;
    private int entr;
    private String chr;
    private Short bushu;
    private Short strokes;
    private Short freq;
    private Short grade;
    private Short jlpt;
    private String radname;
    private EN_Entr entrByEntr;
    private Collection<EN_Cinf> cinfsByEntr;

    @Basic
    @Column(name = "bushu", nullable = true, insertable = true, updatable = true)
    public Short getBushu() {
        return bushu;
    }

    public void setBushu(Short bushu) {
        this.bushu = bushu;
    }

    @Basic
    @Column(name = "chr", columnDefinition = "bpchar", nullable = false, insertable = true, updatable = true, length = 1)
    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    @OneToMany(mappedBy = "chrByEntr")
    public Collection<EN_Cinf> getCinfsByEntr() {
        return cinfsByEntr;
    }

    public void setCinfsByEntr(Collection<EN_Cinf> cinfsByEntr) {
        this.cinfsByEntr = cinfsByEntr;
    }

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @OneToOne
    @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "id", nullable = false)
    public EN_Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(EN_Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @Basic
    @Column(name = "freq", nullable = true, insertable = true, updatable = true)
    public Short getFreq() {
        return freq;
    }

    public void setFreq(Short freq) {
        this.freq = freq;
    }

    @Basic
    @Column(name = "grade", nullable = true, insertable = true, updatable = true)
    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "jlpt", nullable = true, insertable = true, updatable = true)
    public Short getJlpt() {
        return jlpt;
    }

    public void setJlpt(Short jlpt) {
        this.jlpt = jlpt;
    }

    @Basic
    @Column(name = "radname", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRadname() {
        return radname;
    }

    public void setRadname(String radname) {
        this.radname = radname;
    }

    @Basic
    @Column(name = "strokes", nullable = true, insertable = true, updatable = true)
    public Short getStrokes() {
        return strokes;
    }

    public void setStrokes(Short strokes) {
        this.strokes = strokes;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_Chr chr1 = (EN_Chr) o;

        if (entr != chr1.entr) return false;
        if (bushu != null ? !bushu.equals(chr1.bushu) : chr1.bushu != null) return false;
        if (chr != null ? !chr.equals(chr1.chr) : chr1.chr != null) return false;
        if (freq != null ? !freq.equals(chr1.freq) : chr1.freq != null) return false;
        if (grade != null ? !grade.equals(chr1.grade) : chr1.grade != null) return false;
        if (jlpt != null ? !jlpt.equals(chr1.jlpt) : chr1.jlpt != null) return false;
        if (radname != null ? !radname.equals(chr1.radname) : chr1.radname != null) return false;
        if (strokes != null ? !strokes.equals(chr1.strokes) : chr1.strokes != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (chr != null ? chr.hashCode() : 0);
        result = 31 * result + (bushu != null ? bushu.hashCode() : 0);
        result = 31 * result + (strokes != null ? strokes.hashCode() : 0);
        result = 31 * result + (freq != null ? freq.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (jlpt != null ? jlpt.hashCode() : 0);
        result = 31 * result + (radname != null ? radname.hashCode() : 0);
        return result;
    }
}
