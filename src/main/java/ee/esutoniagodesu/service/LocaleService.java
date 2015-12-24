package ee.esutoniagodesu.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class LocaleService {

    @Inject
    private LocaleResolver localeResolver;

    @Inject
    private MessageSource messageSource;

    public String m(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    public String m(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String m(String code, HttpServletRequest req) {
        Locale locale = localeResolver.resolveLocale(req);
        return messageSource.getMessage(code, null, locale);
    }
}
