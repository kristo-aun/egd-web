package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "item_cnts", schema = "jmen")
public final class EN_ItemCnts implements Serializable {

    private static final long serialVersionUID = -7564094684824848730L;
    private Integer id;
    private Long seq;
    private Long nrdng;
    private Long nkanj;
    private Long nsens;

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
    @Column(name = "nkanj", nullable = true, insertable = true, updatable = true)
    public Long getNkanj() {
        return nkanj;
    }

    public void setNkanj(Long nkanj) {
        this.nkanj = nkanj;
    }

    @Basic
    @Column(name = "nrdng", nullable = true, insertable = true, updatable = true)
    public Long getNrdng() {
        return nrdng;
    }

    public void setNrdng(Long nrdng) {
        this.nrdng = nrdng;
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
    @Column(name = "seq", nullable = true, insertable = true, updatable = true)
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_ItemCnts itemCnts = (EN_ItemCnts) o;

        if (id != null ? !id.equals(itemCnts.id) : itemCnts.id != null) return false;
        if (nkanj != null ? !nkanj.equals(itemCnts.nkanj) : itemCnts.nkanj != null) return false;
        if (nrdng != null ? !nrdng.equals(itemCnts.nrdng) : itemCnts.nrdng != null) return false;
        if (nsens != null ? !nsens.equals(itemCnts.nsens) : itemCnts.nsens != null) return false;
        if (seq != null ? !seq.equals(itemCnts.seq) : itemCnts.seq != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (nrdng != null ? nrdng.hashCode() : 0);
        result = 31 * result + (nkanj != null ? nkanj.hashCode() : 0);
        result = 31 * result + (nsens != null ? nsens.hashCode() : 0);
        return result;
    }
}
