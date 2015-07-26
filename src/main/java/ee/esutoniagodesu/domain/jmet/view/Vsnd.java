package ee.esutoniagodesu.domain.jmet.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "vsnd", schema = "jmet")
public final class Vsnd implements Serializable {

    private static final long serialVersionUID = 8017730638192293071L;

    private Integer id;
    private Integer strt;
    private Integer leng;
    private String sfile;
    private String sdir;
    private Boolean iscd;
    private Integer sdid;
    private String trns;

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
    @Column(name = "iscd", nullable = true, insertable = true, updatable = true)
    public Boolean getIscd() {
        return iscd;
    }

    public void setIscd(Boolean iscd) {
        this.iscd = iscd;
    }

    @Basic
    @Column(name = "leng", nullable = true, insertable = true, updatable = true)
    public Integer getLeng() {
        return leng;
    }

    public void setLeng(Integer leng) {
        this.leng = leng;
    }

    @Basic
    @Column(name = "sdid", nullable = true, insertable = true, updatable = true)
    public Integer getSdid() {
        return sdid;
    }

    public void setSdid(Integer sdid) {
        this.sdid = sdid;
    }

    @Basic
    @Column(name = "sdir", nullable = true, insertable = true, updatable = true, length = 500)
    public String getSdir() {
        return sdir;
    }

    public void setSdir(String sdir) {
        this.sdir = sdir;
    }

    @Basic
    @Column(name = "sfile", nullable = true, insertable = true, updatable = true, length = 500)
    public String getSfile() {
        return sfile;
    }

    public void setSfile(String sfile) {
        this.sfile = sfile;
    }

    @Basic
    @Column(name = "strt", nullable = true, insertable = true, updatable = true)
    public Integer getStrt() {
        return strt;
    }

    public void setStrt(Integer strt) {
        this.strt = strt;
    }

    @Basic
    @Column(name = "trns", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getTrns() {
        return trns;
    }

    public void setTrns(String trns) {
        this.trns = trns;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vsnd vsnd = (Vsnd) o;

        if (id != null ? !id.equals(vsnd.id) : vsnd.id != null) return false;
        if (iscd != null ? !iscd.equals(vsnd.iscd) : vsnd.iscd != null) return false;
        if (leng != null ? !leng.equals(vsnd.leng) : vsnd.leng != null) return false;
        if (sdid != null ? !sdid.equals(vsnd.sdid) : vsnd.sdid != null) return false;
        if (sdir != null ? !sdir.equals(vsnd.sdir) : vsnd.sdir != null) return false;
        if (sfile != null ? !sfile.equals(vsnd.sfile) : vsnd.sfile != null) return false;
        if (strt != null ? !strt.equals(vsnd.strt) : vsnd.strt != null) return false;
        if (trns != null ? !trns.equals(vsnd.trns) : vsnd.trns != null) return false;

        return true;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (strt != null ? strt.hashCode() : 0);
        result = 31 * result + (leng != null ? leng.hashCode() : 0);
        result = 31 * result + (sfile != null ? sfile.hashCode() : 0);
        result = 31 * result + (sdir != null ? sdir.hashCode() : 0);
        result = 31 * result + (iscd != null ? iscd.hashCode() : 0);
        result = 31 * result + (sdid != null ? sdid.hashCode() : 0);
        result = 31 * result + (trns != null ? trns.hashCode() : 0);
        return result;
    }
}
