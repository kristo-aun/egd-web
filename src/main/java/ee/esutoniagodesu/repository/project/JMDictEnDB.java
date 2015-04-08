package ee.esutoniagodesu.repository.project;

import com.googlecode.genericdao.search.Search;
import ee.esutoniagodesu.domain.jmdict_en.table.EN_Sens;
import ee.esutoniagodesu.domain.jmdict_en.view.EN_Essum;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JMDictEnDB extends AbstractProjectRepository {

    public EN_Sens getFirstSensByKanjAndRdng(String kanj, String rdng) {
        StringBuilder msg = new StringBuilder("getFirstSensByKanjAndRdng (en): kanj=" + kanj + ", rdng=" + rdng);
        Assert.isTrue(kanj != null && rdng != null);
        long ms = System.currentTimeMillis();

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_en_sens_by_kanj_and_rdng");
            query.setString(0, kanj);
            query.setString(1, rdng);
            query.setInteger(2, 1);
            EN_Sens result = (EN_Sens) query.uniqueResult();
            log.debug(msg.append(", result=").append(result).append(", time=").append(System.currentTimeMillis() - ms).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
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
            String sql = "select gloss from jmdict_en.esum where rdng=? and kanj=? limit 1";
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
