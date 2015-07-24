package ee.esutoniagodesu.pojo.test.compound;

import ee.esutoniagodesu.pojo.entity.IntIDStringTitle;

public enum EDictionary implements IntIDStringTitle {

    ilo_yellow_jp_et(1),
    core10k(2),
    core6k(3),
    tofu(4);

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
