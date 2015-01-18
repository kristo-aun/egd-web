package ee.esutoniagodesu.pojo.cf;

import java.util.Arrays;
import java.util.List;

public enum ECfVocaTransQuality {
    ILO(1, "ILO taskusõnastik"),
    JATIK(2, "JATIK 0.43"),
    GOOGLE(3, "JMDICT JP tõlge via Google"),
    EKI(4, "JMDICT JP tõlge via EKI ing-est");

    public final int ID;
    public final String TITLE;

    ECfVocaTransQuality(int id, String title) {
        ID = id;
        TITLE = title;
    }

    public int getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public static List<ECfVocaTransQuality> asList() {
        return Arrays.asList(values());
    }

    public static ECfVocaTransQuality findById(int needle) {
        for (ECfVocaTransQuality p : values()) {
            if (p.ID == needle) return p;
        }
        return null;
    }

    public static ECfVocaTransQuality findByTitle(String needle) {
        for (ECfVocaTransQuality p : values()) {
            if (p.TITLE.equals(needle)) return p;
        }
        return null;
    }
}
