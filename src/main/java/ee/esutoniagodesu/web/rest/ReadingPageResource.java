package ee.esutoniagodesu.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.domain.library.table.ReadingPage;
import ee.esutoniagodesu.service.ReadingService;
import ee.esutoniagodesu.util.PaginationUtil;
import ee.esutoniagodesu.web.rest.dto.View;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
@RequestMapping(ReadingPageResource.BASE_URL)
public class ReadingPageResource {

    private static final Logger log = Logger.getLogger(ReadingPageResource.class);

    public static final String BASE_URL = "/api/readingPages";

    @Inject
    private ReadingService service;

    @Inject
    private MappingJackson2HttpMessageConverter mapper;

    private ReadingPage resolve(String readingPage, MultipartFile file) throws IOException {
        ReadingPage entity = mapper.getObjectMapper().readValue(readingPage, ReadingPage.class);
        entity.setAudioFile(file);
        return entity;
    }

    private ResponseEntity<ReadingPage> create(ReadingPage entity) throws URISyntaxException, IOException {
        if (entity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new entity cannot already have an ID").body(null);
        }
        ReadingPage result = service.create(entity);
        return ResponseEntity.created(new URI(BASE_URL + "/" + result.getId())).body(result);
    }

    @RequestMapping(value = "",
        method = RequestMethod.POST,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingPage> save(@RequestParam("data") String json, @RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException, IOException {
        ReadingPage entity = resolve(json, file);
        if (entity.getId() == null) return create(entity);
        ReadingPage result = service.update(entity);
        return ResponseEntity.ok().body(result);
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reading>> getReadings(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer limit) throws URISyntaxException {
        Page<Reading> result = service.getReadings(page, limit);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = "/byTag",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reading>> byTag(@RequestParam String tag,
                                               @RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer limit) throws URISyntaxException {

        Page<Reading> result = service.findByTag(tag, page, limit);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @RequestMapping(value = "/autocompleteTag",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> autocompleteTag(@RequestParam String tagstart) throws URISyntaxException {
        return ResponseEntity.ok().body(service.autocompleteTag(tagstart));
    }

    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reading> get(@PathVariable Integer id) {
        return Optional.ofNullable(service.getReading(id))
            .map(author -> new ResponseEntity<>(
                author,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{id}/deleteAudio",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Integer id) {
        service.deleteReading(id);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAudio(@PathVariable Integer id) throws IOException {
        return Optional.ofNullable(service.deleteAudio(id))
            .map(sha -> new ResponseEntity<>(
                sha,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}