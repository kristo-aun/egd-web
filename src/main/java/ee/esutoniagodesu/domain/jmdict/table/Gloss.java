package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.GlossPK;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@IdClass(GlossPK.class)
@Table(name = "Gloss", schema = "jmdict", catalog = "egd")
public final class Gloss implements Serializable {

    private static final long serialVersionUID = -578353391829685811L;
    private int entr;
    private int sens;
    private int gloss;
    private int lang;
    private int ginf;
    private String txt;
    private Date inserted;
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
    public int getGinf() {
        return ginf;
    }

    public void setGinf(int ginf) {
        this.ginf = ginf;
    }

    @Id
    @Column(name = "gloss", nullable = false, insertable = true, updatable = true)
    public int getGloss() {
        return gloss;
    }

    public void setGloss(int gloss) {
        this.gloss = gloss;
    }

    @Basic
    @Column(name = "inserted", nullable = true, insertable = true, updatable = true)
    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
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
        if (inserted != null ? !inserted.equals(gloss1.inserted) : gloss1.inserted != null) return false;
        if (txt != null ? !txt.equals(gloss1.txt) : gloss1.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + gloss;
        result = 31 * result + lang;
        result = 31 * result + ginf;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (inserted != null ? inserted.hashCode() : 0);
        return result;
    }
}
