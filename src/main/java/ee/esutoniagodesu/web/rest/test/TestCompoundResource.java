package ee.esutoniagodesu.web.rest.test;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.pojo.test.compound.KanjiCompound;
import ee.esutoniagodesu.pojo.test.compound.TestCompoundParamsDTO;
import ee.esutoniagodesu.pojo.test.compound.TestCompoundSubmitDTO;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.TestCompoundService;
import ee.esutoniagodesu.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(TestCompoundResource.BASE_URL)
public class TestCompoundResource {

    public static final String BASE_URL = "/api/test/compound";

    @Inject
    private TestCompoundService service;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    @RequestMapping(value = "/params",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public TestCompoundParamsDTO getAllParams() {
        return service.params();
    }

    @RequestMapping(value = "/form-default/{defaultId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<TestCompoundSubmitDTO> getFormDefault(@PathVariable("defaultId") int defaultId) {
        TestCompoundSubmitDTO result = service.getFormDefault(defaultId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/submit",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<List<KanjiCompound>> submit(@Valid @RequestBody TestCompoundSubmitDTO submit) {
        List<KanjiCompound> result = service.submit(submit, getSessionUser());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
