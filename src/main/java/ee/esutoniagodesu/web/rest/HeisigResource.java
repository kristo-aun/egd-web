package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.heisig.table.HeisigCoreKw;
import ee.esutoniagodesu.domain.heisig.view.VHeisig6Custom;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.HeisigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(HeisigResource.BASE_URL)
public class HeisigResource {

    public static final String BASE_URL = "/api/rtk";

    @Inject
    private HeisigService service;

    @RequestMapping(value = "/byBookAndQuery",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VHeisig6Custom>> byBookAndQuery(@RequestParam int book, @RequestParam String query) {
        return Optional.ofNullable(service.findByBookAndQuery(book, query))
            .map(entity -> new ResponseEntity<>(
                entity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VHeisig6Custom>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VHeisig6Custom> get(@PathVariable Integer id) {
        return Optional.ofNullable(service.getHeisig6(id))
            .map(entity -> new ResponseEntity<>(
                entity,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/defaultWord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<HeisigCoreKw> defaultWord(@RequestParam String kanji,
                                                    @RequestParam String word,
                                                    @RequestParam String wordReading,
                                                    @RequestParam String wordTranslation) {
        return new ResponseEntity<>(
            service.setDefaultHeisigWord(kanji, word, wordReading, wordTranslation),
            HttpStatus.OK);
    }
}
