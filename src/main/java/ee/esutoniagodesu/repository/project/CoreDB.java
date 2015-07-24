package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.core.table.*;
import ee.esutoniagodesu.domain.freq.table.NresBase;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "f_compd_ilo_by_kanji", procedureName = "f_compd_ilo_by_kanji",
        resultClasses = Ilo.class,
        parameters = {
            @StoredProcedureParameter(name = "kanjis", type = String.class, mode = ParameterMode.IN),
            @StoredProcedureParameter(name = "compdlfrom", type = Integer.class, mode = ParameterMode.IN),
            @StoredProcedureParameter(name = "compdlto", type = Integer.class, mode = ParameterMode.IN),
            @StoredProcedureParameter(name = "ilo_by_kanji", type = void.class, mode = ParameterMode.REF_CURSOR)

        })
})
@Repository
public class CoreDB extends AbstractProjectRepository {

    public List<TofuSentence> findUserTofus(int from, int to, String createdBy) {
        String sql = "SELECT * from core.tofu_sentence where id between ?1 and ?2 order by id";
        Query q = em.createNativeQuery(sql, TofuSentence.class);
        q.setParameter(1, from);
        q.setParameter(2, to);

        List<TofuSentence> result = q.getResultList();

        for (TofuSentence p : result) {
            p.setTranslation(findUserTofuSentenceTranslation(p.getId(), createdBy));
        }
        return result;
    }

    public TofuSentence findUserTofuById(int id, String createdBy) {
        String sql = "SELECT * from core.tofu_sentence where id = ?1";
        Query q = em.createNativeQuery(sql, TofuSentence.class);
        q.setParameter(1, id);

        TofuSentence result = (TofuSentence) q.getSingleResult();
        result.setTranslation(findUserTofuSentenceTranslation(result.getId(), createdBy));

        return result;
    }

    public TofuSentenceTranslation findUserTofuSentenceTranslation(int tofuSentenceId, String createdBy) {
        if (tofuSentenceId < 1 || createdBy == null)
            throw new IllegalArgumentException("findUserTofuSentenceTranslation");

        try {
            String sql = "SELECT * FROM core.tofu_sentence_translation WHERE tofu_sentence_id=?1 AND created_by=?2";
            Query q = em.createNativeQuery(sql, TofuSentenceTranslation.class);
            q.setParameter(1, tofuSentenceId);
            q.setParameter(2, createdBy);

            return (TofuSentenceTranslation) q.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        } catch (Exception e) {
            log.error("msg=" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @param countGlossEq kui -1, siis loendame kõik sagedused
     */
    public int countFrequencyList(int countGlossEq) {
        StringBuilder msg = new StringBuilder("countFrequencyList: countGlossEq=" + countGlossEq);

        Connection con = null;
        CallableStatement s = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "{? = call public.f_order_jp_by_freq_count(?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.INTEGER);
            s.setInt(2, countGlossEq);

            s.execute();
            result = s.getInt(1);

            log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(s, con);
        }

        return result;
    }

    public List<NresBase> getFrequencyList(int from, int to, int countGlossEq) {
        StringBuilder msg = new StringBuilder("getFrequencyList: from=" + from + ", to=" + to + ", countGlossEq=" + countGlossEq);

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<NresBase> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call public.f_order_jp_by_freq_paged(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setInt(2, from);
            s.setInt(3, to);
            s.setInt(4, countGlossEq);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            NresBase item;
            while (rs.next()) {
                item = new NresBase();

                item.setId(rs.getInt(1));
                item.setFreq(rs.getInt(2));
                item.setJp(rs.getString(3));
                item.setTypes(rs.getString(4));
                item.setCountGloss(rs.getInt(5));

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

    public List<Ilo> getIloWordsByKanjis(String kanjis, int compdlfrom, int compdlto) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<Ilo> result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call f_compd_ilo_by_kanji(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, kanjis);
            s.setInt(3, compdlfrom);
            s.setInt(4, compdlto);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            result = new ArrayList<>();

            while (rs.next()) {
                Ilo item = new Ilo();

                item.setId(rs.getInt("id"));
                item.setWordRomaji(rs.getString("word_romaji"));
                item.setWord(rs.getString("word"));
                item.setWordPos(rs.getString("word_pos"));
                item.setWordTranslation(rs.getString("word_translation"));

                item.setComment(rs.getString("comment"));
                item.setWordReading(rs.getString("word_reading"));
                item.setWithJmdict(rs.getBoolean("with_jmdict"));
                item.setWordKanjiCount(rs.getInt("kanji_count"));

                result.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Core6K> getCore6KWordsByKanjis(String kanjis, int compdlfrom, int compdlto) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<Core6K> result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call f_compd_core_6k_by_kanji(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, kanjis);
            s.setInt(3, compdlfrom);
            s.setInt(4, compdlto);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            result = new ArrayList<>();

            while (rs.next()) {
                Core6K item = new Core6K();

                item.setWord(rs.getString("word"));
                item.setWordReading(rs.getString("word_reading"));
                item.setWordFurigana(rs.getString("word_furigana"));
                item.setWordRomaji(rs.getString("word_romaji"));
                item.setWordTranslation(rs.getString("word_translation"));

                item.setWordPos(rs.getString("word_pos"));
                item.setWordAudio(rs.getString("word_audio"));
                item.setSentence(rs.getString("sentence"));
                item.setSentenceReading(rs.getString("sentence_reading"));
                item.setSentenceFurigana(rs.getString("sentence_furigana"));

                item.setSentenceRomaji(rs.getString("sentence_romaji"));
                item.setSentenceTranslation(rs.getString("sentence_translation"));
                item.setSentenceAudio(rs.getString("sentence_audio"));
                item.setSentencePicture(rs.getString("sentence_picture"));
                item.setId(rs.getInt("id"));

                item.setWithJmdict(rs.getBoolean("with_jmdict"));
                item.setWordKanjiCount(rs.getInt("kanji_count"));

                result.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Core10K> getCore10KWordsByKanjis(String kanjis, int compdlfrom, int compdlto) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<Core10K> result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call f_compd_core_10k_by_kanji(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, kanjis);
            s.setInt(3, compdlfrom);
            s.setInt(4, compdlto);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            result = new ArrayList<>();

            while (rs.next()) {
                Core10K item = new Core10K();

                item.setWord(rs.getString("word"));
                item.setWordReading(rs.getString("word_reading"));
                item.setSentence(rs.getString("sentence"));
                item.setSentenceAudio(rs.getString("sentence_audio"));
                item.setWordAudio(rs.getString("word_audio"));

                item.setWordTranslation(rs.getString("word_translation"));
                item.setMnemonic(rs.getString("mnemonic"));
                item.setMasterIndex(rs.getString("master_index"));
                item.setLevel(rs.getString("level"));
                item.setSentenceReading(rs.getString("sentence_reading"));

                item.setSentenceTranslation(rs.getString("sentence_translation"));
                item.setWordAltDef(rs.getString("word_alt_def"));
                item.setId(rs.getInt("id"));
                item.setWithJmdict(rs.getBoolean("with_jmdict"));
                item.setWordKanjiCount(rs.getInt("kanji_count"));

                result.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<TofuSentence> getTofuSentencesByKanjis(String kanjis, int compdlfrom, int compdlto) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<TofuSentence> result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call f_compd_tofu_by_kanji(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, kanjis);
            s.setInt(3, compdlfrom);
            s.setInt(4, compdlto);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            result = new ArrayList<>();

            while (rs.next()) {
                TofuSentence item = new TofuSentence();
                item.setId(rs.getInt("id"));
                item.setWord(rs.getString("word"));
                item.setSentence(rs.getString("sentence"));
                item.setWithJmdict(rs.getBoolean("with_jmdict"));
                item.setWordKanjiCount(rs.getInt("kanji_count"));

                result.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }
}
