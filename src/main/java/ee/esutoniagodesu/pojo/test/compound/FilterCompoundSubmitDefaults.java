package ee.esutoniagodesu.pojo.test.compound;

import java.util.LinkedHashMap;
import java.util.Map;

public enum FilterCompoundSubmitDefaults {

    ilo_heisig6_1_100(1, getIloHeisig6()),
    core6k_heisig6_1_900(2, getCore6KHeisig6()),
    core10k_jlpt_1_3(3, getCore10KJLPT());

    public final int ID;
    public final FilterCompoundSubmitDTO VALUE;

    FilterCompoundSubmitDefaults(int id, FilterCompoundSubmitDTO form) {
        ID = id;
        VALUE = form;
    }

    public static FilterCompoundSubmitDefaults getValueById(int id) {
        for (FilterCompoundSubmitDefaults p : values()) {
            if (p.ID == id) return p;
        }
        throw new NullPointerException("id=" + id);
    }

    public static Map<Integer, String> asMap() {
        Map<Integer, String> result = new LinkedHashMap<>();
        for (FilterCompoundSubmitDefaults p : values()) {
            result.put(p.ID, p.name().toLowerCase());
        }
        return result;
    }

    //------------------------------ vormid ------------------------------

    public static FilterCompoundSubmitDTO getIloHeisig6() {
        FilterCompoundSubmitDTO submit = new FilterCompoundSubmitDTO();

        submit.dictionary = EDictionary.ilo_yellow_jp_et.ID;
        submit.generateCount = 100;

        submit.notesVisible = true;
        submit.compLengthInterval[0] = 1;
        submit.compLengthInterval[1] = 3;
        submit.filterType = EFilterType.heisig6.ID;//heisig

        submit.kanjiIntervalType = "index";
        submit.kanjiInterval[0] = 1;//heisig frame from
        submit.kanjiInterval[1] = 100;

        submit.orderByType = EOrderByType.random.ID;//filter_id

        submit.strokeCountHintVisible = true;
        submit.radicalHintVisible = true;
        submit.noEnIfHasEt = true;

        return submit;
    }

    public static FilterCompoundSubmitDTO getCore6KHeisig6() {
        FilterCompoundSubmitDTO submit = new FilterCompoundSubmitDTO();

        submit.dictionary = EDictionary.core6k.ID;
        submit.generateCount = 100;

        submit.notesVisible = true;
        submit.compLengthInterval[0] = 1;
        submit.compLengthInterval[1] = 4;
        submit.filterType = EFilterType.heisig6.ID;//heisig

        submit.kanjiIntervalType = "index";
        submit.kanjiInterval[0] = 1;//heisig frame from
        submit.kanjiInterval[1] = 900;

        submit.orderByType = EOrderByType.random.ID;//filter_id

        submit.strokeCountHintVisible = true;
        submit.radicalHintVisible = false;
        submit.noEnIfHasEt = false;

        return submit;
    }

    public static FilterCompoundSubmitDTO getCore10KJLPT() {
        FilterCompoundSubmitDTO submit = new FilterCompoundSubmitDTO();

        submit.dictionary = EDictionary.core10k.ID;
        submit.generateCount = 100;

        submit.notesVisible = true;
        submit.compLengthInterval[0] = 1;
        submit.compLengthInterval[1] = 4;
        submit.filterType = EFilterType.jlpt.ID;//

        submit.kanjiIntervalType = "level";
        submit.kanjiInterval[0] = 1;//frame from
        submit.kanjiInterval[1] = 3;

        submit.orderByType = EOrderByType.random.ID;//

        submit.strokeCountHintVisible = true;
        submit.radicalHintVisible = false;
        submit.noEnIfHasEt = false;

        return submit;
    }
}
