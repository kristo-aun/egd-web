package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.core.table.TofuSentence;
import ee.esutoniagodesu.pojo.test.compound.FilterCompoundSubmitDTO;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.TofuService;
import ee.esutoniagodesu.service.UserService;
import ee.esutoniagodesu.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tofu.
 */
@RestController
@RequestMapping(TofuResource.BASE_URL)
public class TofuResource {

    public static final String BASE_URL = "/api/tofus";

    @Inject
    private TofuService service;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    @RequestMapping(value = "",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<TofuSentence> update(@Valid @RequestBody TofuSentence tofu) throws URISyntaxException {
        TofuSentence result = service.save(tofu, getSessionUser());
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TofuSentence>> getAll(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer limit)
        throws URISyntaxException {

        Page<TofuSentence> result = service.getTofusByUser(page, limit);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @RequestMapping(value = "/byFilter",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<List<TofuSentence>> byFilter(@Valid @RequestBody FilterCompoundSubmitDTO filter) {
        List<TofuSentence> result = service.byFilter(filter);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TofuSentence> get(@PathVariable int id) {
        return Optional.ofNullable(service.findById(id))
            .map(tofu -> new ResponseEntity<>(
                tofu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
