package ee.esutoniagodesu.domain.jmdict_en.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;


public final class EN_LsrcPK implements Serializable {

    private static final long serialVersionUID = -2374115835603708151L;
    private int entr;
    private short sens;
    private short lang;
    private String txt;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "lang", nullable = false, insertable = true, updatable = true)
    @Id
    public short getLang() {
        return lang;
    }

    public void setLang(short lang) {
        this.lang = lang;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public short getSens() {
        return sens;
    }

    public void setSens(short sens) {
        this.sens = sens;
    }

    @Column(name = "txt", nullable = false, insertable = true, updatable = true, length = 250)
    @Id
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_LsrcPK lsrcPK = (EN_LsrcPK) o;

        if (entr != lsrcPK.entr) return false;
        if (lang != lsrcPK.lang) return false;
        if (sens != lsrcPK.sens) return false;
        if (txt != null ? !txt.equals(lsrcPK.txt) : lsrcPK.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + (int) sens;
        result = 31 * result + (int) lang;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
