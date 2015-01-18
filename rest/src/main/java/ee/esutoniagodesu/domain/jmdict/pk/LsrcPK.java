package ee.esutoniagodesu.domain.jmdict.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class LsrcPK implements Serializable {

    private static final long serialVersionUID = -7374210104956088666L;
    private int entr;
    private int sens;
    private int lang;
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
    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true)
    @Id
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
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

        LsrcPK lsrcPK = (LsrcPK) o;

        if (entr != lsrcPK.entr) return false;
        if (lang != lsrcPK.lang) return false;
        if (sens != lsrcPK.sens) return false;
        if (txt != null ? !txt.equals(lsrcPK.txt) : lsrcPK.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + lang;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
