package ee.esutoniagodesu.repository.project;

import com.jc.util.JDBCUtil;
import ee.esutoniagodesu.pojo.cf.ECfEtSonaliik;
import ee.esutoniagodesu.pojo.cf.ECfOrigin;
import ee.esutoniagodesu.pojo.entity.Voca;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PhraseEtDB extends AbstractProjectRepository {

	public List<String> getIdAndEtByRightOpen(String example, int limit) {
		StringBuilder msg = new StringBuilder("getIdAndEtByRightOpen: example=" + example);
		if (example == null || limit < 1) throw new IllegalArgumentException(msg.toString());

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
        List<String> result = new ArrayList<>();

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
			String sql = "{? = call f_get_entr_et_like(?,?)}";
			s = con.prepareCall(sql);
			s.registerOutParameter(1, Types.OTHER);//cursor
			s.setString(2, example + "%");
			s.setInt(3, limit);
			s.execute();
			rs = (ResultSet) s.getObject(1);

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

	public Map<Integer, String> getIdAndEtByRightOpen_2(String example) {

		StringBuilder msg = new StringBuilder("getIdAndEtByRightOpen: example=" + example);

		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		Map<Integer, String> result = new LinkedHashMap<>();

		try {

			long ms = System.currentTimeMillis();
			con = dao.getConnection();

			String sql = "SELECT entr, txt FROM jmdict.gloss WHERE upper(txt) LIKE upper(?) ORDER BY txt COLLATE \"estonian\"";

			s = con.prepareCall(sql);
			s.setString(1, example + "%");
			s.setFetchSize(50);
			rs = s.executeQuery();

			while (rs.next()) {
				result.put(rs.getInt(1), rs.getString(2));
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


	public boolean exists(String phrase) {
		StringBuilder msg = new StringBuilder("getIdByPhrase: phrase=" + phrase);

		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();

			String sql = "select id from phrase_et where et='" + phrase + "'";
			s = con.prepareCall(sql);
			rs = s.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(rs, s, con);
		}

		return result;
	}

	public int getIdByPhrase(String phrase) {

		StringBuilder msg = new StringBuilder("getIdByPhrase: phrase=" + phrase);

		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		int result = 0;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();

			String sql = "select id from phrase_et where lower(et)='" +
					phrase.toLowerCase() + "'";

			s = con.prepareCall(sql);
			rs = s.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
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

	public List<Voca> findEtOrderEtPaginationSmall(int page, int pageSize) {
		int rowFirst = (page * pageSize) - pageSize + 1;
		int rowLast = page * pageSize;//inclusive
		return findEtOrderEtPaginationSmall(rowFirst, rowLast, pageSize);
	}

	public List<Voca> findEtOrderEtPaginationSmall(int rowFirst, int rowLast, int fetchSize) {

		StringBuilder msg = new StringBuilder("findEtOrderEtPaginationSmall: rowFirst=" + rowFirst +
				", rowLast=" + rowLast + ", fetchSize=" + fetchSize);

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		List<Voca> result = new ArrayList<>();

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
			String sql = "{? = call f_et_order_et_pagination_small_curs(?,?)}";
			s = con.prepareCall(sql);
			s.setFetchSize(fetchSize);
			s.registerOutParameter(1, Types.OTHER);//cursor
			s.setInt(2, rowFirst);
			s.setInt(3, rowLast);
			s.execute();
			rs = (ResultSet) s.getObject(1);

			Voca item;
			while (rs.next()) {

				int id = rs.getInt(1);
				String et = rs.getString(2);

				Array z = rs.getArray(3);
				Integer[] sonaliikIds = (Integer[]) z.getArray();

				item = new Voca();
				item.setId(id);
				item.setPhraseEtId(id);
				item.setEt(et);

				if (sonaliikIds != null && sonaliikIds.length > 0) {
					String categories = "(";
					for (int p : sonaliikIds) {
						if (categories.length() != 1)
							categories += ", ";
						categories += ECfEtSonaliik.findById(p).ABBREVIATION;
					}
					categories += ")";
					item.setCfEtSonaliigid(categories);
				}
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

	public int countEt() {
		StringBuilder msg = new StringBuilder("countEt: ");

		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		int result = 0;

		try {
			con = dao.getConnection();
			String sql = "select count(*) from phrase_et";
			s = con.prepareCall(sql);
			rs = s.executeQuery();
			if (rs.next()) result = rs.getInt(1);
		} catch (SQLException e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(rs, s, con);
		}

		return result;
	}

	public void recordSearch(String q, int resultSize, String lang) {
		StringBuilder msg = new StringBuilder("recordSearch: q=" + q + ", resultSize=" + resultSize);
		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		try {
			con = dao.getConnection();
			String sql = "insert into et_search_hist(s_string, result_size, lang) values (?,?,?)";
			s = con.prepareCall(sql);
			s.setString(1, q);
			s.setInt(2, resultSize);
			s.setString(3, lang);
			s.execute();
		} catch (SQLException e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(rs, s, con);
		}
	}

	public String[] getSonaliigid(String et) {
		StringBuilder msg = new StringBuilder("getSonaliigid: et=" + et);

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		String[] result = null;

		try {
			con = dao.getConnection();
			String sql = "{? = call f_et_sonaliigid(?)}";
			s = con.prepareCall(sql);
			s.registerOutParameter(1, Types.ARRAY);
			s.setString(2, et);
			s.execute();

			Array a = s.getArray(1);
			result = (String[]) a.getArray();
		} catch (SQLException e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(rs, s, con);
		}

		return result;
	}

	public Voca findPhraseEt(int phraseEtId) {
		StringBuilder msg = new StringBuilder("findPhraseEt: phraseEtId=" + phraseEtId);

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		Voca result = null;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			con.setAutoCommit(false);//kui tagastatakse kursor, siis peab autcommit olema false
			String sql = "{? = call f_phrase_et(?)}";
			s = con.prepareCall(sql);
			s.registerOutParameter(1, Types.OTHER);//cursor
			s.setInt(2, phraseEtId);
			s.execute();
			rs = (ResultSet) s.getObject(1);
			if (rs.next()) {
				int i = 1;

				result = new Voca();
				result.setPhraseEtId(phraseEtId);

				result.setEt(rs.getString(1));
				result.setEtAudioId(rs.getInt(2));
				result.setCfOriginEt(ECfOrigin.findById(rs.getInt(3)));

				Array z = rs.getArray(4);
				Integer[] sonaliikIds = (Integer[]) z.getArray();

				if (sonaliikIds != null && sonaliikIds.length > 0) {
					String categories = "";
					for (int p : sonaliikIds) {
						if (categories.length() > 0)
							categories += ", ";
						categories += ECfEtSonaliik.findById(p).ABBREVIATION;
					}
					result.setCfEtSonaliigid(categories);
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
}
