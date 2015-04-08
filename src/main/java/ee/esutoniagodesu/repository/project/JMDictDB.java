package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.jmdict.table.Entr;
import ee.esutoniagodesu.domain.jmdict.table.Sens;
import ee.esutoniagodesu.pojo.entity.JapEst;
import ee.esutoniagodesu.util.JCString;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JMDictDB extends AbstractProjectRepository {

    public Entr getEntrById(int entrId) {
        StringBuilder msg = new StringBuilder("getEntrById: entrId=" + entrId);
        if (entrId < 1) throw new IllegalArgumentException(msg.toString());
        long ms = System.currentTimeMillis();
        Entr result = dao.find(Entr.class, entrId);
        log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        return result;
    }

    public Sens getFirstSensByKanjAndRdng(String kanj, String rdng) {
        StringBuilder msg = new StringBuilder("getFirstSensByKanjAndRdng: kanj=" + kanj + ", rdng=" + rdng);
        Assert.isTrue(kanj != null && rdng != null);
        long ms = System.currentTimeMillis();

        try {
            Query query = null;//dao.getSession().getNamedQuery("f_sens_by_kanj_and_rdng");
            query.setString(0, kanj);
            query.setString(1, rdng);
            query.setInteger(2, 1);
            Sens result = (Sens) query.uniqueResult();
            log.debug(msg.append(", result=").append(result).append(", time=").append(System.currentTimeMillis() - ms).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }

    public List<Integer> findEntrIdsFromRkValidity(String kanj, String rdng) {
        StringBuilder msg = new StringBuilder("findEntrIdsFromRkValidity: kanj=" + kanj + ", rdng=" + rdng);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            StringBuilder sql = new StringBuilder("SELECT id FROM jmdict.rk_validity WHERE ktxt");
            if (kanj != null)
                sql.append("='").append(kanj).append("'");
            else sql.append(" is null");

            sql.append(" and rtxt='").append(rdng).append("'");
            s = con.prepareStatement(sql.toString());

            rs = s.executeQuery();

            while (rs.next()) {
                int entr = rs.getInt(1);
                result.add(entr);
            }

            if (result.size() < 1)
                log.debug(sql.toString());

            log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public List<Integer> findEntrIdsFromGloss(String et) {
        StringBuilder msg = new StringBuilder("findEntrIdsFromGloss: et=" + et);
        if (et == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            s = con.prepareStatement("SELECT entr FROM jmdict.gloss WHERE txt=? ORDER BY sens, gloss");
            s.setString(1, et);
            rs = s.executeQuery();

            while (rs.next()) {
                int entr = rs.getInt(1);
                result.add(entr);
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

    public List<Integer> findEntrIdsByKanjOrKana(String kanjiOrKana) {
        List<Integer> entrids = findEntrIdsFromEsum(kanjiOrKana, false);
        if (entrids.size() < 1) {
            entrids = findEntrIdsFromEsum(kanjiOrKana, true);
        }
        return entrids;
    }

    public List<Integer> findEntrIdsFromEsum(String jp, boolean kana) {
        StringBuilder msg = new StringBuilder("findEntrIdsFromEsum: jp=" + jp + ", kana=" + kana);
        if (jp == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = kana ? "SELECT id FROM jmdict.esum WHERE rdng=?" : "SELECT id FROM jmdict.esum WHERE kanj=?";
            s = con.prepareStatement(sql);
            s.setString(1, jp);
            rs = s.executeQuery();

            while (rs.next()) {
                int entr = rs.getInt(1);
                result.add(entr);
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

    public List<Integer> findEntrIdsFromEsum(String kanj, String rdng) {
        StringBuilder msg = new StringBuilder("findEntrIdsFromEsum: kanj=" + kanj + ", rdng=" + rdng);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            StringBuilder sql = new StringBuilder("SELECT id FROM jmdict.esum WHERE kanj");
            if (kanj != null)
                sql.append("='").append(kanj).append("'");
            else sql.append(" is null");

            sql.append(" and rdng='").append(rdng).append("'");
            s = con.prepareStatement(sql.toString());

            rs = s.executeQuery();

            while (rs.next()) {
                int entr = rs.getInt(1);
                result.add(entr);
            }

            if (result.size() > 1)
                log.debug(sql.toString());

            log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
    }

    public int getSensIdFromJMDictEN(String kanj, String rdng, String enGloss) {
        if (kanj.equals(rdng)) kanj = null;
        StringBuilder msg = new StringBuilder("getSensIdFromJMDictEN: kanj=" + kanj + ", rdng=" + rdng + ", enGloss=" + enGloss);
        if (rdng == null || enGloss == null) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "SELECT sens FROM jmdict_en.gloss WHERE txt=? AND" +
                " entr=(SELECT id FROM jmdict_en.essum a WHERE a.kanj=? AND a.rdng=? LIMIT 1)";

            s = con.prepareStatement(sql);

            s.setString(1, enGloss);
            if (kanj != null) s.setString(2, kanj);
            else s.setNull(2, Types.VARCHAR);
            s.setString(3, rdng);

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

    public List<Integer> getSensIdsFromJMDictET(String kanj, String rdng) {
        if (kanj.equals(rdng)) kanj = null;
        StringBuilder msg = new StringBuilder("getSensIdsFromJMDictET: kanj=" + kanj + ", rdng=" + rdng);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            StringBuilder sql = new StringBuilder("SELECT sens FROM jmdict.essum WHERE kanj");
            if (kanj != null)
                sql.append("='").append(kanj).append("'");
            else sql.append(" is null");

            sql.append(" and rdng='").append(rdng).append("' and gloss is not null");

            s = con.prepareStatement(sql.toString());

            rs = s.executeQuery();

            while (rs.next()) {
                int sens = rs.getInt(1);
                result.add(sens);
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

    public int countSens(int entr) {
        StringBuilder msg = new StringBuilder("countSens: entr=" + entr);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = -1;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select count(*) from jmdict.sens where entr=?";
            s = con.prepareStatement(sql);
            s.setInt(1, entr);
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

    public void insertGloss(int entr, int sens, int gloss, String txt, String etInf, String jpInf, String jatikId, int cfOrigin) {
        StringBuilder msg = new StringBuilder("insertGloss: entr=" + entr +
            ", sens=" + sens +
            ", gloss=" + gloss +
            ", txt=" + txt +
            ", etInf=" + etInf +
            ", jpInf=" + jpInf +
            ", jatikId=" + jatikId +
            ", cfOrigin=" + cfOrigin);

        if (entr < 1 || sens < 1 || gloss < 1 || txt == null || txt.trim().length() < 1 || cfOrigin < 1)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{call f_ins_jmdict_et_gloss(?,?,?,?,?, ?,?,?)}";
            s = con.prepareCall(sql);

            s.setInt(1, entr);
            s.setInt(2, sens);
            s.setInt(3, gloss);
            s.setString(4, txt);
            s.setString(5, etInf);

            s.setString(6, jpInf);
            s.setInt(7, cfOrigin);
            s.setString(8, jatikId);

            s.execute();
            log.debug(msg.append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(s, con);
        }

    }

    public boolean duplicateGloss(int entr, String txt) {
        StringBuilder msg = new StringBuilder("duplicateGloss: entr=" + entr + ", txt=" + txt);
        if (entr < 1 || txt.trim().length() < 1)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select count(*) from jmdict.gloss where entr=? and txt=?";
            s = con.prepareStatement(sql);
            s.setInt(1, entr);
            s.setString(2, txt);

            rs = s.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1) > 0;
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

    public JapEst getEntryDataForJapEst(int entr) {
        StringBuilder msg = new StringBuilder("getEntryDataForJapEst: entr=" + entr);
        if (entr < 1) throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        JapEst result = null;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            con.setAutoCommit(false);
            String sql = "{? = call f_entr_data_for_japest(?)}";
            s = con.prepareCall(sql);

            s.registerOutParameter(1, Types.OTHER);//cursor
            s.setInt(2, entr);
            s.execute();
            rs = (ResultSet) s.getObject(1);

            if (rs.next()) {
                result = new JapEst();
                result.entr = entr;
                String kanjs = rs.getString(1);
                String rdngs = rs.getString(2);
                String senses = rs.getString(3);

                if (kanjs != null) {
                    result.kanj = JCString.trim(kanjs.split(";"));
                }
                if (rdngs != null) {
                    result.rdng = JCString.trim(rdngs.split(";"));
                }
                if (senses != null) {
                    String[] sarr = JCString.trim(senses.split("/"));

                    for (String p : sarr) {
                        result.sens.add(new JapEst.Sens(p));
                    }
                }
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

    public List<String> getGlossByEntr(int entr) {
        StringBuilder msg = new StringBuilder("getGlossByEntr: entr=" + entr);
        if (entr < 1)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "select txt from jmdict.gloss where entr=? order by sens, gloss";
            s = con.prepareStatement(sql);
            s.setInt(1, entr);

            rs = s.executeQuery();

            while (rs.next()) {
                result.add(rs.getString(1));
            }

            if (log.isDebugEnabled()) log.debug(msg.append(", result.size=").append(result.size())
                .append(", time=").append(System.currentTimeMillis() - ms).toString());
        } catch (SQLException e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, s, con);
        }

        return result;
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
            String sql = "select gloss from jmdict.esum where rdng=? and kanj=?";
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
