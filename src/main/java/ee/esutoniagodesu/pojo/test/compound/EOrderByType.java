package ee.esutoniagodesu.pojo.test.compound;

import ee.esutoniagodesu.pojo.dto.IntIDStringTitle;

public enum EOrderByType implements IntIDStringTitle {
    random(1),
    filter_id(2);

    public final int ID;

    EOrderByType(int id) {
        ID = id;
    }

    public static EOrderByType findById(int id) {
        for (EOrderByType p : values()) {
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
