package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.service.DictService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/pub/dict")
public class DictResource {

    @Inject
    private DictService service;

    @Inject
    private LocaleResolver localeResolver;

    @RequestMapping(value = "/autocomplete/{phrasepart}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> autocomplete(@PathVariable("phrasepart") String phrasepart) {
        return service.autocomplete(phrasepart);
    }

    @RequestMapping(value = "/jmtrans/{phrase}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List jmtrans(@PathVariable("phrase") String phrase, HttpServletRequest request) {
        String locale = localeResolver.resolveLocale(request).toString();
        return service.jmtrans(locale, phrase);
    }
}
