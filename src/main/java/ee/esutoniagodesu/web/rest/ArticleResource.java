package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.test.dto.ArticleDTO;
import ee.esutoniagodesu.domain.test.table.Article;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.ArticleService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ArticleResource.BASE_URL)
public class ArticleResource {

    public static final String BASE_URL = "/api/articles";

    @Inject
    private ArticleService service;

    @Inject
    private UserService userService;

    private User getSessionUser() {
        return userService.getUserWithAuthorities();
    }

    @RequestMapping(value = "",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<Void> create(@RequestBody Article entity) throws URISyntaxException {
        if (entity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new article cannot already have an ID").build();
        }
        service.save(entity, getSessionUser());
        return ResponseEntity.created(new URI(BASE_URL + "/" + entity.getId())).build();
    }

    @RequestMapping(value = "",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<Void> update(@RequestBody Article entity) throws URISyntaxException {
        if (entity.getId() == null) {
            return create(entity);
        }
        service.save(entity, getSessionUser());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> getArticles(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "limit", required = false) Integer limit) throws URISyntaxException {
        Page<ArticleDTO> result = service.getArticles(page, limit, getSessionUser());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL, page, limit);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> get(@PathVariable Integer id) {
        return Optional.ofNullable(service.getArticle(id, getSessionUser()))
            .map(author -> new ResponseEntity<>(
                author,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public void delete(@PathVariable Integer id) {
        service.deleteArticle(id, getSessionUser());
    }
}
