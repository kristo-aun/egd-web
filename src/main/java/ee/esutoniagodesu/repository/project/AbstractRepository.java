package ee.esutoniagodesu.repository.project;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;

public abstract class AbstractRepository {

    private static final Logger log = Logger.getLogger(AbstractRepository.class);

    protected final JdbcTemplate jdbcTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        log.debug("New instance of " + getClass());
        this.jdbcTemplate = jdbcTemplate;
    }

    protected DataSource getDataSource() {
        return jdbcTemplate.getDataSource();
    }

    private static String safeWrap(Object parameter) {
        if (parameter == null) return null;

        if (parameter instanceof String) {
            return "\"" + parameter + '"';
        } else if (parameter instanceof byte[]) {
            return "byte[length=" + ((byte[]) parameter).length + "]";
        } else {
            return parameter.toString();
        }
    }

    protected abstract class CustomStoredProcedure extends StoredProcedure implements SqlProvider {
        public CustomStoredProcedure(String procedure) {
            this(jdbcTemplate, procedure);
        }

        public CustomStoredProcedure(JdbcTemplate jdbcTemplate, String procedure) {
            super(jdbcTemplate, procedure);
            prepare();
            compile();
        }

        public String getSql(Object... inParams) {
            StringBuilder builder = new StringBuilder();

            for (Object p : inParams) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(safeWrap(p));
            }

            return "call " + super.getSql() + "(" + builder.toString() + ")";
        }

        public Map<String, Object> execute(Object... inParams) {
            if (log.isDebugEnabled()) {
                log.debug(getSql(inParams));
            }

            try {
                return super.execute(inParams);
            } catch (Exception e) {
                log.error(getSql(inParams));
                throw e;
            }
        }

        public abstract void prepare();
    }

    protected class CustomStatementBuilder implements SqlProvider {

        protected StringBuilder parameters = new StringBuilder();
        protected final String sql;

        public CustomStatementBuilder(String sql) {
            Assert.notNull(sql, "Call string must not be null");
            this.sql = sql;
        }

        public String getSql() {
            return this.sql;
        }

        public void setLong(PreparedStatement s, int index, Long x) throws SQLException {
            setParameter(s, index, x, Types.NUMERIC);
        }

        public void setInt(PreparedStatement s, int index, Integer x) throws SQLException {
            setParameter(s, index, x, Types.INTEGER);
        }

        public void setString(PreparedStatement s, int index, String x) throws SQLException {
            setParameter(s, index, x, Types.VARCHAR);
        }

        public void setDate(PreparedStatement s, int index, java.util.Date x) throws SQLException {
            setParameter(s, index, x, Types.DATE);
        }

        public void setTimestampWithTimezone(PreparedStatement s, int index, java.util.Date x) throws SQLException {
            setParameter(s, index, x, Types.TIMESTAMP_WITH_TIMEZONE);
        }

        public void setTimestamp(PreparedStatement s, int index, java.util.Date x) throws SQLException {
            setParameter(s, index, x, Types.TIMESTAMP);
        }

        public void setParameter(PreparedStatement cs, int key, Object parameter, int otype) throws SQLException {
            if (parameter == null) {
                cs.setNull(key, otype);
            } else {
                if (parameter instanceof Boolean) cs.setBoolean(key, (boolean) parameter);
                else if (parameter instanceof Byte) cs.setByte(key, (byte) parameter);
                else if (parameter instanceof Short) cs.setShort(key, (short) parameter);
                else if (parameter instanceof Integer) cs.setInt(key, (int) parameter);
                else if (parameter instanceof Long) cs.setLong(key, (long) parameter);
                else if (parameter instanceof Float) cs.setFloat(key, (float) parameter);
                else if (parameter instanceof Double) cs.setDouble(key, (double) parameter);
                else if (parameter instanceof BigDecimal) cs.setBigDecimal(key, (BigDecimal) parameter);
                else if (parameter instanceof String) cs.setString(key, (String) parameter);
                else if (parameter instanceof byte[]) cs.setBytes(key, (byte[]) parameter);
                else if (parameter instanceof java.sql.Date) cs.setDate(key, (java.sql.Date) parameter);
                else if (parameter instanceof java.sql.Time) cs.setTime(key, (java.sql.Time) parameter);
                else if (parameter instanceof java.sql.Timestamp) cs.setTimestamp(key, (java.sql.Timestamp) parameter);
                else if (parameter instanceof java.sql.Array) cs.setArray(key, (java.sql.Array) parameter);
                else if (parameter instanceof java.util.Date)
                    cs.setDate(key, new java.sql.Date(((java.util.Date) parameter).getTime()));

                else {
                    throw new RuntimeException("not implemented");
                }
            }

            if (log.isErrorEnabled()) {
                if (parameters.length() > 0) {
                    parameters.append(", ");
                }

                parameters.append(safeWrap(parameter));
            }
        }
    }

    protected abstract class CustomCallableStatementCreator extends CustomStatementBuilder implements CallableStatementCreator {

        private static final String prefix = "call ";

        public CustomCallableStatementCreator(String sql) {
            super(sql);
        }

        public CallableStatement createCallableStatement(Connection con) throws SQLException {
            return con.prepareCall(sql);
        }

        @Override
        public String getSql() {
            String function = sql.substring(sql.indexOf(prefix) + prefix.length(), sql.indexOf("("));
            return prefix + function + "(" + parameters.toString() + ")";
        }
    }

    public <T> T execute(CustomCallableStatementCreator csc, CallableStatementCallback<T> action) {
        try {
            T result = jdbcTemplate.execute(csc, action);
            if (log.isDebugEnabled()) {
                log.debug(csc.getSql());
            }
            return result;
        } catch (Exception e) {
            log.error(csc.getSql());
            throw e;
        }
    }

    protected abstract class CustomPreparedStatementCreator extends CustomStatementBuilder implements PreparedStatementCreator, SqlProvider {

        public CustomPreparedStatementCreator(String sql) {
            super(sql);
        }

        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            return con.prepareStatement(this.sql);
        }
    }

    public <T> T execute(CustomPreparedStatementCreator csc, PreparedStatementCallback<T> action) {
        try {
            T result = jdbcTemplate.execute(csc, action);
            if (log.isDebugEnabled()) {
                log.debug(csc.getSql());
            }
            return result;
        } catch (Exception e) {
            log.error(csc.getSql());
            throw e;
        }
    }
}
