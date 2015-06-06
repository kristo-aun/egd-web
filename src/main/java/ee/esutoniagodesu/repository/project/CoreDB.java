package ee.esutoniagodesu.repository.project;

import com.google.common.base.Joiner;
import ee.esutoniagodesu.domain.core.table.Core10K;
import ee.esutoniagodesu.domain.core.table.Core6K;
import ee.esutoniagodesu.domain.core.table.Ilo;
import ee.esutoniagodesu.domain.freq.table.NresBase;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

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
        ResultSet rs = null;
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
            JDBCUtil.close(rs, s, con);
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

    public List<NresBase> getFreqNresWordsByKanjis(List<Character> chars, int cpdlen_from, int cpdlen_to) {
        StringBuilder msg = new StringBuilder("getFreqNresWordsByKanjis: chars=" + chars +
            ", cpdlen_from=" + cpdlen_from + ", cpdlen_to=" + cpdlen_to);

        if (chars.size() < 1 || cpdlen_from < 1 || cpdlen_to < cpdlen_from)
            throw new IllegalArgumentException(msg.toString());

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_compd_nres_by_kanji");
            query.setString(0, Joiner.on(",").join(chars));
            query.setInteger(1, cpdlen_from);
            query.setInteger(2, cpdlen_to);
            List<NresBase> result = query.list();

            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<Ilo> getIloWordsByKanjis(String chars, int cpdlen_from, int cpdlen_to) {
        StringBuilder msg = new StringBuilder("getIloWordsByKanjis: chars=" + chars +
            ", cpdlen_from=" + cpdlen_from + ", cpdlen_to=" + cpdlen_to);

        if (chars.length() < 1 || cpdlen_from < 1 || cpdlen_to < cpdlen_from)
            throw new IllegalArgumentException(msg.toString());

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_compd_ilo_by_kanji");
            query.setString(0, chars);
            query.setInteger(1, cpdlen_from);
            query.setInteger(2, cpdlen_to);
            List<Ilo> result = query.list();

            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<Core6K> getCore6KWordsByKanjis(String chars, int cpdlen_from, int cpdlen_to) {
        StringBuilder msg = new StringBuilder("getCore6KWordsByKanjis: chars=" + chars +
            ", cpdlen_from=" + cpdlen_from + ", cpdlen_to=" + cpdlen_to);

        if (chars.length() < 1 || cpdlen_from < 1 || cpdlen_to < cpdlen_from)
            throw new IllegalArgumentException(msg.toString());

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_compd_core_6k_by_kanji");
            query.setString(0, chars);
            query.setInteger(1, cpdlen_from);
            query.setInteger(2, cpdlen_to);
            List<Core6K> result = query.list();

            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<Core10K> getCore10KWordsByKanjis(String chars, int cpdlen_from, int cpdlen_to) {
        StringBuilder msg = new StringBuilder("getCore10KWordsByKanjis: chars=" + chars +
            ", cpdlen_from=" + cpdlen_from + ", cpdlen_to=" + cpdlen_to);

        if (chars.length() < 1 || cpdlen_from < 1 || cpdlen_to < cpdlen_from)
            throw new IllegalArgumentException(msg.toString());

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_compd_core_10k_by_kanji");
            query.setString(0, chars);
            query.setInteger(1, cpdlen_from);
            query.setInteger(2, cpdlen_to);
            List<Core10K> result = query.list();

            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }
}
