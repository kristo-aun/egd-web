package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;

@Repository
public class BinaryFileDB extends AbstractProjectRepository {

    public int insUpdReport(Map.Entry<String, byte[]> imageFile, String mime, int cfReportType) {
        StringBuilder msg = new StringBuilder("insUpdReport: ");
        if (cfReportType < 1 || imageFile == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        int result = -1;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_ins_upd_report_file(?,?,?,?)}";
            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);//id
            InputStream istream = new ByteArrayInputStream(imageFile.getValue());

            s.setInt(2, cfReportType);
            s.setString(3, imageFile.getKey());//file_name
            s.setBinaryStream(4, istream, imageFile.getValue().length);
            s.setString(5, mime);

            s.execute();
            result = s.getInt(1);//bfile.id
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
}
