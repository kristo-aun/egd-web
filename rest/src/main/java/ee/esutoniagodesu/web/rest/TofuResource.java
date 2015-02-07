package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.TofuService;
import ee.esutoniagodesu.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/app")
public class TofuResource {

    @Inject
    private TofuService service;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    @RequestMapping(value = "/rest/tofus",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void save(@RequestBody TofuSentence tofu) {
        service.save(tofu, getSessionUser());
    }

    @RequestMapping(value = "/rest/tofus",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<TofuSentence> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return service.getTofusByUser(page, size, getSessionUser());
    }
}
