package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.publik.view.VStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class ReportDB extends AbstractRepository {

    @Autowired
    public ReportDB(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<Map<String, ?>> getTofuTranslatedByUser(String uuid) {

        String sql = "{? = call core.f_tofu_translated_by_user(?)}";

        CustomCallableStatementCreator sc = new CustomCallableStatementCreator(sql) {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                con.setAutoCommit(false);
                CallableStatement s = super.createCallableStatement(con);
                s.registerOutParameter(1, Types.OTHER);//cursor
                s.setString(2, uuid);
                return s;
            }
        };

        CallableStatementCallback<List<Map<String, ?>>> cb = s -> {
            s.execute();
            ResultSet rs = (ResultSet) s.getObject(1);

            List<Map<String, ?>> result = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", rs.getInt("id"));
                item.put("word", rs.getString("word"));
                item.put("sentence", rs.getString("sentence"));
                item.put("lang", rs.getString("lang"));
                item.put("translation", rs.getString("translation"));

                result.add(item);
            }
            return result;
        };
        return execute(sc, cb);
    }

    public List<Map<String, ?>> findAllVHeisig4() {

        String sql = "select frame_no, kanji, keyword_en, keyword_et, keyword_jp," +
            " keyword_jp_kana, stroke_diagram, my_story, constituent, stroke_count," +
            " lesson_no, story, heisig_comment, koohii_story_1, koohii_story_2," +
            " on_yomi, kun_yomi, example_words, keyword_jp_sentence from v_heisig4";

        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {};

        PreparedStatementCallback<List<Map<String, ?>>> cb = s -> {
            ResultSet rs = s.executeQuery();

            List<Map<String, ?>> result = new ArrayList<>();
            Map<String, Object> item;
            while (rs.next()) {
                item = new HashMap<>();
                int i = 1;

                item.put("frameNo", rs.getInt(i++));//frame_no
                item.put("kanji", rs.getString(i++));//kanji
                item.put("keywordEn", rs.getString(i++));//keyword_en
                item.put("keywordEt", rs.getString(i++));//keyword_et
                item.put("keywordJp", rs.getString(i++));//keyword_jp

                item.put("keywordJpKana", rs.getString(i++));//keyword_jp_kana
                item.put("strokeDiagram", rs.getString(i++));//stroke_diagram
                item.put("myStory", rs.getString(i++));//my_story
                item.put("constituent", rs.getString(i++));//constituent
                item.put("strokeCount", rs.getInt(i++));//stroke_count

                item.put("lessonNo", rs.getInt(i++));//lesson_no
                item.put("story", rs.getString(i++));//story
                item.put("heisigComment", rs.getString(i++));//heisig_comment
                item.put("koohiiStory1", rs.getString(i++));//koohii_story_1
                item.put("koohiiStory2", rs.getString(i++));//koohii_story_2

                item.put("onYomi", rs.getString(i++));//on_yomi
                item.put("kunYomi", rs.getString(i++));//kun_yomi
                item.put("exampleWords", rs.getString(i++));//example_words
                item.put("keywordJpSentence", rs.getString(i));//keyword_jp_sentence

                result.add(item);
            }

            return result;
        };
        return execute(sc, cb);
    }

    public List<Map<String, ?>> findAllVKanji() {

        String sql = "select kanji, stroke_count, on_yomis, kun_yomis, primitives," +
            " jinmei_seq, jouyou_radical, jouyou_year_added, jouyou_old_kanji," +
            " jouyou_meaning_en, jouyou_grade, jouyou_grade_seq from v_kanji";

        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {};

        PreparedStatementCallback<List<Map<String, ?>>> cb = s -> {
            ResultSet rs = s.executeQuery();

            List<Map<String, ?>> result = new ArrayList<>();
            Map<String, Object> item;
            while (rs.next()) {
                item = new HashMap<>();
                int i = 1;

                item.put("kanji", rs.getString(i++));//kanji
                item.put("strokeCount", rs.getInt(i++));//stroke_count
                item.put("onYomis", rs.getString(i++));//on_yomis
                item.put("kunYomis", rs.getString(i++));//kun_yomis
                item.put("primitives", rs.getString(i++));//primitives

                item.put("jinmeiSeq", rs.getInt(i++));//jinmei_seq
                item.put("jouyouRadical", rs.getString(i++));//jouyou_radical
                item.put("jouyouYearAdded", rs.getInt(i++));//jouyou_year_added
                item.put("jouyouOldKanji", rs.getString(i++));//jouyou_old_kanji
                item.put("jouyouMeaningEn", rs.getString(i++));//jouyou_meaning_en

                item.put("jouyouGrade", rs.getInt(i++));//jouyou_grade
                item.put("jouyouGradeSeq", rs.getInt(i));//jouyou_grade_seq

                result.add(item);
            }

            return result;
        };
        return execute(sc, cb);
    }

    public VStats getStats() {

        String sql = "select * from v_stats";
        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {};

        PreparedStatementCallback<VStats> cb = s -> {
            ResultSet rs = s.executeQuery();

            VStats result = null;
            if (rs.next()) {
                int i = 1;
                result = new VStats();

                result.countJpEntry = rs.getInt(i++);//countJpEntry
                result.countTranslatedEntries = rs.getInt(i++);//countTranslatedEntries
                result.countEtTranslations = rs.getInt(i++);//countEtTranslations
                result.countSentencePairs = rs.getInt(i++);//countSentencePairs
                result.countEstwnExamples = rs.getInt(i++);//countEstwnExamples

                result.countOriginJatik = rs.getInt(i++);//countOriginJatik
                result.countOriginIlo = rs.getInt(i++);//countOriginIlo
                result.countOriginEgd = rs.getInt(i++);//countOriginEgd

                result.countJpSearchWords = rs.getInt(i++);//countJpSearchWords
                result.countJpSearchWithResults = rs.getInt(i++);//countJpSearchWithResults
                result.countEtSearchWords = rs.getInt(i++);//countEtSearchWords
                result.countEtSearchWithResults = rs.getInt(i++);//countEtSearchWithResults

                result.countKanjiInDb = rs.getInt(i++);//countKanjiInDb
                result.countKanjisDescribedWithHeisig = rs.getInt(i++);//countKanjisDescribedWithHeisig
                result.countStrokeDiagrams = rs.getInt(i);//countStrokeDiagrams

            }

            return result;
        };
        return execute(sc, cb);
    }

    public List<Map<String, ?>> getTranslatedEntrRatio() {

        String sql = "{? = call public.f_jmet_entr_gloss_ratio()}";

        CustomCallableStatementCreator sc = new CustomCallableStatementCreator(sql) {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                con.setAutoCommit(false);
                CallableStatement s = super.createCallableStatement(con);
                s.registerOutParameter(1, Types.OTHER);//cursor
                return s;
            }
        };

        CallableStatementCallback<List<Map<String, ?>>> cb = s -> {
            s.execute();
            ResultSet rs = (ResultSet) s.getObject(1);

            List<Map<String, ?>> result = new ArrayList<>();
            Map<String, Object> item;
            while (rs.next()) {
                item = new LinkedHashMap<>();

                item.put("countGloss", rs.getInt(1));
                item.put("sumCount", rs.getInt(2));
                item.put("sumRatio", rs.getFloat(3));

                result.add(item);
            }

            rs.close();
            return result;
        };
        return execute(sc, cb);
    }

    public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
        String sql = "SELECT count_gloss, sum_freq, freq_ratio FROM public.v_count_gloss_to_sum_freq";
        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {};

        PreparedStatementCallback<List<Map<String, ?>>> cb = s -> {
            ResultSet rs = s.executeQuery();

            List<Map<String, ?>> result = new ArrayList<>();
            Map<String, Object> item;
            while (rs.next()) {
                item = new LinkedHashMap<>();

                item.put("countGloss", rs.getInt(1));
                item.put("sumFreq", rs.getInt(2));
                item.put("freqRatio", rs.getFloat(3));

                result.add(item);
            }

            return result;
        };
        return execute(sc, cb);
    }
}
