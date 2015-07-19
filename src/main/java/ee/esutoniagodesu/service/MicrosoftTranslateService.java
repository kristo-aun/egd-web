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

    public String translate(String from, String to, String string) {
        try {
            return Translate.execute(string, Language.fromString(from), Language.fromString(to));
        } catch (Exception e) {
            log.error("translate: ", e.getMessage());
            return null;
        }
    }
}
