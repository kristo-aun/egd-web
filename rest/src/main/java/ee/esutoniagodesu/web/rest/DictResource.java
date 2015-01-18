package ee.esutoniagodesu.web.rest;

import com.jc.structure.pojo.IntIDStringTitle;
import ee.esutoniagodesu.pojo.entity.EstJap;
import ee.esutoniagodesu.pojo.entity.JapEst;
import ee.esutoniagodesu.service.DictService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/app/dict")
public class DictResource {

    @Inject
    private DictService service;

    @RequestMapping(value = "/autocomplete",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<IntIDStringTitle> autocomplete(@RequestParam("lang") String lang, @RequestParam("q") String q) {
        return service.autocomplete(lang, q);
    }

    @RequestMapping(value = "/japest",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JapEst> japest(@RequestParam("lang") String lang, @RequestParam("q") String query) {
        return service.japest(lang, query);
    }

    @RequestMapping(value = "/estjap",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstJap> estjap(@RequestParam("lang") String lang, @RequestParam("q") String query) {
        return service.estjap(lang, query);
    }
}
