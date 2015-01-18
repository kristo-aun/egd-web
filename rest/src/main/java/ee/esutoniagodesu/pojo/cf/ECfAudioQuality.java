package ee.esutoniagodesu.pojo.cf;

import java.util.Arrays;
import java.util.List;

public enum ECfAudioQuality {

    HUMAN_VERY_GOOD(194, "Inimene, väga hea diktsioon & kvaliteet"),
    HUMAN_VERY_OK(195, "Inimene, hea"),
    HUMAN_VERY_BAD(196, "Inimene, halb kvaliteet"),
    ROBOT_VERY_GOOD(197, "Robothääl, väga hea"),
    ROBOT_VERY_OK(198, "Robothääl, rahuldav"),
    ROBOT_VERY_BAD(199, "Robothääl, halb");

    public final int ID;
    public final String TITLE;

    ECfAudioQuality(int id, String title) {
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

    public static List<ECfAudioQuality> asList() {
        return Arrays.asList(values());
    }

    public static ECfAudioQuality findById(int needle) {
        for (ECfAudioQuality p : values()) {
            if (p.ID == needle) return p;
        }
        return null;
    }

    public static ECfAudioQuality findByTitle(String needle) {
        for (ECfAudioQuality p : values()) {
            if (p.TITLE.equals(needle)) return p;
        }
        return null;
    }
}
