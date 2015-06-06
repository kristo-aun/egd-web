package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.publik.table.Audio;
import ee.esutoniagodesu.util.JCFile;
import ee.esutoniagodesu.bean.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/api/audio")
public class AudioResource {

    private static final Logger log = LoggerFactory.getLogger(AudioResource.class);

    @Inject
    private ProjectDAO dao;

    @RequestMapping(value = "/{audioId}",
        method = RequestMethod.GET)
    public HttpEntity<byte[]> audio(@PathVariable int audioId) throws IOException {
        log.debug("audio: audioId=" + audioId);
        Audio audio = dao.find(Audio.class, audioId);
        byte[] bytes = audio.getAudioFile();
        HttpHeaders header = new HttpHeaders();
        //header.setContentType(new MediaType("audio", "ogg"));
        header.setContentType(MediaType.valueOf(JCFile.getContentType(audio.getFileName())));
        header.set("Content-Disposition", "inline; filename=" + audio.getFileName());
        header.setContentLength(bytes.length);
        return new HttpEntity<>(bytes, header);
    }
}
