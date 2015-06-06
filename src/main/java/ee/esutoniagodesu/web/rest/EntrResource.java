package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.jmdict.table.Entr;
import ee.esutoniagodesu.bean.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * JMDict.Entr objektide REST teenus.
 * Võimaldab eestikeelseid tõlkeid lisada/muuta/eemaldada.
 * Muuta saab ainult Gloss.txt välja, st. Entr ja Sens muutmisele ei kuulu, kuna
 * need on läbinud kvaliteedikontrolli JMDict projektis.
 */
@RestController
@RequestMapping("/api/entrs")
public class EntrResource {

    private final Logger log = LoggerFactory.getLogger(EntrResource.class);

    @Inject
    private ProjectDAO dao;

    @RequestMapping(value = "",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Entr entr) {
        log.debug("REST request to save Entr : {}", entr);
        dao.save(entr);
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entr> get(@PathVariable int id) {
        log.debug("REST request to get Entr : {}", id);
        Entr entr = dao.find(Entr.class, id);
        if (entr == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entr, HttpStatus.OK);
    }
}
