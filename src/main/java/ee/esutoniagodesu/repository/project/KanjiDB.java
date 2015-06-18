package ee.esutoniagodesu.repository.project;

import com.google.common.base.Joiner;
import com.googlecode.genericdao.search.Search;
import ee.esutoniagodesu.domain.kanjidic2.table.Kanji;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class KanjiDB extends AbstractProjectRepository {

    public Map.Entry<String, byte[]> getStrokeDiagram(char kanji) {
        StringBuilder msg = new StringBuilder("getStrokeDiagram: kanji=" + kanji);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Map.Entry<String, byte[]> result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select file_name, image_file from v_kanji_stroke_diagrams WHERE kanji=?";
            s = con.prepareStatement(sql);
            s.setString(1, String.valueOf(kanji));
            rs = s.executeQuery();
            byte[] bytes;
            String fileName;
            if (rs.next()) {
                fileName = rs.getString(1);
                bytes = rs.getBytes(2);
                result = new AbstractMap.SimpleEntry<>(fileName, bytes);
            }
            log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public int getStrokeDiagramImageId(char kanji) {
        StringBuilder msg = new StringBuilder("getStrokeDiagramImageId: kanji=" + kanji);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select id from v_kanji_stroke_diagrams WHERE kanji=?";
            s = con.prepareStatement(sql);
            s.setString(1, String.valueOf(kanji));
            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
            log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public String getKanjisWithoutStrokeDiagram() {
        StringBuilder msg = new StringBuilder("getKanjisWithoutStrokeDiagram");

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        String result = null;

        try {
            con = dao.getConnection();
            String sql = "SELECT array_to_string(ARRAY(SELECT kanji FROM kanji WHERE stroke_image_id is null), ',')";
            s = con.prepareStatement(sql);
            rs = s.executeQuery();
            if (rs.next()) result = rs.getString(1);
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public int insUpdStrokeDiagram(String kanji, Map.Entry<String, byte[]> imageFile, String copyright) {
        StringBuilder msg = new StringBuilder("insertImage: kanji=" + kanji);
        if (imageFile == null || copyright == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        int result = -1;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_ins_upd_kanji_stroke_image(?,?,?,?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);
            s.setString(2, kanji);

            s.setString(3, imageFile.getKey());

            InputStream istream = new ByteArrayInputStream(imageFile.getValue());

            s.setBinaryStream(4, istream, imageFile.getValue().length);

            s.setString(5, copyright);
            s.execute();

            result = s.getInt(1);
            istream.close();

            log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public boolean containsKanji(char kanji) {
        StringBuilder msg = new StringBuilder("containsKanji: kanji=" + kanji);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            con = dao.getConnection();
            String sql = "SELECT id from kanjidic2.kanji where literal=?";
            s = con.prepareStatement(sql);
            s.setString(1, String.valueOf(kanji));
            rs = s.executeQuery();
            if (rs.next()) result = true;
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<String[]> getExampleWords(String kanji, int countExamples) {
        StringBuilder msg = new StringBuilder("getExampleWords: kanji=" + kanji + ", countExamples=" + countExamples);
        if (kanji == null || kanji.length() != 1) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<String[]> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_en_examples_by_kanj_freq(?,?)}";
            con.setAutoCommit(false);
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, "%" + kanji + "%");
            s.setInt(3, countExamples);

            s.execute();

            rs = (ResultSet) s.getObject(1);

            String[] item;
            while (rs.next()) {
                item = new String[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
                };
                result.add(item);
            }
            log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    /**
     * Leiab märkide unicode koodid ja võrdleb vastu kanjidic2 registrit.
     *
     * @return mitu kanjidic2 registris sisalduvat kanjit sisaldab antud sõna.
     */
    public int countKanjidic2(String japanese) {
        StringBuilder msg = new StringBuilder("countKanjidic2: japanese=" + japanese);
        if (japanese == null || japanese.length() < 1) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        int result = 0;

        List<Integer> codepoints = new ArrayList<>();

        for (int i = 0; i < japanese.length(); i++) {
            codepoints.add(japanese.codePointAt(i));
        }

        String cps = Joiner.on(",").join(codepoints);

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select id from kanjidic2.kanji where ucp_dec in (" + cps + ")";
            s = con.prepareCall(sql);

            rs = s.executeQuery();

            while (rs.next()) {
                result++;
            }

            if (log.isDebugEnabled()) log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    /**
     * Koostab nimekirja kanji primitiividest ja lisab primitiivide tabelist suvalisi juurde.
     * Näiteks kui kanji koosneb 3 primitiivist,
     * siis kokku sisaldab vihje 3 * 2 + (10 - (3 * 2 % 10)) = 10 primitiivi.
     * Kui primitiive on 12, siis 30tk jne.
     * Korruta primitiivide arv 2-ga ja suurenda lähima kümnega jaguva arvuni.
     * <p/>
     * kanjidic2.kanji.literal sisaldab 1 - 14 radikaali
     */
    public String getPrimitiveHint(char kanji, String delimiter) {
        StringBuilder msg = new StringBuilder("getPrimitiveHint: kanji=" + kanji + ", delimiter=" + delimiter);
        if (delimiter == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        String result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call kanjidic2.f_kanji_radical_hint(?,?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.VARCHAR);
            s.setString(2, String.valueOf(kanji));
            s.setString(3, delimiter);

            s.execute();

            result = s.getString(1);

            if (log.isDebugEnabled()) log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public int getStrokeCount(char kanji) {
        StringBuilder msg = new StringBuilder("getStrokeCount: kanji=" + kanji);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select stroke_count from kanjidic2.kanji where literal=?";
            s = con.prepareCall(sql);
            s.setString(1, String.valueOf(kanji));

            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }

            if (log.isDebugEnabled()) log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public String getFirstReading(String jp) {
        StringBuilder msg = new StringBuilder("getFirstReading: jp=" + jp);
        if (jp == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        String result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select rtxt from jmdict.rk_valid where rdng=1 and ktxt=?";
            s = con.prepareCall(sql);
            s.setString(1, jp);

            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            }

            if (log.isDebugEnabled()) log.debug(msg.append(", result=").append(result)
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Kanji> getHeisig6KanjisByIndex(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN heisig.heisig6 b" +
            " ON a.literal = b.kanji " +
            " WHERE b.id BETWEEN ?1 AND ?2 ORDER BY b.id";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getHeisig6KanjisByLesson(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN heisig.heisig6 b" +
            " ON a.literal = b.kanji " +
            " WHERE b.lesson_no BETWEEN ?1 AND ?2 ORDER BY b.id";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getHeisig4KanjisByIndex(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN heisig.heisig4 b" +
            " ON a.literal = b.kanji " +
            " WHERE b.id BETWEEN ?1 AND ?2 ORDER BY b.id";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getHeisig4KanjisByLesson(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN heisig.heisig4 b" +
            " ON a.literal = b.kanji " +
            " WHERE b.lesson_no BETWEEN ?1 AND ?2 ORDER BY b.id";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getJLPTKanjisByLevel(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a WHERE a.jlpt BETWEEN ?1 AND ?2 ORDER BY a.jlpt, a.freq";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getGradeKanjisByLevel(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a WHERE a.grade BETWEEN ?1 AND ?2 ORDER BY a.grade, a.freq";
        return sqlToKanjis(sql, from, to);
    }

    public List<Kanji> getJouyouKanjisByGrade(int from, int to) {
        String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN kanjidic2.jouyou b" +
            " ON a.id = b.kanji_id " +
            " WHERE b.grade BETWEEN ?1 AND ?2 ORDER BY b.grade, b.gradeSeq";
        return sqlToKanjis(sql, from, to);
    }

    private List<Kanji> sqlToKanjis(String sql_ft, int from, int to) {
        StringBuilder msg = new StringBuilder("sqlToKanjis: from=" + from + ", to=" + to + ", sql_ft=" + sql_ft);
        if (from < 1 || from > to) throw new IllegalArgumentException(msg.toString());

        try {
            Query q = em.createNativeQuery(sql_ft, Kanji.class);
            q.setParameter(1, from);
            q.setParameter(2, to);
            List<Kanji> result = q.getResultList();

            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public Kanji findByKanji(char kanji) {
        StringBuilder msg = new StringBuilder("findByKanji: kanji=" + kanji);
        try {
            Search search = new Search(Kanji.class);
            search.addFilterEqual("literal", kanji);
            Kanji result = (Kanji) dao.search(search).iterator().next();
            log.debug(msg.append(", result.id=").append(result.getId()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<String> getKanjiReadingsJp(char kanji) {
        StringBuilder msg = new StringBuilder("getKanjiReadingsJp: kanji=" + kanji);

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call kanjidic2.f_kanji_readings_jp(?)}";
            con.setAutoCommit(false);
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setString(2, String.valueOf(kanji));

            s.execute();

            rs = (ResultSet) s.getObject(1);

            while (rs.next()) {
                result.add(rs.getString(1));
            }
            log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }
}
