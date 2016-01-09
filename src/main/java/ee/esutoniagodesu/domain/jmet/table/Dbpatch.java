package ee.esutoniagodesu.domain.jmet.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Immutable
@Table(name = "dbpatch", schema = "jmet")
public final class Dbpatch implements Serializable {

    private static final long serialVersionUID = 4358484042908856282L;
    private int level;
    private Timestamp dt;

    @Basic
    @Column(name = "dt", nullable = true, insertable = true, updatable = true)
    public Timestamp getDt() {
        return dt;
    }

    public void setDt(Timestamp dt) {
        this.dt = dt;
    }

    @Id
    @Column(name = "level", nullable = false, insertable = true, updatable = true)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dbpatch dbpatch = (Dbpatch) o;

        if (level != dbpatch.level) return false;
        if (dt != null ? !dt.equals(dbpatch.dt) : dbpatch.dt != null) return false;

        return true;
    }

    public int hashCode() {
        int result = level;
        result = 31 * result + (dt != null ? dt.hashCode() : 0);
        return result;
    }
}
