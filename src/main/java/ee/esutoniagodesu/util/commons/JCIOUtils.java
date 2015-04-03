package ee.esutoniagodesu.util.commons;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class JCIOUtils {
	public static String asString(File file) throws IOException {
		FileInputStream fisTargetFile = new FileInputStream(file);
		return IOUtils.toString(fisTargetFile, Charset.defaultCharset());
	}

	public static void addToZipFile(Map.Entry<String, byte[]> file, ZipOutputStream zos) throws IOException {
		addToZipFile(file.getKey(), file.getValue(), zos);
	}

	public static void addToZipFile(String fileName, byte[] bytes, ZipOutputStream zos) throws IOException {
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		zos.write(bytes, 0, bytes.length);
		zos.closeEntry();
	}
}
