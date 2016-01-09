package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.GlossPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "gloss", schema = "jmet")
@IdClass(GlossPK.class)
public final class Gloss implements Serializable {

    private static final long serialVersionUID = -326353107448740577L;
    private int entr;
    private short sens;
    private short gloss;
    private short lang;
    private short ginf;
    private String txt;
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

    @Basic
    @Column(name = "ginf", nullable = false, insertable = true, updatable = true)
    public short getGinf() {
        return ginf;
    }

    public void setGinf(short ginf) {
        this.ginf = ginf;
    }

    @Id
    @Column(name = "gloss", nullable = false, insertable = true, updatable = true)
    public short getGloss() {
        return gloss;
    }

    public void setGloss(short gloss) {
        this.gloss = gloss;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "lang", referencedColumnName = "id", nullable = false)
    public Kwlang getKwlangByLang() {
        return kwlangByLang;
    }

    public void setKwlangByLang(Kwlang kwlangByLang) {
        this.kwlangByLang = kwlangByLang;
    }

    @Basic
    @Column(name = "lang", nullable = false, insertable = true, updatable = true)
    public short getLang() {
        return lang;
    }

    public void setLang(short lang) {
        this.lang = lang;
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
    public Sens getSens_0() {
        return sens_0;
    }

    public void setSens_0(Sens sens_0) {
        this.sens_0 = sens_0;
    }

    @Basic
    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 2048)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gloss gloss1 = (Gloss) o;

        if (entr != gloss1.entr) return false;
        if (ginf != gloss1.ginf) return false;
        if (gloss != gloss1.gloss) return false;
        if (lang != gloss1.lang) return false;
        if (sens != gloss1.sens) return false;
        if (txt != null ? !txt.equals(gloss1.txt) : gloss1.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) gloss;
        result = 31 * result + (int) lang;
        result = 31 * result + (int) ginf;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }

    public String toString() {
        return txt;
    }
}
