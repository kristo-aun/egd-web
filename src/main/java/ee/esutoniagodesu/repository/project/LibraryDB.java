package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LibraryDB extends AbstractProjectRepository {

    public List<String> getAutocompleteTags(String tagstart, int limit) {
        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();

        try {
            con = dao.getConnection();
            String sql = "SELECT tag FROM library.reading_tags WHERE tag LIKE ? GROUP BY tag ORDER BY tag LIMIT ?";
            s = con.prepareStatement(sql);
            s.setString(1, tagstart + "%");
            s.setInt(2, limit);

            rs = s.executeQuery();

            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }
}
