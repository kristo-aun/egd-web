package ee.esutoniagodesu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SHADBConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SHADBConfiguration.class);

    @Bean(name ="shadb", destroyMethod = "close")
    @ConfigurationProperties(prefix="app.multimedia.datasource")
    public DataSource shadb() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcShadb")
    public JdbcTemplate jdbcShadb(@Qualifier("shadb") DataSource shadb) {
        return new JdbcTemplate(shadb);
    }
}
