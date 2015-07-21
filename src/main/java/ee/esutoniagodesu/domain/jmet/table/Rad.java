package ee.esutoniagodesu.domain.jmet.table;

import ee.esutoniagodesu.domain.jmet.pk.RadPK;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Immutable
@Table(name = "rad", schema = "jmet")
@IdClass(RadPK.class)
public final class Rad implements Serializable {

    private static final long serialVersionUID = 5489798792299591183L;
    private short num;
    private short var;
    private String rchr;
    private String chr;
    private Short strokes;
    private String loc;
    private String name;
    private String examples;

    @Basic
    @Column(name = "chr", columnDefinition = "bpchar", nullable = true, insertable = true, updatable = true, length = 1)
    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    @Basic
    @Column(name = "examples", nullable = true, insertable = true, updatable = true, length = 20)
    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    @Basic
    @Column(name = "loc", columnDefinition = "bpchar", nullable = true, insertable = true, updatable = true, length = 1)
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "num", nullable = false, insertable = true, updatable = true)
    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    @Basic
    @Column(name = "rchr", columnDefinition = "bpchar", nullable = true, insertable = true, updatable = true, length = 1)
    public String getRchr() {
        return rchr;
    }

    public void setRchr(String rchr) {
        this.rchr = rchr;
    }

    @Basic
    @Column(name = "strokes", nullable = true, insertable = true, updatable = true)
    public Short getStrokes() {
        return strokes;
    }

    public void setStrokes(Short strokes) {
        this.strokes = strokes;
    }

    @Id
    @Column(name = "var", nullable = false, insertable = true, updatable = true)
    public short getVar() {
        return var;
    }

    public void setVar(short var) {
        this.var = var;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rad rad = (Rad) o;

        if (num != rad.num) return false;
        if (var != rad.var) return false;
        if (chr != null ? !chr.equals(rad.chr) : rad.chr != null) return false;
        if (examples != null ? !examples.equals(rad.examples) : rad.examples != null) return false;
        if (loc != null ? !loc.equals(rad.loc) : rad.loc != null) return false;
        if (name != null ? !name.equals(rad.name) : rad.name != null) return false;
        if (rchr != null ? !rchr.equals(rad.rchr) : rad.rchr != null) return false;
        if (strokes != null ? !strokes.equals(rad.strokes) : rad.strokes != null) return false;

        return true;
    }

    public int hashCode() {
        int result = (int) num;
        result = 31 * result + (int) var;
        result = 31 * result + (rchr != null ? rchr.hashCode() : 0);
        result = 31 * result + (chr != null ? chr.hashCode() : 0);
        result = 31 * result + (strokes != null ? strokes.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (examples != null ? examples.hashCode() : 0);
        return result;
    }
}
