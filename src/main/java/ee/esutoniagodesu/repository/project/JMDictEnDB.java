package ee.esutoniagodesu.repository.project;

import com.googlecode.genericdao.search.Search;
import ee.esutoniagodesu.domain.jmen.pk.EN_SensPK;
import ee.esutoniagodesu.domain.jmen.table.EN_Sens;
import ee.esutoniagodesu.domain.jmen.view.EN_Essum;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class JMDictEnDB extends AbstractProjectRepository {

    public EN_Sens getFirstSensByKanjAndRdng(String kanj, String rdng) {

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        EN_Sens result = null;

        try {
            con = dao.getConnection();
            con.setAutoCommit(false);

            String sql = "{? = call f_en_sens_by_kanj_and_rdng(?,?,?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor

            s.setString(2, kanj);
            s.setString(3, rdng);
            s.setInt(4, 1);

            s.execute();
            rs = (ResultSet) s.getObject(1);

            if (rs.next()) {
                EN_SensPK pk = new EN_SensPK(rs.getInt(1), rs.getShort(2));

                result = dao.find(EN_Sens.class, pk);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<EN_Essum> getEssumByKanj(String kanj) {
        StringBuilder msg = new StringBuilder("getEssumByKanj: kanj=" + kanj);
        if (kanj == null) throw new IllegalArgumentException(msg.toString());

        try {
            Search search = new Search(EN_Essum.class);
            search.addFilterEqual("kanj", kanj);
            List<EN_Essum> result = dao.search(search);
            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<EN_Essum> getEssumByKanjAndRdng(String kanj, String rdng) {
        StringBuilder msg = new StringBuilder("getEssumByKanjAndRdng: kanj=" + kanj + ", rdng=" + rdng);
        if (kanj == null || rdng == null) throw new IllegalArgumentException(msg.toString());

        try {
            Search search = new Search(EN_Essum.class);
            search.addFilterEqual("kanj", kanj);
            search.addFilterEqual("rdng", rdng);
            List<EN_Essum> result = dao.search(search);
            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public String getGlossByRdngAndKanj(String rdng, String kanj) {
        StringBuilder msg = new StringBuilder("getGlossByRdngAndKanj: rdng=" + rdng + ", kanj=" + kanj);
        if (rdng == null)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        String result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "SELECT gloss FROM jmdict_en.esum WHERE rdng=? AND kanj=? LIMIT 1";
            s = con.prepareStatement(sql);
            s.setString(1, rdng);
            s.setString(2, kanj);

            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
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
}
