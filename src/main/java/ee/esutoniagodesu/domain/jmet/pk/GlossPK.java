package ee.esutoniagodesu.domain.jmet.pk;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public final class GlossPK implements Serializable {

    private static final long serialVersionUID = -1899841963977998825L;
    private int entr;
    private int sens;
    private int gloss;
    private int lang;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "gloss", nullable = false, insertable = true, updatable = true)
    @Id
    public int getGloss() {
        return gloss;
    }

    public void setGloss(int gloss) {
        this.gloss = gloss;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlossPK glossPK = (GlossPK) o;

        if (entr != glossPK.entr) return false;
        if (gloss != glossPK.gloss) return false;
        if (lang != glossPK.lang) return false;
        if (sens != glossPK.sens) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + sens;
        result = 31 * result + gloss;
        result = 31 * result + lang;
        return result;
    }
}
