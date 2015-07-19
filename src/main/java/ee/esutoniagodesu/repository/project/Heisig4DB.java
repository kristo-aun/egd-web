package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.kanjidic2.table.Kanji;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Heisig4DB extends AbstractProjectRepository {

    public Character findKanjiByFrame(int frameNo) {
        StringBuilder msg = new StringBuilder("findKanjiByFrame: frameNo=" + frameNo);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Character result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            s = con.prepareCall("select kanji from heisig.heisig4 where id=?");
            s.setInt(1, frameNo);
            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getString(1).charAt(0);
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

    public List<Character> findKanjisByKeyword(String keyword) {
        StringBuilder msg = new StringBuilder("findKanjisByKeyword: keyword=" + keyword);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Character> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            s = con.prepareCall("select kanji from heisig.heisig4 where keyword_en=?");
            s.setString(1, keyword);
            rs = s.executeQuery();

            while (rs.next()) {
                result.add(rs.getString(1).charAt(0));
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

    public List<Kanji> getKanjisBetween(int frameFrom, int frameTo) {
        StringBuilder msg = new StringBuilder("getKanjisBetween: frameFrom=" + frameFrom + ", frameTo=" + frameTo);
        if (frameFrom < 1 || frameFrom > frameTo) throw new IllegalArgumentException(msg.toString());

        try {
            String sql = "SELECT a.* FROM kanjidic2.kanji a LEFT JOIN heisig.heisig4 b" +
                " ON a.literal = b.kanji " +
                " WHERE b.id BETWEEN ?1 AND ?2 ORDER BY b.id";
            Query q = em.createNativeQuery(sql, Kanji.class);
            q.setParameter(1, frameFrom);
            q.setParameter(2, frameTo);

            List<Kanji> result = q.getResultList();
            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }
}
