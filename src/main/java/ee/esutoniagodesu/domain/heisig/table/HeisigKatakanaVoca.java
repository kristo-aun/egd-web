package ee.esutoniagodesu.domain.heisig.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "heisig_katakana_voca", schema = "heisig", catalog = "egd")
@Entity
public final class HeisigKatakanaVoca implements Serializable {

    private static final long serialVersionUID = 7696325794722707887L;

    private Integer id;
    private String kanaInFocus;
    private String wordEn;
    private String wordEt;
    private String wordKana;
    private String wordRomaji;

    @SequenceGenerator(name = "seq", sequenceName = "heisig.heisig_katakana_voca_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "kana_in_focus", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public String getKanaInFocus() {
        return kanaInFocus;
    }

    public void setKanaInFocus(String kanaInFocus) {
        this.kanaInFocus = kanaInFocus;
    }

    @Column(name = "word_en", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getWordEn() {
        return wordEn;
    }

    public void setWordEn(String wordEn) {
        this.wordEn = wordEn;
    }

    @Column(name = "word_et", nullable = true, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getWordEt() {
        return wordEt;
    }

    public void setWordEt(String wordEt) {
        this.wordEt = wordEt;
    }

    @Column(name = "word_kana", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getWordKana() {
        return wordKana;
    }

    public void setWordKana(String wordKana) {
        this.wordKana = wordKana;
    }

    @Column(name = "word_romaji", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getWordRomaji() {
        return wordRomaji;
    }

    public void setWordRomaji(String wordRomaji) {
        this.wordRomaji = wordRomaji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeisigKatakanaVoca that = (HeisigKatakanaVoca) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kanaInFocus != null ? !kanaInFocus.equals(that.kanaInFocus) : that.kanaInFocus != null) return false;
        if (wordEn != null ? !wordEn.equals(that.wordEn) : that.wordEn != null) return false;
        if (wordEt != null ? !wordEt.equals(that.wordEt) : that.wordEt != null) return false;
        if (wordKana != null ? !wordKana.equals(that.wordKana) : that.wordKana != null) return false;
        if (wordRomaji != null ? !wordRomaji.equals(that.wordRomaji) : that.wordRomaji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kanaInFocus != null ? kanaInFocus.hashCode() : 0);
        result = 31 * result + (wordEn != null ? wordEn.hashCode() : 0);
        result = 31 * result + (wordEt != null ? wordEt.hashCode() : 0);
        result = 31 * result + (wordKana != null ? wordKana.hashCode() : 0);
        result = 31 * result + (wordRomaji != null ? wordRomaji.hashCode() : 0);
        return result;
    }
}
