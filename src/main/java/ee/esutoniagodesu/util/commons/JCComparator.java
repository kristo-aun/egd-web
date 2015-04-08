package ee.esutoniagodesu.util.commons;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class JCComparator {
    public static <T> Comparator<T> getComparator(String columnName, boolean reverse) {
        Comparator<T> c;
        if (reverse) {
            c = Collections.reverseOrder(new BeanComparator<T>(columnName, new NullComparator(false)));
        } else {
            c = new BeanComparator<T>(columnName, new NullComparator(false));
        }
        return c;
    }

    public static void sort(List list, String columnName, boolean reverse) {
        Comparator c = getComparator(columnName, reverse);
        Collections.sort(list, c);
    }
}
