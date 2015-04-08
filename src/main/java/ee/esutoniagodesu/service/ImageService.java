package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.publik.table.Image;
import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

@Service
@Transactional
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Inject
    private ProjectDAO dao;

    public void image(int imageId, HttpServletResponse resp) throws IOException {
        Map.Entry<String, byte[]> image = getImage(imageId);
        resp.setContentType(image.getKey());
        int length = image.getValue().length;
        resp.setContentLength(length);
        FileCopyUtils.copy(image.getValue(), resp.getOutputStream());
        resp.flushBuffer();
    }

    public Map.Entry<String, byte[]> getImage(int imageId) {
        log.debug("getImage: imageId=" + imageId);
        Image i = dao.find(Image.class, imageId);
        if (i == null) throw new IllegalArgumentException("retrievePicture: no image found");
        String mime = getMimeType(i.getFileName());
        return new AbstractMap.SimpleEntry<>(mime, i.getImageFile());
    }

    private static String getMimeType(String fileName) {
        if (fileName == null || !fileName.contains(".")) return null;
        String ext = fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
        switch (ext) {
            case "png":
                return "image/png";
            case "svg":
                return "image/svg+xml";
            default:
                throw new RuntimeException("not implemented");
        }
    }
}
