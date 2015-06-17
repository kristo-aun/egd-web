package ee.esutoniagodesu.repository.project;

import com.google.common.base.Joiner;
import ee.esutoniagodesu.domain.core.table.Core10K;
import ee.esutoniagodesu.domain.core.table.Core6K;
import ee.esutoniagodesu.domain.core.table.Ilo;
import ee.esutoniagodesu.domain.freq.table.NresBase;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CoreDB extends AbstractProjectRepository {

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
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("f_compd_core_6k_by_kanji");
        query.setParameter("kanjis", kanjis);
        query.setParameter("compdlfrom", compdlfrom);
        query.setParameter("compdlto", compdlto);
        query.execute();
        return (List<Core6K>) query.getOutputParameterValue("core_6k_by_kanji");
    }

    public List<Core10K> getCore10KWordsByKanjis(String kanjis, int compdlfrom, int compdlto) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("f_compd_core_10k_by_kanji");
        query.setParameter("kanjis", kanjis);
        query.setParameter("compdlfrom", compdlfrom);
        query.setParameter("compdlto", compdlto);
        query.execute();
        return (List<Core10K>) query.getOutputParameterValue("core_10k_by_kanji");
    }

    public List<NresBase> getFreqNresWordsByKanjis(List<Character> chars, int compdlfrom, int compdlto) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("f_compd_nres_by_kanji");
        query.setParameter("kanjis", Joiner.on(",").join(chars));
        query.setParameter("compdlfrom", compdlfrom);
        query.setParameter("compdlto", compdlto);
        query.execute();
        return (List<NresBase>) query.getOutputParameterValue("nres_by_kanji");
    }
}
