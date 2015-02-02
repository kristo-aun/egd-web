package ee.esutoniagodesu.repository.project;

import com.jc.util.JDBCUtil;
import ee.esutoniagodesu.pojo.dto.ArticleDTO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestRepository extends AbstractProjectRepository {

    public List<ArticleDTO> findUserArticles(String login) {
        StringBuilder msg = new StringBuilder("findUserArticles: login=" + login);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<ArticleDTO> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "SELECT * FROM test.article where created_by=? or shared = true ORDER BY id desc";

            s = con.prepareCall(sql);
            s.setString(1, login);
            rs = s.executeQuery();
            while (rs.next()) {
                ArticleDTO item = new ArticleDTO();

                item.setId(rs.getInt("id"));
                item.setTitle(rs.getString("title"));
                item.setAuthor(rs.getString("author"));
                item.setTranscriptLang(rs.getString("transcript_lang"));
                item.setCopyright(rs.getString("copyright"));
                item.setShared(rs.getBoolean("shared"));

                item.setCreatedBy(rs.getString("created_by"));
                item.setCreatedDate(new DateTime(rs.getTimestamp("created_date")));

                item.setLastModifiedBy(rs.getString("last_modified_by"));
                item.setLastModifiedDate(new DateTime(rs.getTimestamp("last_modified_date")));

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
}
