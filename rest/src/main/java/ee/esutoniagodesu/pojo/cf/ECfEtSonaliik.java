package ee.esutoniagodesu.pojo.cf;

import java.util.Arrays;
import java.util.List;

/**
 * A - omadussõna
 * D - määrsõna
 * G - omastavaline täiend
 * H - pärisnimi
 * I - hüüdsõna
 * J - sidesõna
 * K - kaassõna
 * N - põhiarvsõna
 * O - järgarvsõna
 * P - asesõna
 * S - nimisõna
 * V - tegusõna
 * X - tundmatu
 */
public enum ECfEtSonaliik {

    N(210, "nimisõna", "n"),
    PARV(211, "põhiarvsõna", "p.arv"),
    ADJ(212, "omadussõna", "adj"),
    ASES(213, "asesõna", "ases"),
    V(214, "tegusõna", "v"),

    KAAS(215, "kaassõna", "kaas"),
    HUUD(216, "hüüdsõna", "hüüd"),
    SIDE(217, "sidesõna", "side"),
    ADV(218, "määrsõna", "adv");

    public final int ID;
    public final String TITLE;
    public String ABBREVIATION;

    ECfEtSonaliik(int id, String title, String abbreviation) {
        ID = id;
        TITLE = title;
        ABBREVIATION = abbreviation;
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

    public static List<ECfEtSonaliik> asList() {
        return Arrays.asList(values());
    }

    public static ECfEtSonaliik findById(int needle) {
        for (ECfEtSonaliik p : values()) {
            if (p.ID == needle) return p;
        }
        return null;
    }

    public static ECfEtSonaliik findByTitle(String needle) {
        for (ECfEtSonaliik p : values()) {
            if (p.TITLE.equals(needle)) return p;
        }
        return null;
    }

    public String toString() {
        return ABBREVIATION;
    }
}
