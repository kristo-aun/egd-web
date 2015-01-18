package ee.esutoniagodesu.domain.estwn.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "variant", schema = "estwn", catalog = "egd")
public final class Variant implements Serializable {

    private static final long serialVersionUID = -6949963501599292207L;
    private Integer id;
    private String definition;
    private String literal;
    @JsonIgnore
    private Collection<Example> examples;
    private WordMeaning wordMeaning;

    @SequenceGenerator(name = "seq", sequenceName = "estwn.variant_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "definition", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @OneToMany(mappedBy = "variant")
    public Collection<Example> getExamples() {
        return examples;
    }

    public void setExamples(Collection<Example> examples) {
        this.examples = examples;
    }

    @Basic
    @Column(name = "literal", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @ManyToOne
    @JoinColumn(name = "word_meaning_id", referencedColumnName = "id", nullable = false)
    public WordMeaning getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(WordMeaning wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variant variant = (Variant) o;

        if (id != variant.id) return false;
        if (definition != null ? !definition.equals(variant.definition) : variant.definition != null) return false;
        if (literal != null ? !literal.equals(variant.literal) : variant.literal != null) return false;

        return true;
    }

    public int hashCode() {
        int result = definition != null ? definition.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (literal != null ? literal.hashCode() : 0);
        return result;
    }
}
