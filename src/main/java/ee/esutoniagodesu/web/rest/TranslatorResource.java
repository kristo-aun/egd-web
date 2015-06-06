package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.service.MicrosoftTranslateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

/**
 * REST controller for managing Tofu.
 */
@RestController
@RequestMapping("/api/pub/translator")
public class TranslatorResource {

    @Inject
    private MicrosoftTranslateService service;

    @RequestMapping(value = "/{from}/{to}/{string}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable String from, @PathVariable String to,
                                      @PathVariable String string) throws Exception {

        return Optional.ofNullable(service.translate(from, to, string))
            .map(t -> new ResponseEntity<>(
                t,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
