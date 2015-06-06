package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/pub/images")
public class ImageResource {

    @Inject
    private ImageService service;

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void get(@PathVariable int id, HttpServletResponse response) throws IOException {
        service.image(id, response);
    }
}
