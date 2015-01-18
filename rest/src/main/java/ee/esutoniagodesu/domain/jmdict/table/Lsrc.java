package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.LsrcPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@IdClass(LsrcPK.class)
@Table(name = "Lsrc", schema = "jmdict", catalog = "egd")
public final class Lsrc implements Serializable {

    private static final long serialVersionUID = -660340825614251192L;
    private int entr;
    private int sens;
    private int ord;
    private int lang;
    private String txt;
    private Boolean part;
    private Boolean wasei;
    private Kwlang kwlangByLang;
    private Sens sens_0;

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "lang", referencedColumnName = "id", nullable = false)
    public Kwlang getKwlangByLang() {
        return kwlangByLang;
    }

    public void setKwlangByLang(Kwlang kwlangByLang) {
        this.kwlangByLang = kwlangByLang;
    }

    @Id
    @Column(name = "lang", nullable = false, insertable = true, updatable = true)
    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    @Basic
    @Column(name = "part", nullable = true, insertable = true, updatable = true)
    public Boolean getPart() {
        return part;
    }

    public void setPart(Boolean part) {
        this.part = part;
    }

    @Id
    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(Sens sens_0) {
        this.sens_0 = sens_0;
    }

    @Id
    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 250)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Basic
    @Column(name = "wasei", nullable = true, insertable = true, updatable = true)
    public Boolean getWasei() {
        return wasei;
    }

    public void setWasei(Boolean wasei) {
        this.wasei = wasei;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsrc lsrc = (Lsrc) o;

        if (entr != lsrc.entr) return false;
        if (lang != lsrc.lang) return false;
        if (ord != lsrc.ord) return false;
        if (sens != lsrc.sens) return false;
        if (part != null ? !part.equals(lsrc.part) : lsrc.part != null) return false;
        if (txt != null ? !txt.equals(lsrc.txt) : lsrc.txt != null) return false;
        if (wasei != null ? !wasei.equals(lsrc.wasei) : lsrc.wasei != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + ord;
        result = 31 * result + lang;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (part != null ? part.hashCode() : 0);
        result = 31 * result + (wasei != null ? wasei.hashCode() : 0);
        return result;
    }
}
