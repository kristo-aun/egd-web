package ee.esutoniagodesu.domain.jmdict.table;

import ee.esutoniagodesu.domain.jmdict.pk.HistPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Immutable
@IdClass(HistPK.class)
@Table(name = "Hist", schema = "jmdict", catalog = "egd")
public final class Hist implements Serializable {

    private static final long serialVersionUID = 1202012374156801240L;
    private int entr;
    private int hist;
    private int stat;
    private boolean unap;
    private Timestamp dt;
    private String userid;
    private String name;
    private String email;
    private String diff;
    private String refs;
    private String notes;
    private Entr entrByEntr;
    private Kwstat kwstatByStat;

    @Basic
    @Column(name = "diff", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    @Basic
    @Column(name = "dt", nullable = false, insertable = true, updatable = true)
    public Timestamp getDt() {
        return dt;
    }

    public void setDt(Timestamp dt) {
        this.dt = dt;
    }

    @Basic
    @Column(name = "email", nullable = true, insertable = true, updatable = true, length = 120)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @Column(name = "entr", nullable = false, insertable = true, updatable = true)
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "entr", referencedColumnName = "id", nullable = false)
    public Entr getEntrByEntr() {
        return entrByEntr;
    }

    public void setEntrByEntr(Entr entrByEntr) {
        this.entrByEntr = entrByEntr;
    }

    @Id
    @Column(name = "hist", nullable = false, insertable = true, updatable = true)
    public int getHist() {
        return hist;
    }

    public void setHist(int hist) {
        this.hist = hist;
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "stat", referencedColumnName = "id", nullable = false)
    public Kwstat getKwstatByStat() {
        return kwstatByStat;
    }

    public void setKwstatByStat(Kwstat kwstatByStat) {
        this.kwstatByStat = kwstatByStat;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "notes", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "refs", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getRefs() {
        return refs;
    }

    public void setRefs(String refs) {
        this.refs = refs;
    }

    @Basic
    @Column(name = "stat", nullable = false, insertable = true, updatable = true)
    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    @Basic
    @Column(name = "userid", nullable = true, insertable = true, updatable = true, length = 20)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "unap", nullable = false, insertable = true, updatable = true)
    public boolean isUnap() {
        return unap;
    }

    public void setUnap(boolean unap) {
        this.unap = unap;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hist hist1 = (Hist) o;

        if (entr != hist1.entr) return false;
        if (hist != hist1.hist) return false;
        if (stat != hist1.stat) return false;
        if (unap != hist1.unap) return false;
        if (diff != null ? !diff.equals(hist1.diff) : hist1.diff != null) return false;
        if (dt != null ? !dt.equals(hist1.dt) : hist1.dt != null) return false;
        if (email != null ? !email.equals(hist1.email) : hist1.email != null) return false;
        if (name != null ? !name.equals(hist1.name) : hist1.name != null) return false;
        if (notes != null ? !notes.equals(hist1.notes) : hist1.notes != null) return false;
        if (refs != null ? !refs.equals(hist1.refs) : hist1.refs != null) return false;
        if (userid != null ? !userid.equals(hist1.userid) : hist1.userid != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr;
        result = 31 * result + hist;
        result = 31 * result + stat;
        result = 31 * result + (unap ? 1 : 0);
        result = 31 * result + (dt != null ? dt.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (diff != null ? diff.hashCode() : 0);
        result = 31 * result + (refs != null ? refs.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
