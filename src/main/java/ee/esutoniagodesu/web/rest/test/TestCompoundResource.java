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
import java.util.List;

@RestController
@RequestMapping("/api/test/compounds")
public class TestCompoundResource {

    @Inject
    private UserService userService;

    @Inject
    private TestCompoundService service;

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
    public List<KanjiCompound> submit(@RequestBody TestCompoundSubmitDTO submit) {
        return service.submit(submit, getSessionUser());
    }
}
