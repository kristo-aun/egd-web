package ee.esutoniagodesu.service;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
 * 2'000'000 characters/month
 */
@Service
public class MicrosoftTranslateService {

    private static final Logger log = LoggerFactory.getLogger(MicrosoftTranslateService.class);

    public static String translate(String from, String to, String string) {
        try {
            Language lfrom = Language.fromString(from);
            Language lto = Language.fromString(to);
            log.debug("translate: {}, {}, {}", string, lfrom, lto);
            return Translate.execute(string, lfrom, lto);
        } catch (Exception e) {
            log.error("translate: ", e.getMessage());
            return null;
        }
    }
}
