package ee.esutoniagodesu.domain.jmdict_en.table;

import ee.esutoniagodesu.domain.jmdict_en.pk.EN_LsrcPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "lsrc", schema = "jmdict_en", catalog = "egd")
@IdClass(EN_LsrcPK.class)
public final class EN_Lsrc implements Serializable {

    private static final long serialVersionUID = -3789853165764046294L;
    private int entr;
    private short sens;
    private short ord;
    private short lang;
    private String txt;
    private Boolean part;
    private Boolean wasei;
    private EN_Kwlang kwlangByLang;
    private EN_Sens sens_0;

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
    public EN_Kwlang getKwlangByLang() {
        return kwlangByLang;
    }

    public void setKwlangByLang(EN_Kwlang kwlangByLang) {
        this.kwlangByLang = kwlangByLang;
    }

    @Id
    @Column(name = "lang", nullable = false, insertable = true, updatable = true)
    public short getLang() {
        return lang;
    }

    public void setLang(short lang) {
        this.lang = lang;
    }

    @Basic
    @Column(name = "ord", nullable = false, insertable = true, updatable = true)
    public short getOrd() {
        return ord;
    }

    public void setOrd(short ord) {
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
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "entr", nullable = false), @JoinColumn(insertable = false, updatable = false, name = "sens", referencedColumnName = "sens", nullable = false)})
    public EN_Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(EN_Sens sens_0) {
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

        EN_Lsrc lsrc = (EN_Lsrc) o;

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
        result = 31 * result + (int) sens;
        result = 31 * result + (int) ord;
        result = 31 * result + (int) lang;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (part != null ? part.hashCode() : 0);
        result = 31 * result + (wasei != null ? wasei.hashCode() : 0);
        return result;
    }
}
