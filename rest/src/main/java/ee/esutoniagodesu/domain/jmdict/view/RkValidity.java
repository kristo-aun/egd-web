package ee.esutoniagodesu.domain.jmdict.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "rk_validity", schema = "jmdict", catalog = "egd")
public final class RkValidity implements Serializable {

    private static final long serialVersionUID = -7297229460436239343L;
    private Integer id;
    private Long seq;
    private Integer rdng;
    private String rtxt;
    private Integer kanj;
    private String ktxt;
    private String valid;

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
    @Column(name = "kanj", nullable = true, insertable = true, updatable = true)
    public Integer getKanj() {
        return kanj;
    }

    public void setKanj(Integer kanj) {
        this.kanj = kanj;
    }

    @Basic
    @Column(name = "ktxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getKtxt() {
        return ktxt;
    }

    public void setKtxt(String ktxt) {
        this.ktxt = ktxt;
    }

    @Basic
    @Column(name = "rdng", nullable = true, insertable = true, updatable = true)
    public Integer getRdng() {
        return rdng;
    }

    public void setRdng(Integer rdng) {
        this.rdng = rdng;
    }

    @Basic
    @Column(name = "rtxt", nullable = true, insertable = true, updatable = true, length = 2048)
    public String getRtxt() {
        return rtxt;
    }

    public void setRtxt(String rtxt) {
        this.rtxt = rtxt;
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
    @Column(name = "valid", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RkValidity that = (RkValidity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kanj != null ? !kanj.equals(that.kanj) : that.kanj != null) return false;
        if (ktxt != null ? !ktxt.equals(that.ktxt) : that.ktxt != null) return false;
        if (rdng != null ? !rdng.equals(that.rdng) : that.rdng != null) return false;
        if (rtxt != null ? !rtxt.equals(that.rtxt) : that.rtxt != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        if (valid != null ? !valid.equals(that.valid) : that.valid != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (rdng != null ? rdng.hashCode() : 0);
        result = 31 * result + (rtxt != null ? rtxt.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }
}
