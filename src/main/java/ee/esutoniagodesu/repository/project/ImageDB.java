package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ImageDB extends AbstractProjectRepository {

	public byte[] findById(int imageId) {
		StringBuilder msg = new StringBuilder("findById: imageId=" + imageId);
		if (imageId < 1) throw new IllegalArgumentException(msg.toString());

		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		byte[] result = null;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			String sql = "SELECT image_file FROM image WHERE id=?";
			s = con.prepareStatement(sql);
			rs = s.executeQuery();
			if (rs.next()) result = rs.getBytes(1);
			Assert.isTrue(result != null);
			log.debug(msg.append(", result.length=").append(result.length)
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
