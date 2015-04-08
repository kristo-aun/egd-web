package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.freq.table.TofuSentence;
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
@RequestMapping("/api/tofu")
public class TofuResource {

    @Inject
    private TofuService service;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    /**
     * PUT  /tofus -> Updates an existing tofu.
     */
    @RequestMapping(value = "",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> update(@Valid @RequestBody TofuSentence tofu) throws URISyntaxException {
        if (tofu.getId() == null) {
            return ResponseEntity.badRequest().header("Failure", "A tofu cannot already have null ID").build();
        }
        service.save(tofu, getSessionUser());
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tofus -> get all the tofus.
     */
    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TofuSentence>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                     @RequestParam(value = "limit", required = false) Integer limit)
        throws URISyntaxException {

        Page<TofuSentence> page = service.getTofusByUser(offset, limit, getSessionUser());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tofus", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tofus/:id -> get the "id" tofu.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TofuSentence> get(@PathVariable int id) {
        return Optional.ofNullable(service.findById(id, getSessionUser()))
            .map(tofu -> new ResponseEntity<>(
                tofu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
