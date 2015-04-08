package ee.esutoniagodesu.util.lang.alphab;

import java.util.Arrays;
import java.util.List;

public enum HepburnRomaji {

    a('a'),
    b('b'),
    c('c'),
    d('d'),
    e('e'),
    f('f'),
    g('g'),
    h('h'),
    i('i'),
    j('j'),
    k('k'),
    m('m'),
    n('n'),
    o('o'),
    p('p'),
    r('r'),
    s('s'),
    t('t'),
    u('u'),
    v('v'),
    w('w'),
    y('y'),
    z('z'),
    a_('ā'),
    i_('ī'),
    e_('ē'),
    o_('ō'),
    u_('ū'),
    apostrophe('\'');

    public static final Character[] AS_ARR = new Character[]
        {'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'o',
            'p', 'r', 's', 't',
            'u', 'v', 'w', 'y',
            'z',
            'ā', 'ī', 'ē', 'ō', 'ū', '\''};

    public static final List<Character> AS_CHAR_LIST = Arrays.asList(AS_ARR);

    public final char AS_CHAR;
    public final String AS_STRING;

    HepburnRomaji(char ch) {
        AS_CHAR = ch;
        AS_STRING = String.valueOf(ch);
    }

    public static char[] asCharArray() {
        throw new RuntimeException("not implemented");
    }

    public static String[] asStringArray() {
        throw new RuntimeException("not implemented");
    }

    public static boolean isHepburnRomaji(char ch) {
        return findByChar(ch) != null;
    }

    public static boolean isHepburnRomaji(String s) {
        return isHepburnRomaji(s.toCharArray()[0]);
    }

    public static HepburnRomaji findByChar(char ch) {
        for (HepburnRomaji p : values()) {
            if (p.AS_CHAR == ch) return p;
        }
        return null;
    }
}
