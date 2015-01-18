package ee.esutoniagodesu.pojo.test.compound;

import com.jc.util.JCMap;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TestCompoundParams implements Serializable {
    private static final long serialVersionUID = -4846810847410530439L;

    public final Map<Integer, String> dictionaryMap = JCMap.toMap(EDictionary.values());
    public final Map<Integer, String> core10kLevelMap = JCMap.toIntLinkedHashMap(1, 10);
    //SELECT max(char_length(txt)) FROM jmdict.kanj
    public final Map<Integer, String> compLengthMap = JCMap.toIntLinkedHashMap(1, 37);
    public final Map<Integer, String> filterTypeMap = JCMap.toMap(EFilterType.values());

    public final Map<Integer, String> gradeLevelMap = JCMap.toIntLinkedHashMap(1, 9);
    public final Map<Integer, String> jlptLevelMap = JCMap.toIntLinkedHashMap(1, 4);
    public final Map<Integer, String> jouyouLevelMap = JCMap.toIntLinkedHashMap(1, 7);
    public final Map<Integer, String> heisigBookMap = new LinkedHashMap<Integer, String>() {{
        put(4, "4");
        put(6, "6");
    }};
    public final Map<Integer, String> heisig4LessonMap = JCMap.toIntLinkedHashMap(1, 62);
    public final Map<Integer, String> heisig6LessonMap = JCMap.toIntLinkedHashMap(1, 56);
    public final Map<Integer, String> orderByTypeMap = JCMap.toMap(EOrderByType.values());
    public final Map<Integer, String> orderDirectionMap = JCMap.toMap(EOrderDirection.values());
}
