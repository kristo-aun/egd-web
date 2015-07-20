package ee.esutoniagodesu.domain.jmen.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "sk_valid", schema = "jmen", catalog = "egd")
public final class EN_SkValid implements Serializable {

    private static final long serialVersionUID = -8919521483451248672L;
    private Integer entr;
    private Short sens;
    private Short kanj;
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
    public Short getKanj() {
        return kanj;
    }

    public void setKanj(Short kanj) {
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
    public Short getSens() {
        return sens;
    }

    public void setSens(Short sens) {
        this.sens = sens;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EN_SkValid skValid = (EN_SkValid) o;

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
