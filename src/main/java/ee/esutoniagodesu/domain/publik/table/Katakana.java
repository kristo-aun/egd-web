package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "katakana", schema = "public", catalog = "egd")
public final class Katakana implements Serializable {

    private static final long serialVersionUID = 7812037269479270274L;

    private String kana;
    private String romaji;
    private Integer strokeCount;
    private Audio audio;
    private Image image;

    @Column(name = "kana", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    @Id
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @ManyToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    @ManyToOne
    @JoinColumn(name = "stroke_image_id", referencedColumnName = "id")
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Column(name = "romaji", nullable = false, insertable = true, updatable = true, length = 4, precision = 0)
    @Basic
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Column(name = "stroke_count", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(Integer strokeCount) {
        this.strokeCount = strokeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Katakana katakana = (Katakana) o;

        if (kana != null ? !kana.equals(katakana.kana) : katakana.kana != null) return false;
        if (romaji != null ? !romaji.equals(katakana.romaji) : katakana.romaji != null) return false;
        if (strokeCount != null ? !strokeCount.equals(katakana.strokeCount) : katakana.strokeCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kana != null ? kana.hashCode() : 0;
        result = 31 * result + (romaji != null ? romaji.hashCode() : 0);
        result = 31 * result + (strokeCount != null ? strokeCount.hashCode() : 0);
        return result;
    }
}
