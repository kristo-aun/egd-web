package ee.esutoniagodesu.util.persistence;

import org.apache.log4j.Logger;

import javax.sql.rowset.serial.SerialClob;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class JDBCUtil {

	private static final Logger log = Logger.getLogger(JDBCUtil.class);

	/**
	 * cs and parameter are immutable, so they must be final for this method to be thread safe
	 */
	public static void setCSParameter(final CallableStatement cs, int key, final Object parameter, int otype) throws SQLException {
		if (parameter == null) {
			cs.setNull(key, otype);
		} else {
			if (parameter instanceof Integer) cs.setInt(key, (Integer) parameter);
			else if (parameter instanceof String) cs.setString(key, (String) parameter);
			else if (parameter instanceof Date) cs.setDate(key, (Date) parameter);
			else if (parameter instanceof java.util.Date)
				cs.setDate(key, new Date(((java.util.Date) parameter).getTime()));
			else if (parameter instanceof BigDecimal) cs.setBigDecimal(key, (BigDecimal) parameter);
		}
	}

	public static void explainResultSetMetaData(final ResultSetMetaData md) {
		try {
			log.debug("explainResultSetMetaData: getColumnCount=" + md.getColumnCount());
			for (int i = 1; i <= md.getColumnCount(); i++) {
				System.out.print("explainResultSetMetaData: i=" + i);
				System.out.print(", getColumnName=" + md.getColumnName(i));
				//System.out.print(", getCatalogName=" + md.getCatalogName(i));
				System.out.print(", getColumnClassName=" + md.getColumnClassName(i));
				System.out.print(", getColumnLabel=" + md.getColumnLabel(i));
				System.out.print(", getColumnType=" + md.getColumnType(i));
				System.out.println(", getColumnTypeName=" + md.getColumnTypeName(i));
			}
		} catch (SQLException e) {
			log.debug("explainResultSetMetaData: SQLException, msg=" + e.getMessage(), e);
		}
	}

	/**
	 * ei tööta oracle driveriga
	 */
	public static void explainParameterMetaData(final ParameterMetaData md) {
		try {
			log.debug("explainParameterMetaData: getParameterCount=" + md.getParameterCount());
			for (int i = 1; i <= md.getParameterCount(); i++) {
				System.out.print("explainParameterMetaData: i=" + i);
				System.out.print(", getParameterClassName=" + md.getParameterClassName(i));
				System.out.print(", getParameterType=" + md.getParameterType(i));
				System.out.print(", getParameterMode=" + md.getParameterMode(i));
				System.out.print(", getPrecision=" + md.getPrecision(i));
			}
		} catch (SQLException e) {
			log.debug("explainParameterMetaData: SQLException, msg=" + e.getMessage(), e);
		}
	}

	/**
	 * rs.getInt() tagstab int, mis ei arvesta võimalusega, et väli on null. See meetod tagastab null kui
	 * väli on null.
	 * <p/>
	 * ei anna SQLException'it
	 *
	 * @param columnName kasuta explainResultSetMetaData, et teada saada resultset'i ülesehitust
	 * @return Integer väärtus resultSetist kohalt columnname
	 */
	public static Integer getIntegerSafe(final ResultSet rs, String columnName) {
		try {
			int nValue = rs.getInt(columnName);
			if (rs.wasNull()) return null;
			return nValue;
		} catch (SQLException e) {
			log.debug("getInteger: SQLException, columnName=" + columnName);
			return null;
		}
	}

	public static Integer getIntegerSafe(final ResultSet rs, int column) {
		try {
			if (column < 1) return null;
			int nValue = rs.getInt(column);
			if (rs.wasNull()) return null;
			return nValue;
		} catch (SQLException e) {
			log.debug("getInteger: SQLException, column=" + column);
			return null;
		}
	}

	public static String quoteLiteral(String s) {
		return "'" + s + "'";
	}

	public static String quoteIdent(String s) {
		return "\"" + s + "\"";
	}

	public static boolean execute(Connection con, String sql) throws IllegalArgumentException {
		if (sql == null) throw new IllegalArgumentException("sql == null");

		PreparedStatement s = null;
		boolean result = false;

		try {
			long ms = System.currentTimeMillis();
			s = con.prepareCall(sql);
			result = s.execute();
			log.debug("execute: result=" + result + ", time=" + (System.currentTimeMillis() - ms));
		} catch (SQLException e) {
			log.error("execute: exc=" + e.getMessage() + ", sql=" + sql, e);
			e.printStackTrace();
		} finally {
			close(s, con);
		}

		return result;
	}

	public static String clobToString(Clob clob) throws IOException, SQLException {
		if (clob == null) return null;
		Reader reader = clob.getCharacterStream();
		int c;
		StringBuilder sb = new StringBuilder();
		while((c = reader.read()) != -1) {
			sb.append(((char)c));
		}
		return sb.toString();
	}

	public static String clobToStringSafe(Clob clob) throws IOException, SQLException {
		try {
			return clobToString(clob);
		} catch (Exception e) {
			log.error("clobToStringSafe: exc=" + e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	public SerialClob stringToClob(String string) throws SQLException {
		return new SerialClob(string.toCharArray());
	}

	//------------------------------ tekstiindeksi abi, lubatud ainult % -----------------------------

	private static final char[] _forbidden = new char[] {',', '&', '=', '~', ';', '|', '$', '*', '-', '>'};
	private static List<Character> _forbiddenChars;

	public static List<Character> getForbiddenChars() {
		if (_forbiddenChars == null) {
			_forbiddenChars = new ArrayList<>();
			for (char p: _forbidden) {
				_forbiddenChars.add(p);
			}
		}
		return _forbiddenChars;
	}

	//------------------------------ connection close ------------------------------

	public static void close(final ResultSet rs, Statement cs, final Connection con) {
		if (rs != null) try {
			rs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (cs != null) try {
			cs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (con != null) try {
			con.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static void close(final Statement cs, final Connection con) {
		if (cs != null) try {
			cs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (con != null) try {
			con.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static void close(final Statement cs, final Statement cs2, final Connection con) {
		if (cs != null) try {
			cs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}

		if (cs2 != null) try {
			cs2.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}

		if (con != null) try {
			con.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static void close(final ResultSet rs, final Reader reader, final Statement cs, final Connection con) {
		if (rs != null) try {
			rs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (reader != null) try {
			reader.close();
		} catch (IOException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (cs != null) try {
			cs.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
		if (con != null) try {
			con.close();
		} catch (SQLException e) {
			log.error("close: msg=" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
