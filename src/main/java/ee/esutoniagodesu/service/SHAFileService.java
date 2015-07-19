package ee.esutoniagodesu.service;

import com.google.common.base.Joiner;
import ee.esutoniagodesu.domain.ac.table.Authority;
import ee.esutoniagodesu.security.permission.CustomPermissionEvaluator;
import ee.esutoniagodesu.security.permission.Permission;
import ee.esutoniagodesu.util.JCString;
import org.apache.log4j.Logger;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Does not move files across different mount points.
 * Must have write rights to destination folder.
 */
@Service
public class SHAFileService implements EnvironmentAware {

    private static final Logger log = Logger.getLogger(SHAFileService.class);

    private static String filesPath;
    private static String deletedPath;
    private static String tempUploadFolder;
    private static final String _PROP_SUFFIX = ".properties";

    @Override
    public void setEnvironment(Environment env) {
        filesPath = env.getProperty("app.files.path") + "main/";
        tempUploadFolder = filesPath + "temp-uploads/";
        deletedPath = filesPath + "deleted/";
        Assert.notNull(filesPath);
    }

    @Inject
    private CustomPermissionEvaluator pe;

    public Map.Entry<Properties, File> authorizedGetWithProperties(String sha256sum) throws IOException {
        Map.Entry<Properties, File> entry = getWithProperties(sha256sum);
        pe.check(entry.getKey(), Permission.shafile_read);
        return entry;
    }

    public Map.Entry<Properties, File> getWithProperties(String sha256sum) throws IOException {
        Properties properties = getProperties(sha256sum);
        return new AbstractMap.SimpleImmutableEntry<>(properties, get(sha256sum));
    }

    public Properties getProperties(String sha256sum) throws IOException {
        String hexpath = hexpath(sha256sum);
        String path = filesPath + hexpath + sha256sum + _PROP_SUFFIX;
        Properties prop = new Properties();
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }
        prop.load(new FileInputStream(path));
        return prop;
    }

    public File authorizedGet(String sha256sum) throws IOException {
        Map.Entry<Properties, File> entry = getWithProperties(sha256sum);
        pe.check(entry.getKey(), Permission.shafile_read);
        return entry.getValue();
    }

    public File get(String sha256sum) {
        String hexpath = hexpath(sha256sum);
        return new File(filesPath + hexpath + sha256sum);
    }

    public String put(MultipartFile multipartFile, Authority... authorities) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        File tmp = new File(tempUploadFolder + UUID.randomUUID().toString());
        multipartFile.transferTo(tmp);
        Properties properties = metadata(tmp);
        properties.put("orig-name", originalName);
        properties.put("orig-extension", JCString.getExtension(originalName));
        return put(tmp, properties, authorities);
    }

    public String put(File file, Authority... authorities) throws IOException {
        Properties properties = metadata(file);
        return put(file, properties, authorities);
    }

    private String put(File file, Properties properties, Authority... authorities) throws IOException {
        if (authorities != null && authorities.length > 0)
            properties.put("roles-allowed", Joiner.on(",").join(authorities));

        return put(file, properties);
    }

    private String put(File tmp, Properties properties) throws IOException {
        File propertiesFile = writeProperties(properties);

        String sha256sum = properties.getProperty("sha256sum");
        String subdir = hexpath(sha256sum);

        new File(filesPath + subdir).mkdirs();

        File movedProperties = new File(filesPath + subdir + propertiesFile.getName());
        Files.move(propertiesFile.toPath(), movedProperties.toPath(), StandardCopyOption.ATOMIC_MOVE);

        File movedFile = new File(filesPath + subdir + sha256sum);
        Files.move(tmp.toPath(), movedFile.toPath(), StandardCopyOption.ATOMIC_MOVE);

        return sha256sum;
    }

    public void delete(String sha256sum) throws IOException {
        String subdir = hexpath(sha256sum);

        new File(deletedPath + subdir).mkdirs();

        File properties = new File(filesPath + subdir + sha256sum + _PROP_SUFFIX);
        File movedProperties = new File(deletedPath + subdir + sha256sum + _PROP_SUFFIX);
        Files.move(properties.toPath(), movedProperties.toPath(), StandardCopyOption.ATOMIC_MOVE);

        File file = new File(filesPath + subdir + sha256sum);
        File movedFile = new File(deletedPath + subdir + sha256sum);
        Files.move(file.toPath(), movedFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
    }

    private static File writeProperties(Properties properties) throws IOException {
        String filename = properties.getProperty("sha256sum") + _PROP_SUFFIX;
        File file = new File(filename);
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, null);
        fileOut.close();
        return file;
    }

    public static String mimetype(File file) throws IOException {
        Process p = Runtime.getRuntime().exec("file --mime-type " + file.getPath());
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        return stdInput.readLine().split(":")[1].trim();
    }

    public static Properties metadata(File file) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("temp-name", file.getName());
        properties.setProperty("mime-type", mimetype(file));
        properties.setProperty("sha256sum", sha256sum(file));
        properties.setProperty("length", String.valueOf(file.length()));
        JCString.getExtension(file.getName());
        return properties;
    }

    public static String hexpath(String hexstring) {
        StringBuilder s = new StringBuilder();

        char[] chars = hexstring.toCharArray();
        int i = 1;
        int mod = 2;
        for (char c : chars) {
            s.append(c);
            if ((i+2) % mod == 0) {
                s.append(File.separator);
                mod *= mod;
            }
            i++;
        }
        return s.append(File.separator).toString();
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
