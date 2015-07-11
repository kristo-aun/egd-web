package ee.esutoniagodesu.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.service.KuromojiService;
import ee.esutoniagodesu.service.ReadingService;
import ee.esutoniagodesu.util.PaginationUtil;
import ee.esutoniagodesu.web.rest.dto.View;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ReadingResource.BASE_URL)
public class ReadingResource {

    private static final Logger log = Logger.getLogger(ReadingResource.class);

    public static final String BASE_URL = "/api/readings";

    @Inject
    private ReadingService service;

    @Inject
    private MappingJackson2HttpMessageConverter mapper;

    private Reading resolve(String reading, MultipartFile file) throws IOException {
        Reading entity = mapper.getObjectMapper().readValue(reading, Reading.class);
        entity.setAudioFile(file);
        return entity;
    }

    private ResponseEntity<Reading> create(Reading entity) throws URISyntaxException, IOException {
        if (entity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new entity cannot already have an ID").body(null);
        }
        Reading result = service.create(entity);
        return ResponseEntity.created(new URI(BASE_URL + "/" + result.getId())).body(result);
    }


    @Autowired
    private MultipartResolver multipartResolver;

    @RequestMapping(value = "",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reading> save(@RequestParam("json") String json, MultipartHttpServletRequest req) throws URISyntaxException, IOException {

        MultipartHttpServletRequest request = multipartResolver.resolveMultipart(req);


        System.out.println("request" + request);
        System.out.println("getRequestHeaders" + request.getRequestHeaders());
        System.out.println("getAttributeNames" + request.getAttributeNames());
        System.out.println("getContentLength" + request.getContentLength());
        System.out.println("getContentType" + request.getContentType());
        System.out.println("getHeaderNames" + request.getHeaderNames());
        MultipartFile file = request.getFile("file");
        log.debug(file);

        Reading entity = resolve(json, file);
        if (entity.getId() == null) return create(entity);
        return ResponseEntity.ok().body(service.update(entity));
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reading>> getReadings(@RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "limit", required = false) Integer limit) throws URISyntaxException {
        Page<Reading> result = service.getReadings(page, limit);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @JsonView(View.Basic.class)
    @RequestMapping(value = "/byTag",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reading>> byTag(@RequestParam(value = "tag") String tag,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "limit", required = false) Integer limit) throws URISyntaxException {

        Page<Reading> result = service.findByTag(tag, page, limit);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
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

    @RequestMapping(value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Integer id) {
        service.deleteReading(id);
    }
}
