package ee.esutoniagodesu.pojo.cf;

import ee.esutoniagodesu.pojo.dto.IntIDStringTitle;

import java.util.Arrays;
import java.util.List;

public enum ECfOrigin implements IntIDStringTitle {

    EKI(5, "EKI lemma"),
    JMDICT(7, "JMDICT Jaapani"),
    GOOGLE(8, "Google translate JP"),
    JATIK(16, "JATIK 0.43 JP"),
    ILO(18, "ILO taskusõn. JP"),
    FELD(209, "E.Feldberg"),
    TEKSAURUS(227, "TEKSaurus"),
    EGD(229, "EsutoniaGoDesu"),
    ANKI(230, "Anki shared deck");

    public final int ID;
    public final String TITLE;

    ECfOrigin(int id, String title) {
        ID = id;
        TITLE = title;
    }

    public int getId() {
        return ID;
    }

    public void setId(int id) {
    }

    public String getTitle() {
        return TITLE;
    }

    public void setTitle(String title) {
    }

    public static List<ECfOrigin> asList() {
        return Arrays.asList(values());
    }

    public static ECfOrigin findById(int needle) {
        for (ECfOrigin p : values()) {
            if (p.ID == needle) return p;
        }
        return null;
    }

    public static ECfOrigin findByTitle(String needle) {
        for (ECfOrigin p : values()) {
            if (p.TITLE.equals(needle)) return p;
        }
        return null;
    }
}
