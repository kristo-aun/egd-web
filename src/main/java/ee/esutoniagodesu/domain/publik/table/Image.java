package ee.esutoniagodesu.domain.publik.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "image", schema = "public", catalog = "egd")
public final class Image implements Serializable {

    private static final long serialVersionUID = -905164597956306494L;

    private Integer id;
    private byte[] imageFile;
    private String fileName;
    private String copyright;
    private String mimeType;

    @Column(name = "copyright", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Column(name = "file_name", nullable = false, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "image_file", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    public byte[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    @Column(name = "mime_type", nullable = true, insertable = true, updatable = true, length = 2044, precision = 0)
    @Basic
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != image.id) return false;
        if (copyright != null ? !copyright.equals(image.copyright) : image.copyright != null) return false;
        if (fileName != null ? !fileName.equals(image.fileName) : image.fileName != null) return false;
        if (!Arrays.equals(imageFile, image.imageFile)) return false;
        if (mimeType != null ? !mimeType.equals(image.mimeType) : image.mimeType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imageFile != null ? Arrays.hashCode(imageFile) : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (copyright != null ? copyright.hashCode() : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        return result;
    }
}
