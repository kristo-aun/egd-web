package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.pojo.cf.ECfJpCategory;
import ee.esutoniagodesu.pojo.cf.ECfOrigin;
import ee.esutoniagodesu.pojo.entity.Voca;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhraseJpDB extends AbstractProjectRepository {

    public int getIdByPhrase(String phrase) {

        StringBuilder msg = new StringBuilder("getIdByPhrase: phrase=" + phrase);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select id from phrase_jp where jp=? or lower(romaji)=?";
            s = con.prepareCall(sql);
            s.setString(1, phrase);
            s.setString(2, phrase.toLowerCase());

            rs = s.executeQuery();
            rs.setFetchSize(50);
            if (rs.next()) {
                result = rs.getInt(1);
            }

            if (log.isDebugEnabled()) log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<String> getAutocomplete(String example, int limit) {
        StringBuilder msg = new StringBuilder("getAutocomplete: example=" + example + ", limit=" + limit);
        if (example == null || limit < 1) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
            String sql = "{? = call public.f_get_entr_jp_like(?,?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, example + "%");
            s.setInt(3, limit);
            s.execute();
            rs = (ResultSet) s.getObject(1);
            rs.setFetchSize(limit + 1);

            while (rs.next()) {
                String string = rs.getString(1);
                if (!result.contains(string))
                    result.add(string);
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

    public int countJp() {
        StringBuilder msg = new StringBuilder("countJp: ");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            con = dao.getConnection();
            String sql = "select count(*) from phrase_jp";
            s = con.prepareCall(sql);
            rs = s.executeQuery();
            if (rs.next()) result = rs.getInt(1);
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Voca> findJpOrderJpPaginationSmall(int page, int pageSize) {
        int rowFirst = (page * pageSize) - pageSize + 1;
        int rowLast = page * pageSize;//inclusive
        return findJpOrderJpPaginationSmall(rowFirst, rowLast, pageSize);
    }

    public List<Voca> findJpOrderJpPaginationSmall(int rowFirst, int rowLast, int fetchSize) {

        StringBuilder msg = new StringBuilder("findJpOrderJpPaginationSmall: rowFirst=" + rowFirst +
            ", rowLast=" + rowLast + ", fetchSize=" + fetchSize);

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
            String sql = "{? = call f_jp_order_jp_pagination_small_curs(?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setInt(2, rowFirst);
            s.setInt(3, rowLast);
            s.execute();
            rs = (ResultSet) s.getObject(1);
            rs.setFetchSize(10);
            Voca item;
            while (rs.next()) {

                int id = rs.getInt(1);
                String jp = rs.getString(2);
                String romaji = rs.getString(3);
                String kana = rs.getString(4);

                Array a = rs.getArray(5);
                Integer[] liikSimple = (Integer[]) a.getArray();

                item = new Voca();
                item.setId(id);
                item.setPhraseJpId(id);
                item.setJp(jp);
                item.setRomaji(romaji);
                item.setKana(kana);

                if (liikSimple != null && liikSimple.length > 0) {
                    String categories = "(";
                    for (int p : liikSimple) {
                        if (categories.length() != 1)
                            categories += ", ";
                        categories += ECfJpCategory.findById(p).ABBREVIATION;
                    }
                    categories += ")";
                    item.setCfJpCategories(categories);
                }

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

    public void recordSearch(String q, int resultSize, String lang) {
        StringBuilder msg = new StringBuilder("recordSearch: q=" + q + ", resultSize=" + resultSize);
        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        try {
            con = dao.getConnection();
            String sql = "insert into jp_search_hist(s_string, result_size, lang) values (?,?,?)";
            s = con.prepareCall(sql);
            s.setString(1, q);
            s.setInt(2, resultSize);
            s.setString(3, lang);
            s.execute();
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }
    }

    public Voca findPhraseJp(int phraseJpId) {
        StringBuilder msg = new StringBuilder("findPhraseJp: phraseJpId=" + phraseJpId);

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        Voca result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
            String sql = "{? = call f_phrase_jp(?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setInt(2, phraseJpId);
            s.execute();
            rs = (ResultSet) s.getObject(1);

            if (rs.next()) {
                int i = 1;

                result = new Voca();
                result.setPhraseJpId(phraseJpId);
                result.setJp(rs.getString(1));
                result.setKana(rs.getString(2));
                result.setRomaji(rs.getString(3));
                result.setRomajiHepburn(rs.getString(4));
                result.setJpAudioId(rs.getInt(5));
                result.setCfOriginJp(ECfOrigin.findById(rs.getInt(6)));
                Array z = rs.getArray(7);
                Integer[] sonaliikIds = (Integer[]) z.getArray();

                if (sonaliikIds != null && sonaliikIds.length > 0) {
                    String categories = "";
                    for (int p : sonaliikIds) {
                        if (categories.length() > 0)
                            categories += ", ";
                        categories += ECfJpCategory.findById(p).ABBREVIATION;
                    }
                    result.setCfJpCategories(categories);
                }
            }

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
}
