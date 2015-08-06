package ee.esutoniagodesu.repository.project;

import com.google.common.base.Joiner;
import ee.esutoniagodesu.domain.publik.table.EOrigin;
import ee.esutoniagodesu.domain.publik.view.VStats;
import ee.esutoniagodesu.pojo.cf.ECfEtSonaliik;
import ee.esutoniagodesu.pojo.cf.ECfJpCategory;
import ee.esutoniagodesu.pojo.cf.ECfVocaTransQuality;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class ReportDB extends AbstractProjectRepository {

    public List<Map<String, ?>> getTofuTranslatedByUser(String uuid) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call core.f_tofu_translated_by_user(?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, uuid);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            result = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", rs.getInt("id"));
                item.put("word", rs.getString("word"));
                item.put("sentence", rs.getString("sentence"));
                item.put("lang", rs.getString("lang"));
                item.put("translation", rs.getString("translation"));

                result.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Map<String, ?>> findAllVHeisig4() {

        StringBuilder msg = new StringBuilder("findAllVHeisig4: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select frame_no, kanji, keyword_en, keyword_et, keyword_jp," +
                " keyword_jp_kana, stroke_diagram, my_story, constituent, stroke_count," +
                " lesson_no, story, heisig_comment, koohii_story_1, koohii_story_2," +
                " on_yomi, kun_yomi, example_words, keyword_jp_sentence from v_heisig4";

            s = con.prepareCall(sql);
            s.setFetchSize(50);
            rs = s.executeQuery();
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
            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Map<String, ?>> findAllVHeisig6() {

        StringBuilder msg = new StringBuilder("findAllVHeisig6: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            con = dao.getConnection();
            long ms = System.currentTimeMillis();

            String sql = "select frame_no, kanji from v_heisig6";

            s = con.prepareCall(sql);
            s.setFetchSize(50);
            rs = s.executeQuery();
            Map<String, Object> item;
            while (rs.next()) {
                item = new HashMap<>();
                int i = 1;

                item.put("frameNo", rs.getInt(i++));//frame_no
                item.put("kanji", rs.getString(i));//kanji

                result.add(item);
            }
            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Map<String, ?>> findAllVKanji() {

        StringBuilder msg = new StringBuilder("findAllVkanji: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select kanji, stroke_count, on_yomis, kun_yomis, primitives," +
                " jinmei_seq, jouyou_radical, jouyou_year_added, jouyou_old_kanji," +
                " jouyou_meaning_en, jouyou_grade, jouyou_grade_seq from v_kanji";

            s = con.prepareCall(sql);
            rs = s.executeQuery();
            rs.setFetchSize(50);
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
            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    @Deprecated
    public List<Map<String, ?>> findJapEst(boolean beta) {
        StringBuilder msg = new StringBuilder("findJapEst: beta=" + beta);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_jp_et order by jp COLLATE \"japanese\", sequence_of_et";

            s = con.prepareCall(sql);
            s.setFetchSize(1000);
            rs = s.executeQuery();

            Map<String, Object> item;
            while (rs.next()) {
                item = new HashMap<>();
                int i = 1;

                //jp
                item.put("jp", rs.getString(i++));
                item.put("kana", rs.getString(i++));
                item.put("romaji", rs.getString(i++));

                item.put("cfVocaJpOrigin", EOrigin.valueOf(rs.getString(i++)));

                Array a = rs.getArray(i++);
                Map<Integer, String> simples = new LinkedHashMap<>();
                if (!rs.wasNull()) {
                    Integer[] liikSimple = (Integer[]) a.getArray();
                    if (a != null && liikSimple != null && liikSimple.length > 0) {
                        for (Integer p : liikSimple) {
                            if (p != null) simples.put(p, ECfJpCategory.findById(p).ABBREVIATION);
                        }
                    }
                }
                item.put("categorySimple", simples.size() > 0 ? Joiner.on(", ").join(simples.values()) : null);

                Map<Integer, String> advs = new LinkedHashMap<>();
                Array b = rs.getArray(i++);
                if (!rs.wasNull()) {
                    Integer[] liikAdv = (Integer[]) b.getArray();
                    if (b != null && liikAdv != null && liikAdv.length > 0) {
                        for (Integer p : liikAdv) {
                            if (p != null) advs.put(p, ECfJpCategory.findById(p).ABBREVIATION);
                        }
                    }
                }
                item.put("cfJpCategory", advs.size() > 0 ? Joiner.on(", ").join(advs.values()) : null);

                item.put("et", rs.getString(i++));
                i++;//et_audio_id;

                item.put("cfVocaEtOrigin", EOrigin.valueOf(rs.getString(i++)));

                //et sõnaliigid
                Map<Integer, String> etLiigid = new LinkedHashMap<>();
                Array c = rs.getArray(i++);
                if (!rs.wasNull() && c != null) {
                    Integer[] ca = null;
                    try {
                        ca = (Integer[]) c.getArray();
                    } catch (SQLException ignored) {
                    }

                    if (ca != null && ca.length > 0) {
                        for (Integer p : ca) {
                            if (p != null) etLiigid.put(p, ECfEtSonaliik.findById(p).ABBREVIATION);
                        }
                    }
                }
                item.put("cfEtSonaliik", etLiigid.size() > 0 ? Joiner.on(", ").join(etLiigid.values()) : null);

                i++;//voca_jp_id
                i++;//voca_et_id

                item.put("sequenceOfEt", rs.getString(i++));

                int cfJpEtTransQuality = rs.getInt(i++);
                item.put("cfJpEtTransQuality", cfJpEtTransQuality > 0 ? ECfVocaTransQuality.findById(cfJpEtTransQuality).TITLE : null);

                item.put("descr", rs.getString(i++));
                item.put("uvote", rs.getString(i++));
                item.put("dvote", rs.getString(i));

                result.add(item);
            }
            log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public VStats getStats() {
        StringBuilder msg = new StringBuilder("getStats: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        VStats result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_stats";

            s = con.prepareCall(sql);
            rs = s.executeQuery();

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
            if (log.isDebugEnabled()) log.debug(msg.append(", found=").append(result != null)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Map<String, ?>> getTranslatedEntrRatio() {
        StringBuilder msg = new StringBuilder("getTranslatedEntrRatio: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "SELECT count_gloss, sum_count, sum_ratio FROM public.v_translated_entr_ratio";

            s = con.prepareCall(sql);
            rs = s.executeQuery();

            Map<String, Object> item;
            while (rs.next()) {
                item = new LinkedHashMap<>();

                item.put("countGloss", rs.getInt(1));
                item.put("sumCount", rs.getInt(2));
                item.put("sumRatio", rs.getFloat(3));

                result.add(item);
            }
            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
        StringBuilder msg = new StringBuilder("getCountGlossToSumFreqRatio: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Map<String, ?>> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "SELECT count_gloss, sum_freq, freq_ratio FROM public.v_count_gloss_to_sum_freq";

            s = con.prepareCall(sql);
            rs = s.executeQuery();

            Map<String, Object> item;
            while (rs.next()) {
                item = new LinkedHashMap<>();

                item.put("countGloss", rs.getInt(1));
                item.put("sumFreq", rs.getInt(2));
                item.put("freqRatio", rs.getFloat(3));

                result.add(item);
            }
            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }
}
