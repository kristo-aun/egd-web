package ee.esutoniagodesu.domain.jmdict.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "sk_valid", schema = "jmdict", catalog = "egd")
public final class SkValid implements Serializable {

    private static final long serialVersionUID = 4032118723417474657L;
    private Integer entr;
    private Integer sens;
    private Integer kanj;
    private String ktxt;

    @Id
    @Basic
    @Column(name = "entr", nullable = true, insertable = true, updatable = true)
    public Integer getEntr() {
        return entr;
    }

    public void setEntr(Integer entr) {
        this.entr = entr;
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
    @Column(name = "sens", nullable = true, insertable = true, updatable = true)
    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkValid skValid = (SkValid) o;

        if (entr != null ? !entr.equals(skValid.entr) : skValid.entr != null) return false;
        if (kanj != null ? !kanj.equals(skValid.kanj) : skValid.kanj != null) return false;
        if (ktxt != null ? !ktxt.equals(skValid.ktxt) : skValid.ktxt != null) return false;
        if (sens != null ? !sens.equals(skValid.sens) : skValid.sens != null) return false;

        return true;
    }

    public int hashCode() {
        int result = entr != null ? entr.hashCode() : 0;
        result = 31 * result + (sens != null ? sens.hashCode() : 0);
        result = 31 * result + (kanj != null ? kanj.hashCode() : 0);
        result = 31 * result + (ktxt != null ? ktxt.hashCode() : 0);
        return result;
    }
}
