package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "mtm_jm_sens_jp_sentence", schema = "public", catalog = "egd")
@Entity
public class MtmJmSensJpSentence implements Serializable {

    private static final long serialVersionUID = 548311792671552112L;

    private Integer id;
    private int sens;
    private String rdng;
    private String kanj;
    private JpSentence jpSentence;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "jp_sentence_id", referencedColumnName = "id", nullable = false)
    public JpSentence getJpSentence() {
        return jpSentence;
    }

    public void setJpSentence(JpSentence jpSentence) {
        this.jpSentence = jpSentence;
    }

    @Column(name = "kanj", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getKanj() {
        return kanj;
    }

    public void setKanj(String kanj) {
        this.kanj = kanj;
    }

    @Column(name = "rdng", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getRdng() {
        return rdng;
    }

    public void setRdng(String rdng) {
        this.rdng = rdng;
    }

    @Column(name = "sens", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getSens() {
        return sens;
    }

    public void setSens(int sens) {
        this.sens = sens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MtmJmSensJpSentence that = (MtmJmSensJpSentence) o;

        if (id != that.id) return false;
        if (sens != that.sens) return false;
        if (kanj != null ? !kanj.equals(that.kanj) : that.kanj != null) return false;
        if (rdng != null ? !rdng.equals(that.rdng) : that.rdng != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + sens;
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        return result;
    }
}
