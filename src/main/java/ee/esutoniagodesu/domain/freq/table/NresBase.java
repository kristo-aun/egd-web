package ee.esutoniagodesu.domain.freq.table;

import ee.esutoniagodesu.pojo.dto.IntID;
import ee.esutoniagodesu.pojo.test.compound.ICountKanjis;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "jp_nres_base_aggr", schema = "freq", catalog = "egd")
@Entity
public final class NresBase implements IntID, ICountKanjis, Serializable {

    private static final long serialVersionUID = 5609869213032083552L;

    private int id;
    private String jp;
    private String types;
    private int freq;
    private Collection<MtmNresKanji> mtmNresKanjis;

    private int countGloss;
    private int countKanjis;

    @Transient
    public int getCountGloss() {
        return countGloss;
    }

    public void setCountGloss(int countGloss) {
        this.countGloss = countGloss;
    }

    @Transient
    public int getCountKanjis() {
        return countKanjis;
    }

    public void setCountKanjis(int countKanjis) {
        this.countKanjis = countKanjis;
    }

    @Column(name = "freq", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @SequenceGenerator(name = "seq", sequenceName = "freq.jp_freq_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "jp", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    @OneToMany(mappedBy = "nresBase")
    public Collection<MtmNresKanji> getMtmNresKanjis() {
        return mtmNresKanjis;
    }

    public void setMtmNresKanjis(Collection<MtmNresKanji> mtmNresKanjis) {
        this.mtmNresKanjis = mtmNresKanjis;
    }

    @Column(name = "types", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NresBase nresBase = (NresBase) o;

        if (freq != nresBase.freq) return false;
        if (id != nresBase.id) return false;
        if (jp != null ? !jp.equals(nresBase.jp) : nresBase.jp != null) return false;
        if (types != null ? !types.equals(nresBase.types) : nresBase.types != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jp != null ? jp.hashCode() : 0;
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + freq;
        result = 31 * result + id;
        return result;
    }
}
