package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_kanji_image", schema = "kanjidic2")
@Entity
@Immutable
public final class MtmKanjiImage implements Serializable {

    private static final long serialVersionUID = 8965647731999446040L;
    private Integer id;
    private int seq;
    private Kanji kanji;

    private String imageSha;

    @Column(name = "image_sha")
    public String getImageSha() {
        return imageSha;
    }

    public void setImageSha(String imageSha) {
        this.imageSha = imageSha;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "seq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmKanjiImage that = (MtmKanjiImage) o;

        if (seq != that.seq) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + seq;
        return result;
    }
}
