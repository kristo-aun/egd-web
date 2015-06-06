package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.web.rest.dto.VocabularyDTO;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.KuromojiService;
import ee.esutoniagodesu.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/morphology")
public class MorphologyResource {

    @Inject
    private KuromojiService kuromojiService;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    @RequestMapping(value = "/kuromoji",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public List<VocabularyDTO> kuromoji(@RequestBody String input) {
        return kuromojiService.asDTO(input);
    }
}
