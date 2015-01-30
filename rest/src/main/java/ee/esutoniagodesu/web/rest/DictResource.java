package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.pojo.entity.EstJap;
import ee.esutoniagodesu.pojo.entity.JapEst;
import ee.esutoniagodesu.service.DictService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/app")
public class DictResource {

    @Inject
    private DictService service;

    @Inject
    private LocaleResolver localeResolver;

    @RequestMapping(value = "/rest/dict/autocomplete/{phrasepart}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> autocomplete(@PathVariable("phrasepart") String phrasepart) {
        return service.autocomplete(phrasepart);
    }

    @RequestMapping(value = "/rest/dict/japest/{phrase}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JapEst> japest(@PathVariable("phrase") String phrase, HttpServletRequest request) {
        String locale = localeResolver.resolveLocale(request).toString();
        return service.japest(locale, phrase);
    }

    @RequestMapping(value = "/rest/dict/estjap/{phrase}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstJap> estjap(@PathVariable("phrase") String phrase, HttpServletRequest request) {
        String locale = localeResolver.resolveLocale(request).toString();
        return service.estjap(locale, phrase);
    }
}
