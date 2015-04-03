package ee.esutoniagodesu.pojo.cf;

public enum ECfReportType {
    HEISIG6, HEISIG6_CUSTOM, HEISIG4, KANJI,
    JAPEST, ESTJAP, ARTICLE;

    public static ECfReportType findByName(String entityName) {
        for (ECfReportType p : values()) {
            if (p.name().equalsIgnoreCase(entityName)) return p;
        }
        throw new IllegalArgumentException("No report type: entityName=" + entityName);
    }
}
