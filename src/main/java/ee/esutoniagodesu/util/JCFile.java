package ee.esutoniagodesu.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

public final class JCFile {
    public static String getContentType(String file) {
        return MimetypesFileTypeMap
            .getDefaultFileTypeMap()
            .getContentType(file);
    }

    public static String getContentType(File file) {
        return MimetypesFileTypeMap
            .getDefaultFileTypeMap()
            .getContentType(file);
    }
}
