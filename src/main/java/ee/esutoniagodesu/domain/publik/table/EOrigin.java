package ee.esutoniagodesu.domain.publik.table;

import java.util.Arrays;
import java.util.List;

public enum EOrigin {

    EKI("EKI lemma"),
    JMDICT("JMDICT Jaapani"),
    GOOGLE("Google translate JP"),
    JATIK("JATIK 0.43 JP"),
    ILO("ILO taskusõn. JP"),
    FELD("E.Feldberg"),
    TEKSAURUS("TEKSaurus"),
    EGD("EsutoniaGoDesu"),
    ANKI("Anki shared deck");

    public final String TITLE;

    EOrigin(String title) {
        TITLE = title;
    }

    public static List<EOrigin> asList() {
        return Arrays.asList(values());
    }

    public static EOrigin findByTitle(String needle) {
        for (EOrigin p : values()) {
            if (p.TITLE.equals(needle)) return p;
        }
        return null;
    }
}
