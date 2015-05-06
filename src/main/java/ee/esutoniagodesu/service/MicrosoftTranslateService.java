package ee.esutoniagodesu.service;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * http://blogs.msdn.com/b/translation/p/gettingstarted1.aspx
 * 2'000'000 characters/month
 * */
@Service
public class MicrosoftTranslateService {

    private static final Logger log = LoggerFactory.getLogger(MicrosoftTranslateService.class);

    static {
        log.debug("MicrosoftTranslateService.init");
        Translate.setClientId("esutoniagodesu");
        Translate.setClientSecret("qkAx7U5QVpvDh7kVijUhCkofH7Dd9My4RzlkXPwlM2g");
    }

    public String translate(String from, String to, String string) throws Exception {
        return Translate.execute(string, Language.fromString(from), Language.fromString(to));
    }
}
