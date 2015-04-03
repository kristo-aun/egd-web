package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Table(name = "jm_entr_jatik", schema = "public", catalog = "egd")
@Entity
public final class JmEntrJatik implements Serializable {

    private static final long serialVersionUID = 1024055437180433544L;

    private int entr;
    private String jatikId;
    private Time inserted;

    @Column(name = "entr", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getEntr() {
        return entr;
    }

    public void setEntr(int entr) {
        this.entr = entr;
    }

    @Column(name = "inserted", nullable = false, insertable = true, updatable = true, length = 15, precision = 6)
    @Basic
    public Time getInserted() {
        return inserted;
    }

    public void setInserted(Time inserted) {
        this.inserted = inserted;
    }

    @Column(name = "jatik_id", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getJatikId() {
        return jatikId;
    }

    public void setJatikId(String jatikId) {
        this.jatikId = jatikId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmEntrJatik that = (JmEntrJatik) o;

        if (entr != that.entr) return false;
        if (inserted != null ? !inserted.equals(that.inserted) : that.inserted != null) return false;
        if (jatikId != null ? !jatikId.equals(that.jatikId) : that.jatikId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entr;
        result = 31 * result + (jatikId != null ? jatikId.hashCode() : 0);
        result = 31 * result + (inserted != null ? inserted.hashCode() : 0);
        return result;
    }
}
