package ee.esutoniagodesu.service;

import com.google.common.base.Optional;
import ee.esutoniagodesu.config.Constants;
import ee.esutoniagodesu.domain.SHAFile;
import ee.esutoniagodesu.repository.project.SHADBRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.JCString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class SHADBService {

    private static final Logger log = Logger.getLogger(SHADBService.class);

    private final SHADBRepository shadbRepository;
    private final String tempUploadFolder;

    @Autowired
    public SHADBService(@Qualifier("jdbcShadb") JdbcTemplate jdbcShadb, Environment env) {
        shadbRepository = new SHADBRepository(jdbcShadb);
        String path = env.getProperty("app.files.path");
        tempUploadFolder = path + "temp-uploads/";
    }

    public File get(String sha256sum) {
        return shadbRepository.findBySha256sum(sha256sum).getFile();
    }

    public String put(MultipartFile multipartFile) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        File tmp = new File(tempUploadFolder + UUID.randomUUID().toString());
        multipartFile.transferTo(tmp);

        SHAFile shafile = valueOf(tmp, originalName);

        return save(shafile).getSha256sum();
    }

    public String put(File file, String originalName) throws IOException {
        SHAFile shafile = valueOf(file, originalName);
        return save(shafile).getSha256sum();
    }

    public String put(SHAFile shafile) throws IOException {


        return save(shafile).getSha256sum();
    }

    private SHAFile save(SHAFile shafile) throws IOException {

        shadbRepository.save(shafile);

        return shafile;
    }

    public static SHAFile valueOf(File file, String originalName) throws IOException {

        SHAFile shafile = new SHAFile();

        shafile.setSha256sum(sha256sum(file));
        shafile.setLength(file.length());
        shafile.setMime(mimetype(file));
        shafile.setFile(file);
        shafile.setFileName(originalName);
        shafile.setFileExtension(JCString.getExtension(originalName));
        shafile.setCreatedBy(Optional.fromNullable(SecurityUtils.getUserUuid()).or(Constants.SYSTEM_ACCOUNT));
        shafile.setCreatedDate(ZonedDateTime.now());

        return shafile;
    }

    public static String mimetype(File file) throws IOException {
        Process p = Runtime.getRuntime().exec("file --mime-type " + file.getPath());
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        return stdInput.readLine().split(":")[1].trim();
    }

    public static String sha256sum(File file) throws IOException {
        String path = file.getAbsolutePath();
        log.debug("sha256sum: path=" + path);
        Process p = Runtime.getRuntime().exec("sha256sum " + path);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String output = stdInput.readLine();
        log.debug("sha256sum: output=" + output);
        return output.substring(0, 64);
    }

}
