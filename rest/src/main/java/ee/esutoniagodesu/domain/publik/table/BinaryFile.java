package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Table(name = "binary_file", schema = "public", catalog = "egd")
@Entity
public final class BinaryFile implements Serializable {

    private static final long serialVersionUID = 5361786839032652583L;

    private Integer id;
    private String fileName;
    private String mime;
    private byte[] binaryFile;
    private int cfReportType;

    @Column(name = "binary_file", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
    }

    @Column(name = "cf_report_type", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getCfReportType() {
        return cfReportType;
    }

    public void setCfReportType(int cfReportType) {
        this.cfReportType = cfReportType;
    }

    @Column(name = "file_name", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @SequenceGenerator(name = "seq", sequenceName = "public.bfile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "mime", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryFile that = (BinaryFile) o;

        if (cfReportType != that.cfReportType) return false;
        if (id != that.id) return false;
        if (!Arrays.equals(binaryFile, that.binaryFile)) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (mime != null ? !mime.equals(that.mime) : that.mime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (mime != null ? mime.hashCode() : 0);
        result = 31 * result + (binaryFile != null ? Arrays.hashCode(binaryFile) : 0);
        result = 31 * result + cfReportType;
        return result;
    }
}
