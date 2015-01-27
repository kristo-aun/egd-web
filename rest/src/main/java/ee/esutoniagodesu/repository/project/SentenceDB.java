package ee.esutoniagodesu.repository.project;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.jc.util.JDBCUtil;
import ee.esutoniagodesu.domain.estwn.table.Example;
import ee.esutoniagodesu.domain.publik.table.MtmJmSensJpSentence;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SentenceDB extends AbstractProjectRepository {

	public boolean jpSentenceExists(String jp) {
		return sentenceExists("jp_sentence", jp);
	}

	public boolean etSentenceExists(String et) {
		return sentenceExists("et_sentence", et);
	}

	public boolean enSentenceExists(String en) {
		return sentenceExists("en_sentence", en);
	}

	private boolean sentenceExists(String table, String sentence) {
		StringBuilder msg = new StringBuilder("sentenceExists: sentence=" + sentence + ", table=" + table);
		if (sentence == null) throw new IllegalArgumentException(msg.toString());

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			String sql = "select id from " + table + " where jp=?";

			s = con.prepareCall(sql);
			s.setString(1, sentence);
			rs = s.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1) > 0;
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

	public boolean hasExampleSentence(String kanj, String rdng, int sens) {
		StringBuilder msg = new StringBuilder("hasExampleSentence: kanj=" + kanj + ", rdng=" + rdng + ", sens=" + sens);

		Connection con = null;
		CallableStatement s = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			long ms = System.currentTimeMillis();
			con = dao.getConnection();
			String sql = "select id from mtm_jm_sens_jp_sentence where kanj=? and rdng=? and sens=?";

			s = con.prepareCall(sql);
			s.setString(1, kanj);
			s.setString(2, rdng);
			s.setInt(3, sens);

			rs = s.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) > 0;
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

	public List<MtmJmSensJpSentence> getExampleSentences(String kanj, String rdng, int sens) {
		StringBuilder msg = new StringBuilder("getExampleSentences: kanj=" + kanj + ", rdng=" + rdng + ", sens=" + sens);
		log.debug(msg.toString());

        try {
            String sql = "SELECT * from mtm_jm_sens_jp_sentence where kanj=?1 and rdng=?2 and sens=?3";
            Query q = em.createNativeQuery(sql, MtmJmSensJpSentence.class);
            q.setParameter(1, kanj);
            q.setParameter(2, rdng);
            q.setParameter(3, sens);
            List<MtmJmSensJpSentence> result  = q.getResultList();
            log.debug(msg.append(", result.size=").append(result.size()).toString());
			return result;
		} catch (Exception e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw e;
		}
	}

	public List<Example> getEestiWordnetExampleSentences(String variantLiteral) {
		StringBuilder msg = new StringBuilder("getEestiWordnetExampleSentences: variantLiteral=" + variantLiteral);
		log.debug(msg.toString());

        try {
			Search search = new Search(Example.class);
			search.addFilter(Filter.equal("variant.literal", variantLiteral));
			search.setMaxResults(4);
			List<Example> result = dao.search(search);
			log.debug(msg.append(", result.size=").append(result.size()).toString());
			return result;
		} catch (Exception e) {
			log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
			throw e;
		}
	}
}
