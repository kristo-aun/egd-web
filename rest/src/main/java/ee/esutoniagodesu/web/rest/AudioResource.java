package ee.esutoniagodesu.web.rest;

import com.jc.hibernate.ProjectDAO;
import com.jc.http.HeaderContentType;
import ee.esutoniagodesu.domain.publik.table.Audio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/app")
public class AudioResource {

    private final Logger log = LoggerFactory.getLogger(AudioResource.class);

    @Inject
    private ProjectDAO dao;

    @RequestMapping(value = "/rest/audios",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Audio audio) {
        log.debug("REST request to save Audio : {}", audio);
        dao.save(audio);
    }

    @RequestMapping(value = "/rest/audios",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Audio> getAll() {
        log.debug("REST request to get all Audios");
        return dao.findAll(Audio.class);
    }

    @RequestMapping(value = "/rest/audios/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Audio> get(@PathVariable Integer id) {
        log.debug("REST request to get Audio : {}", id);
        Audio audio = dao.find(Audio.class, id);
        if (audio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(audio, HttpStatus.OK);
    }

    @RequestMapping(value = "/{audioId}.mp3",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void audio(@PathVariable int audioId, HttpServletResponse resp) throws IOException {
        log.debug("audio: audioId=" + audioId);
        byte[] bytes = dao.find(Audio.class, audioId).getAudioFile();
        resp.setContentType(HeaderContentType.MP3.CONTENT_TYPE);
        resp.setContentLength(bytes.length);
        FileCopyUtils.copy(bytes, resp.getOutputStream());
        resp.flushBuffer();
    }

    @RequestMapping(value = "/rest/audios/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Integer id) {
        log.debug("REST request to delete Audio : {}", id);
        dao.removeById(Audio.class, id);
    }
}
