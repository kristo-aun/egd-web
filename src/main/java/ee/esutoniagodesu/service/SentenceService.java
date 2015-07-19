package ee.esutoniagodesu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentenceService {

    private static final Logger log = LoggerFactory.getLogger(SentenceService.class);

    public static String firstGloss(String rawGloss) {
        if (!rawGloss.contains(",") && !rawGloss.contains("(") && !rawGloss.contains(" ")) return rawGloss;
        if (rawGloss.contains(",")) {
            rawGloss = rawGloss.split(",")[0];
        }
        if (rawGloss.contains("(")) {
            if (rawGloss.indexOf("(") != 0)
                rawGloss = rawGloss.split("\\(")[0];
            else
                rawGloss = rawGloss.split("\\)")[1];
        }
        return rawGloss.trim();
    }

    public static String removeFurigana(String jpSentenceWithFurigana) {
        StringBuilder kanji = new StringBuilder();
        boolean ignore = false;
        for (char p : jpSentenceWithFurigana.toCharArray()) {
            if (p == ' ' || p == '\t') continue;
            if (p == '[') ignore = true;
            else if (p == ']') {
                if (!ignore) throw new IllegalStateException("et ignore kinni panna, peab see olema avatud");
                ignore = false;
                continue;
            }
            if (!ignore) kanji.append(p);
        }
        return kanji.toString();
    }
}
