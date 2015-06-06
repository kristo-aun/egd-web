package ee.esutoniagodesu.repository.project;

import com.googlecode.genericdao.search.Search;
import ee.esutoniagodesu.domain.heisig.view.VHeisig6Custom;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Heisig6DB extends AbstractProjectRepository {

    public VHeisig6Custom findHeisig6ByKanji(char kanji) {
        StringBuilder msg = new StringBuilder("findHeisig6ByKanji: kanji=" + kanji);
        try {
            Search search = new Search(VHeisig6Custom.class);
            search.addFilterEqual("kanji", kanji);
            VHeisig6Custom result = (VHeisig6Custom) dao.search(search).iterator().next();
            log.debug(msg.append(", result.id=").append(result.getId()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public Character findKanjiByFrame(int frameNo) {
        StringBuilder msg = new StringBuilder("findKanjiByFrame: frameNo=" + frameNo);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Character result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            s = con.prepareCall("select kanji from heisig.heisig6 where id=?");
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

            s = con.prepareCall("select kanji from heisig.heisig6 where keyword_en=? or keyword_et=?");
            s.setString(1, keyword);
            s.setString(2, keyword);
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
}
