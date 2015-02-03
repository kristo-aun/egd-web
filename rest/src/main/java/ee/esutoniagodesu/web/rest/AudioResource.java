package ee.esutoniagodesu.web.rest;

import com.jc.hibernate.ProjectDAO;
import com.jc.util.JCFile;
import ee.esutoniagodesu.domain.publik.table.Audio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/app")
public class AudioResource {

    private static final Logger log = LoggerFactory.getLogger(AudioResource.class);

    @Inject
    private ProjectDAO dao;

    @RequestMapping(value = "/rest/audio/{audioId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void audio(@PathVariable int audioId, HttpServletResponse resp) throws IOException {
        log.debug("audio: audioId=" + audioId);
        Audio audio = dao.find(Audio.class, audioId);
        resp.setContentType(JCFile.getContentType(audio.getFileName()));
        resp.setContentLength(audio.getAudioFile().length);
        FileCopyUtils.copy(audio.getAudioFile(), resp.getOutputStream());
        resp.flushBuffer();
    }
}
