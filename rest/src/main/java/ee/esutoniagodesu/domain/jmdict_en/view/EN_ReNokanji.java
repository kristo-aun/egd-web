package ee.esutoniagodesu.domain.jmdict_en.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "re_nokanji", schema = "jmdict_en", catalog = "egd")
public final class EN_ReNokanji implements Serializable {

    private static final long serialVersionUID = -4670563169176292653L;
    private Integer id;
    private Long seq;
    private Short rdng;
    private String txt;

    @Id
    @Basic
    @Column(name = "id", nullable = true, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Short getRdng() {
        return rdng;
    }

    public void setRdng(Short rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "seq", nullable = true, insertable = true, updatable = true)
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "txt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_ReNokanji reNokanji = (EN_ReNokanji) o;

        if (id != null ? !id.equals(reNokanji.id) : reNokanji.id != null) return false;
        if (rdng != null ? !rdng.equals(reNokanji.rdng) : reNokanji.rdng != null) return false;
        if (seq != null ? !seq.equals(reNokanji.seq) : reNokanji.seq != null) return false;
        if (txt != null ? !txt.equals(reNokanji.txt) : reNokanji.txt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        return result;
    }
}
