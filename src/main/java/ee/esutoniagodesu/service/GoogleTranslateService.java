package ee.esutoniagodesu.service;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
 * 2'000'000 characters/month
 */
@Service
public class GoogleTranslateService {

    private static final Logger log = LoggerFactory.getLogger(GoogleTranslateService.class);

    public static String translate(String from, String to, String string) {
        try {
            Language lfrom = Language.fromString(from);
            Language lto = Language.fromString(to);
            log.debug("translate: {}, {}, {}", string, lfrom, lto);

            return Translate.DEFAULT.execute(string, lfrom, lto);
        } catch (Exception e) {
            log.error("translate: ", e.getMessage(), e);
            return null;
        }
    }
}
