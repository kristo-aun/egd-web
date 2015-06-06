package ee.esutoniagodesu.pojo.test.compound;

import ee.esutoniagodesu.pojo.entity.IntIDStringTitle;

public enum EOrderDirection implements IntIDStringTitle {
    asc(1),
    desc(2);

    public final int ID;

    EOrderDirection(int id) {
        ID = id;
    }

    public static EOrderDirection findById(int id) {
        for (EOrderDirection p : values()) {
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
