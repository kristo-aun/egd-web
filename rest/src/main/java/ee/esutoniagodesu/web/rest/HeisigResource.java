package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.heisig.view.VHeisig6Custom;
import ee.esutoniagodesu.service.HeisigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/app")
public class HeisigResource extends AbstractRestResource<VHeisig6Custom, Integer> {

    @Inject
    private HeisigService service;

    @RequestMapping(value = "/rest/heisigs/{book}/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VHeisig6Custom> heisigs(@PathVariable("book") int book, @PathVariable("query") String query) {
        return service.getCollection(book, query);
    }

    @RequestMapping(value = "/rest/heisigs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VHeisig6Custom> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/rest/heisigs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VHeisig6Custom> get(@PathVariable Integer id) {
        VHeisig6Custom entity = service.getHeisig6(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public void save(VHeisig6Custom entity) {
        throw new RuntimeException("not implemented");
    }

    public void delete(Integer integer) {
        throw new RuntimeException("not implemented");
    }
}
