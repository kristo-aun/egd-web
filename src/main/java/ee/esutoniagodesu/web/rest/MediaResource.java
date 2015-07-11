package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.service.SHAFileService;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/api/media")
public class MediaResource {

    private static final Logger log = LoggerFactory.getLogger(MediaResource.class);

    @Inject
    private SHAFileService service;

    @RequestMapping(value = "/{fileSha}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> get(@PathVariable String fileSha) throws IOException, MimeTypeException {
        log.debug("REST request to get Media : {}", fileSha);

        try {
            Map.Entry<Properties, File> entry = service.getWithProperties(fileSha);

            Properties properties = entry.getKey();
            File file = entry.getValue();
            String mimeType = properties.getProperty("mime-type");

            StringBuilder filename = new StringBuilder(fileSha);
            String origExt = properties.getProperty("orig-extension");

            if (origExt != null) {
                filename.append(".").append(origExt);
            } else {
                try {
                    MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                    MimeType mime = allTypes.forName(mimeType);
                    String ext = mime.getExtension();
                    filename.append(".").append(ext);
                } catch (Exception e) {
                    log.error("no extension", e.getMessage());
                }
            }

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf(mimeType));
            header.set("Content-Disposition", "inline; filename=" + filename.toString());
            header.setContentLength(Long.valueOf(properties.getProperty("length")));

            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<>(isr, header, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException: ", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
