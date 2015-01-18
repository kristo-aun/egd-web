package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.test.table.Article;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ArticleResource extends AbstractRestResource<Article, Integer> {

    @Inject
    private ArticleService service;

    @RequestMapping(value = "/rest/articles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public void save(@RequestBody Article article) {
        service.save(article, getLoggedUserName());
    }

    @RequestMapping(value = "/rest/articles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public List<Article> getAll() {
        return service.getArticlesByUser(getLoggedUserName());
    }

    @RequestMapping(value = "/rest/articles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public ResponseEntity<Article> get(@PathVariable Integer id) {
        Article article = service.getArticle(id, getLoggedUserName(), true);

        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/articles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.USER)
    public void delete(@PathVariable Integer id) {
        service.deleteArticle(id, getLoggedUserName());
    }
}
