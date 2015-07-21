package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.pojo.cf.ECfEtSonaliik;
import ee.esutoniagodesu.pojo.cf.ECfJpCategory;
import ee.esutoniagodesu.domain.publik.table.EOrigin;
import ee.esutoniagodesu.pojo.cf.ECfVocaTransQuality;
import ee.esutoniagodesu.pojo.entity.Voca;
import ee.esutoniagodesu.util.persistence.JDBCUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * tegeleb tõlgetega, mitte sõnavaraga
 */
@Repository
public class VocaDB extends AbstractProjectRepository {

    public int insertVocaEt(Voca o) {
        StringBuilder msg = new StringBuilder("insertVocaEt: entity=" + o);

        if (o == null ||
            o.getEt() == null ||
            o.getCfEtSonaliik() == null ||
            o.getCfOriginEt() == null)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_ins_et(?,?,?,?,?)}";

            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);

            s.setString(2, o.getEt());
            s.setInt(3, o.getCfEtSonaliik().ID);
            s.setString(4, o.getDescrEt());
            if (o.getEtAudioId() > 0) s.setInt(5, o.getEtAudioId());
            else s.setNull(5, Types.INTEGER);
            s.setString(6, o.getCfOriginEt().name());

            s.execute();
            result = s.getInt(1);//voca_et_id

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

    /**
     * Lisab tõlke suunal JP -> ET
     */
    public int safeInsertJpToEt(Voca o) {
        StringBuilder msg = new StringBuilder("safeInsertJpToEt: entity=" + o);

        if (o == null ||
            o.getJp() == null ||
            o.getJp().length() < 1 ||
            o.getCfOriginJp() == null ||
            o.getCfOriginEt() == null ||
            o.getCfVocaTransQuality() == null ||
            o.getCfJpCategory() == null ||
            o.getEt() == null ||
            o.getEt().length() < 1 ||
            o.getCfEtSonaliik() == null)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        int result = 0;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_ins_jp_to_et(?,?,?,?,?, ?,?,?,?,?, ?,?)}";

            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);

            s.setString(2, o.getJp());
            JDBCUtil.setCSParameter(s, 3, o.getKana(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 4, o.getRomaji(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 5, o.getJpAudioId() > 0 ? o.getJpAudioId() : null, Types.INTEGER);
            s.setString(6, o.getCfOriginJp().name());

            s.setInt(7, o.getCfJpCategory().ID);
            s.setString(8, o.getEt());
            JDBCUtil.setCSParameter(s, 9, o.getEtAudioId() > 0 ? o.getEtAudioId() : null, Types.INTEGER);
            s.setInt(10, o.getCfEtSonaliik().ID);
            s.setInt(11, o.getCfVocaTransQuality().ID);

            JDBCUtil.setCSParameter(s, 12, o.getDescrEt(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 13, o.getDescrJp(), Types.VARCHAR);

            s.execute();
            result = s.getInt(1);

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

    /**
     * Lisab tõlke suunal ET -> JP
     * Vahe on selles, mis väärtuse saavad
     * mtm_voca.sec_et_jp
     */
    public boolean safeInsertEtToJp(Voca o) {
        StringBuilder msg = new StringBuilder("safeInsertEtToJp: entity=" + o);

        if (o == null ||
            o.getEt() == null ||
            o.getEt().length() < 1 ||
            o.getCfOriginEt() == null ||
            o.getCfOriginJp() == null ||
            o.getCfVocaTransQuality() == null ||
            o.getCfEtSonaliik() == null ||
            o.getJp() == null ||
            o.getJp().length() < 1 ||
            o.getCfJpCategory() == null)
            throw new IllegalArgumentException(msg.toString());

        Connection con = null;
        CallableStatement s = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();
            String sql = "{? = call f_ins_et_to_jp(?,?,?,?,?, ?,?,?,?,?, ?,?)}";

            s = con.prepareCall(sql);
            s.registerOutParameter(1, Types.INTEGER);

            s.setString(2, o.getJp());
            JDBCUtil.setCSParameter(s, 3, o.getKana(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 4, o.getRomaji(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 5, o.getJpAudioId() > 0 ? o.getJpAudioId() : null, Types.INTEGER);
            s.setString(6, o.getCfOriginJp().name());

            s.setInt(7, o.getCfJpCategory().ID);
            s.setString(8, o.getEt());
            JDBCUtil.setCSParameter(s, 9, o.getEtAudioId() > 0 ? o.getEtAudioId() : null, Types.INTEGER);
            s.setInt(10, o.getCfEtSonaliik().ID);
            s.setInt(11, o.getCfVocaTransQuality().ID);

            JDBCUtil.setCSParameter(s, 12, o.getDescrEt(), Types.VARCHAR);
            JDBCUtil.setCSParameter(s, 13, o.getDescrJp(), Types.VARCHAR);

            s.execute();
            result = s.getInt(1) > 0;

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

    public boolean updateMtmVoca(Voca v) {
        throw new RuntimeException("not implemented");
    }

    private static Voca resolveVJpEt(final ResultSet rs) throws SQLException {
        Voca item = new Voca();
        int i = 1;

        item.setEt(rs.getString(i++));//et
        item.setVocaEtId(rs.getInt(i++));//voca_et_id
        item.setCfEtSonaliik(ECfEtSonaliik.findById(rs.getInt(i++)));//cf_et_sonaliik
        item.setDescrEt(rs.getString(i++));//descr_for_et
        item.setCfOriginEt(EOrigin.valueOf(rs.getString(i++)));//cf_origin_for_phrase_et

        item.setEtAudioId(rs.getInt(i++));//et_audio_id
        item.setPhraseEtId(rs.getInt(i++));//phrase_et_id
        item.setCfVocaTransQuality(ECfVocaTransQuality.findById(rs.getInt(i++)));//cf_voca_trans_quality
        item.setJp(rs.getString(i++));//jp
        item.setKana(rs.getString(i++));//kana

        item.setRomaji(rs.getString(i++));//romaji
        item.setRomajiHepburn(rs.getString(i++));//romaji_hepburn
        item.setPhraseJpId(rs.getInt(i++));//phrase_jp_id
        item.setCfOriginJp(EOrigin.valueOf(rs.getString(i++)));//cf_origin_for_phrase_jp
        item.setCfJpCategory(ECfJpCategory.findById(rs.getInt(i)));//cf_jp_category

        return item;
    }

    public List<Voca> translatePhraseJp(int phraseJpId) {
        StringBuilder msg = new StringBuilder("translatePhraseJp: phraseJpId=" + phraseJpId);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_jp_et where phrase_jp_id=? ORDER BY seq_jp_et";

            s = con.prepareCall(sql);
            s.setInt(1, phraseJpId);
            rs = s.executeQuery();
            while (rs.next()) result.add(resolveVJpEt(rs));

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

    public List<Voca> translatePhraseJp(String jp) {
        StringBuilder msg = new StringBuilder("translatePhraseJp: jp=" + jp);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_jp_et where jp=? ORDER BY seq_jp_et";

            s = con.prepareCall(sql);
            s.setString(1, jp);
            rs = s.executeQuery();
            while (rs.next()) result.add(resolveVJpEt(rs));

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

    public List<Voca> translatePhraseRomaji(String romaji) {
        StringBuilder msg = new StringBuilder("translatePhraseRomaji: romaji=" + romaji);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_jp_et where lower(romaji)=? or lower(romaji_hepburn)=? ORDER BY seq_jp_et";

            s = con.prepareCall(sql);
            s.setString(1, romaji.toLowerCase());
            s.setString(2, romaji.toLowerCase());
            rs = s.executeQuery();

            while (rs.next()) result.add(resolveVJpEt(rs));

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

    private static Voca resolveVEtJp(final ResultSet rs) throws SQLException {
        Voca item = new Voca();
        int i = 1;

        item.setJp(rs.getString(i++));//jp
        item.setCfJpCategory(ECfJpCategory.findById(rs.getInt(i++)));//cf_jp_category
        item.setKana(rs.getString(i++));//kana
        item.setRomaji(rs.getString(i++));//romaji
        item.setRomajiHepburn(rs.getString(i++));//romaji_hepburn

        item.setDescrJp(rs.getString(i++));//descr_for_jp
        item.setCfOriginJp(EOrigin.valueOf(rs.getString(i++)));//cf_origin_for_phrase_jp
        item.setJpAudioId(rs.getInt(i++));//jp_audio_id
        item.setVocaJpId(rs.getInt(i++));//voca_jp_id
        item.setPhraseJpId(rs.getInt(i++));//phrase_jp_id

        item.setCfVocaTransQuality(ECfVocaTransQuality.findById(rs.getInt(i++)));//cf_voca_trans_quality
        i++;//seq_et_jp
        item.setVocaEtId(rs.getInt(i++));//voca_et_id
        item.setEt(rs.getString(i++));//et
        item.setCfOriginEt(EOrigin.valueOf(rs.getString(i++)));//cf_origin_for_phrase_et

        item.setEtAudioId(rs.getInt(i++));//et_audio_id
        item.setPhraseEtId(rs.getInt(i));//phrase_et_id

        return item;
    }

    public List<Voca> translatePhraseEt(int phraseEtId) {
        StringBuilder msg = new StringBuilder("translatePhraseEt: phraseEtId=" + phraseEtId);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_et_jp where phrase_et_id=? ORDER BY seq_et_jp";

            s = con.prepareCall(sql);
            s.setInt(1, phraseEtId);
            rs = s.executeQuery();
            while (rs.next()) result.add(resolveVEtJp(rs));

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

    public List<Voca> translatePhraseEt(String example) {
        StringBuilder msg = new StringBuilder("translatePhraseEt: example=" + example);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Voca> result = new ArrayList<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "select * from v_et_jp where lower(et)=? ORDER BY seq_et_jp";

            s = con.prepareCall(sql);
            s.setString(1, example.toLowerCase());
            rs = s.executeQuery();

            while (rs.next()) result.add(resolveVEtJp(rs));

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

    public Map<Integer, String> getSonaliigid(String et) {
        StringBuilder msg = new StringBuilder("getSonaliigid: et=" + et);

        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Map<Integer, String> result = new LinkedHashMap<>();

        try {
            long ms = System.currentTimeMillis();
            con = dao.getConnection();

            String sql = "SELECT cf_et_sonaliik, title FROM v_et_with_sonaliik WHERE et=?";

            s = con.prepareCall(sql);
            s.setString(1, et);
            rs = s.executeQuery();

            while (rs.next()) {
                result.put(rs.getInt(1), rs.getString(2));
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
