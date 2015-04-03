package ee.esutoniagodesu.pojo.test.compound;

public enum EKanjiIntervalType {

    index, level;

    public static EKanjiIntervalType findByName(String name) {
        for (EKanjiIntervalType p : values()) {
            if (p.name().equals(name)) return p;
        }
        return null;
    }
}
