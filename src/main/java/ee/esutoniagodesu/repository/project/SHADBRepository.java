package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.SHAFile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SHADBRepository extends AbstractRepository {

    public SHADBRepository(JdbcTemplate jdbcShadb) {
        super(jdbcShadb);
    }

    public SHAFile findBySha256sum(String sha256sum) {

        String sql = "select sha256sum, length, mime, file_name, file_extension," +
            " created_by, created_date, last_modified_by, last_modified_date, file" +
            " from sha_file where sha256sum = ?";

        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement s = con.prepareStatement(this.sql);
                setString(s, 1, sha256sum);
                return s;
            }
        };

        PreparedStatementCallback<SHAFile> cb = s -> {
            ResultSet rs = s.executeQuery();

            SHAFile result = new SHAFile();

            while (rs.next()) {
                int i = 1;

                result.setSha256sum(rs.getString(i++));
                result.setLength(rs.getLong(i++));
                result.setMime(rs.getString(i++));
                result.setFileName(rs.getString(i++));
                result.setFileExtension(rs.getString(i++));
                result.setCreatedBy(rs.getString(i++));
                result.setCreatedDate(ZonedDateTime.of(rs.getTimestamp(i++).toLocalDateTime(), ZoneId.systemDefault()));
                result.setLastModifiedBy(rs.getString(i++));

                Timestamp ts = rs.getTimestamp(i++);
                if (!rs.wasNull()) {
                    result.setLastModifiedDate(ZonedDateTime.of(ts.toLocalDateTime(), ZoneId.systemDefault()));
                }

                try {
                    Path path = Paths.get(result.getSha256sum());
                    Files.copy(rs.getBinaryStream(i), path);
                    File file = path.toFile();
                    result.setFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return result;
        };
        return execute(sc, cb);
    }

    public void save(SHAFile shafile) {

        String sql = "insert into public.sha_file(file, sha256sum, length, mime, file_name, file_extension," +
            " created_by, created_date, last_modified_by, last_modified_date)" +
            " values(FILE_READ(?),?,?,?,?, ?,?,?,?,?)";

        CustomPreparedStatementCreator sc = new CustomPreparedStatementCreator(sql) {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement s = con.prepareStatement(this.sql);
                int i = 1;

                setString(s, i++, shafile.getFile().getAbsolutePath());
                setString(s, i++, shafile.getSha256sum());
                setLong(s, i++, shafile.getLength());
                setString(s, i++, shafile.getMime());
                setString(s, i++, shafile.getFileName());

                setString(s, i++, shafile.getFileExtension());
                setString(s, i++, shafile.getCreatedBy());
                setTimestamp(s, i++, Timestamp.from(shafile.getCreatedDate().toInstant()));
                setString(s, i++, shafile.getLastModifiedBy());
                setTimestamp(s, i, shafile.getLastModifiedDate() != null ? Timestamp.from(shafile.getLastModifiedDate().toInstant()) : null);

                return s;
            }
        };

        PreparedStatementCallback cb = PreparedStatement::execute;

        execute(sc, cb);
    }
}
