package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "kanji_meaning", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class KanjiMeaning implements Serializable {

    private static final long serialVersionUID = 8228404888965400722L;
    private Integer id;
    private String mLang;
    private String meaning;
    private Kanji kanji;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "meaning", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiMeaning that = (KanjiMeaning) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mLang != null ? !mLang.equals(that.mLang) : that.mLang != null) return false;
        if (meaning != null ? !meaning.equals(that.meaning) : that.meaning != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mLang != null ? mLang.hashCode() : 0);
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        return result;
    }

    @Column(name = "m_lang", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getmLang() {
        return mLang;
    }

    public void setmLang(String mLang) {
        this.mLang = mLang;
    }
}
