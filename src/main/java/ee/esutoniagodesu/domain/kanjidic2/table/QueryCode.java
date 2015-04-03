package ee.esutoniagodesu.domain.kanjidic2.table;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "query_code", schema = "kanjidic2", catalog = "egd")
@Entity
@Immutable
public final class QueryCode implements Serializable {

    private static final long serialVersionUID = -6520904192106341654L;

    private Integer id;
    private String qCode;
    private String qcType;
    private Kanji kanji;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "kanji_id", referencedColumnName = "id", nullable = false)
    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    @Column(name = "qc_type", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getQcType() {
        return qcType;
    }

    public void setQcType(String qcType) {
        this.qcType = qcType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryCode queryCode = (QueryCode) o;

        if (id != null ? !id.equals(queryCode.id) : queryCode.id != null) return false;
        if (qCode != null ? !qCode.equals(queryCode.qCode) : queryCode.qCode != null) return false;
        if (qcType != null ? !qcType.equals(queryCode.qcType) : queryCode.qcType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (qCode != null ? qCode.hashCode() : 0);
        result = 31 * result + (qcType != null ? qcType.hashCode() : 0);
        return result;
    }

    @Column(name = "q_code", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getqCode() {
        return qCode;
    }

    public void setqCode(String qCode) {
        this.qCode = qCode;
    }
}
