package ee.esutoniagodesu.pojo.test.compound;

import ee.esutoniagodesu.pojo.dto.IntIDStringTitle;

public enum EDictionary implements IntIDStringTitle {
    //jmdict_with_freq(1),
    ilo_yellow_jp_et(2),
    core10k(3),
    core6k(4);

    public final int ID;

    EDictionary(int id) {
        ID = id;
    }

    public static EDictionary findById(int id) {
        for (EDictionary p : values()) {
            if (p.ID == id) return p;
        }
        return null;
    }

    public int getId() {
        return ID;
    }

    public String getTitle() {
        return name().toLowerCase();
    }
}
