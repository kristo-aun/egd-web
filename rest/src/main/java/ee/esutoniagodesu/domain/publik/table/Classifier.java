package ee.esutoniagodesu.domain.publik.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "classifier", schema = "public", catalog = "egd")
public final class Classifier implements Serializable {

    private static final long serialVersionUID = -3630387828878054587L;

    private Integer id;
    private String title;
    private String descr;
    private String kommentaar;
    private int status;

    private Integer orderInLine;
    private Integer parentId;
    @JsonIgnore
    private Collection<CfAudioQuality> cfAudioQualities;
    @JsonIgnore
    private Collection<CfEtSonaliik> cfEtSonaliiks;
    @JsonIgnore
    private Collection<CfJpCategory> cfJpCategories;

    @JsonIgnore
    private Collection<CfOrigin> cfOrigins;
    @JsonIgnore
    private Collection<CfVocaTransQuality> cfVocaTransQualities;

    @OneToMany(mappedBy = "classifier")
    public Collection<CfAudioQuality> getCfAudioQualities() {
        return cfAudioQualities;
    }

    public void setCfAudioQualities(Collection<CfAudioQuality> cfAudioQualities) {
        this.cfAudioQualities = cfAudioQualities;
    }

    @OneToMany(mappedBy = "classifier")
    public Collection<CfEtSonaliik> getCfEtSonaliiks() {
        return cfEtSonaliiks;
    }

    public void setCfEtSonaliiks(Collection<CfEtSonaliik> cfEtSonaliiks) {
        this.cfEtSonaliiks = cfEtSonaliiks;
    }

    @OneToMany(mappedBy = "classifier")
    public Collection<CfJpCategory> getCfJpCategories() {
        return cfJpCategories;
    }

    public void setCfJpCategories(Collection<CfJpCategory> cfJpCategories) {
        this.cfJpCategories = cfJpCategories;
    }

    @OneToMany(mappedBy = "classifier")
    public Collection<CfOrigin> getCfOrigins() {
        return cfOrigins;
    }

    public void setCfOrigins(Collection<CfOrigin> cfOrigins) {
        this.cfOrigins = cfOrigins;
    }

    @OneToMany(mappedBy = "classifier")
    public Collection<CfVocaTransQuality> getCfVocaTransQualities() {
        return cfVocaTransQualities;
    }

    public void setCfVocaTransQualities(Collection<CfVocaTransQuality> cfVocaTransQualities) {
        this.cfVocaTransQualities = cfVocaTransQualities;
    }

    @Column(name = "descr", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @SequenceGenerator(name = "seq", sequenceName = "public.classifier_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "kommentaar", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getKommentaar() {
        return kommentaar;
    }

    public void setKommentaar(String kommentaar) {
        this.kommentaar = kommentaar;
    }

    @Column(name = "order_in_line", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getOrderInLine() {
        return orderInLine;
    }

    public void setOrderInLine(Integer orderInLine) {
        this.orderInLine = orderInLine;
    }

    @Column(name = "parent_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classifier that = (Classifier) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (descr != null ? !descr.equals(that.descr) : that.descr != null) return false;
        if (kommentaar != null ? !kommentaar.equals(that.kommentaar) : that.kommentaar != null) return false;
        if (orderInLine != null ? !orderInLine.equals(that.orderInLine) : that.orderInLine != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        result = 31 * result + (kommentaar != null ? kommentaar.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (orderInLine != null ? orderInLine.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
