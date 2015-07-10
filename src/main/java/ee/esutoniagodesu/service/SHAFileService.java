package ee.esutoniagodesu.service;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Does not move files across different mount points.
 * Must have write rights to destination folder.
 */
@Service
public class SHAFileService implements EnvironmentAware {

    private static String filesPath;
    private static final int dirlength = 16;

    @Override
    public void setEnvironment(Environment env) {
        filesPath = env.getProperty("app.files.path");
    }

    public File get(String sha256sum) {
        String hexpath = hexpath(sha256sum, dirlength);
        return new File(filesPath + hexpath + sha256sum);
    }

    public void put(File file) throws IOException {

        Properties properties = metadata(file);
        File propertiesFile = writeProperties(properties);

        String sha256sum = properties.getProperty("sha256sum");
        String subdir = hexpath(sha256sum, dirlength);

        new File(filesPath + subdir).mkdirs();

        File movedProperties = new File(filesPath + subdir + propertiesFile.getName());
        Files.move(propertiesFile.toPath(), movedProperties.toPath(), StandardCopyOption.ATOMIC_MOVE);

        File movedFile = new File(filesPath + subdir + sha256sum);
        Files.move(file.toPath(), movedFile.toPath(), StandardCopyOption.ATOMIC_MOVE);

    }

    public boolean delete(String sha256sum) {
        return get(sha256sum).delete();
    }

    private static File writeProperties(Properties properties) throws IOException {
        String filename = properties.getProperty("sha256sum") + ".properties";
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
        properties.setProperty("filename", file.getName());
        properties.setProperty("mime-type", mimetype(file));
        properties.setProperty("sha256sum", sha256sum(file));
        properties.setProperty("length", String.valueOf(file.length()));
        return properties;
    }

    public static String hexpath(String hexstring, int dirlength) {
        StringBuilder s = new StringBuilder();

        char[] chars = hexstring.toCharArray();
        int i = 1;
        for (char c : chars) {
            s.append(c);
            if (i % dirlength == 0) s.append(File.separator);
            i++;
        }
        return s.toString();
    }

    public static String sha256sum(File file) throws IOException {
        Process p = Runtime.getRuntime().exec("sha256sum " + file.getPath());
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        return stdInput.readLine().substring(0, 64);
    }
}
