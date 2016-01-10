package ee.esutoniagodesu.domain;

import java.io.File;
import java.io.Serializable;

public class SHAFile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1859141807212637423L;

    private String sha256sum;
    private long length;
    private String mime;
    private File file;
    private String fileName;
    private String fileExtension;

    public String getSha256sum() {
        return sha256sum;
    }

    public void setSha256sum(String sha256sum) {
        this.sha256sum = sha256sum;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
