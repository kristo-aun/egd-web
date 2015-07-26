package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "essum", schema = "jmet")
public final class Essum implements Serializable {

    private static final long serialVersionUID = 7676049241292329926L;
    private Integer id;
    private Long seq;
    private Integer src;
    private Integer stat;
    private Integer sens;
    private String rdng;
    private String kanj;
    private String gloss;
    private Long nsens;

    @Basic
    @Column(name = "gloss", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getKanj() {
        return kanj;
    }

    public void setKanj(String kanj) {
        this.kanj = kanj;
    }

    @Basic
    @Column(name = "nsens", nullable = true, insertable = true, updatable = true)
    public Long getNsens() {
        return nsens;
    }

    public void setNsens(Long nsens) {
        this.nsens = nsens;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRdng() {
        return rdng;
    }

    public void setRdng(String rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
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
    @Column(name = "src", nullable = true, insertable = true, updatable = true)
    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    @Basic
    @Column(name = "stat", nullable = true, insertable = true, updatable = true)
    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Essum essum = (Essum) o;

        if (gloss != null ? !gloss.equals(essum.gloss) : essum.gloss != null) return false;
        if (id != null ? !id.equals(essum.id) : essum.id != null) return false;
        if (kanj != null ? !kanj.equals(essum.kanj) : essum.kanj != null) return false;
        if (nsens != null ? !nsens.equals(essum.nsens) : essum.nsens != null) return false;
        if (rdng != null ? !rdng.equals(essum.rdng) : essum.rdng != null) return false;
        if (sens != null ? !sens.equals(essum.sens) : essum.sens != null) return false;
        if (seq != null ? !seq.equals(essum.seq) : essum.seq != null) return false;
        if (src != null ? !src.equals(essum.src) : essum.src != null) return false;
        if (stat != null ? !stat.equals(essum.stat) : essum.stat != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (gloss != null ? gloss.hashCode() : 0);
        result = 31 * result + (nsens != null ? nsens.hashCode() : 0);
        return result;
    }
}
