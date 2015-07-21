package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Table(name = "jp_search_hist", schema = "public")
@Entity
public final class JpSearchHist implements Serializable {

    private static final long serialVersionUID = 7342143114053439924L;

    private Integer id;
    private String sString;
    private Time searched;
    private int resultSize;
    private String lang;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "lang", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    @Basic
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column(name = "result_size", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    @Column(name = "searched", nullable = false, insertable = true, updatable = true, length = 15, precision = 6)
    @Basic
    public Time getSearched() {
        return searched;
    }

    public void setSearched(Time searched) {
        this.searched = searched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpSearchHist that = (JpSearchHist) o;

        if (id != that.id) return false;
        if (resultSize != that.resultSize) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (sString != null ? !sString.equals(that.sString) : that.sString != null) return false;
        if (searched != null ? !searched.equals(that.searched) : that.searched != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sString != null ? sString.hashCode() : 0);
        result = 31 * result + (searched != null ? searched.hashCode() : 0);
        result = 31 * result + resultSize;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }

    @Column(name = "s_string", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getsString() {
        return sString;
    }

    public void setsString(String sString) {
        this.sString = sString;
    }
}
