package ee.esutoniagodesu.util;

public final class GridUtils {

    public static int countPages(int countAll, int pageSize) {
        if (countAll < 1) return 0;
        return ((countAll - 1) / pageSize) + 1;
    }

    public static int[] rowsFromTo(int page, int pageSize, int countAll) {
        int pages = countPages(countAll, pageSize);
        //kui lehti ei ole
        if (pages == 0 || page < 1) return new int[]{0, 0};
        //kui ei küsita viimast lehte
        int firstrow = (page * pageSize) - pageSize + 1;
        if (page < pages) return new int[]{firstrow, page * pageSize};
        //viimane leht
        return new int[]{firstrow, countAll};
    }

    public static int getRowPageNo(int rowIndex, int pageSize) {
        return ((rowIndex - 1) / pageSize) + 1;
    }
}
