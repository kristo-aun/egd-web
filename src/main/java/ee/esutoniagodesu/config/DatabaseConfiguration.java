package ee.esutoniagodesu.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.googlecode.genericdao.search.jpa.JPAAnnotationMetadataUtil;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import ee.esutoniagodesu.bean.ProjectDAO;
import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ee.esutoniagodesu.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        log.debug("Configuring Datasource");

        if (propertyResolver.getProperty("url") == null && propertyResolver.getProperty("jndi") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                "cannot start. Please check your Spring profile, current profiles are: {}");

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        try {
            String jndi = propertyResolver.getProperty("jndi");

            if (jndi != null) {
                log.debug("Getting datasource from JNDI global resource link {}", jndi);
                JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
                return dataSourceLookup.getDataSource(jndi);
            } else {
                log.debug("Initializing PGPoolingDataSource");
                PGPoolingDataSource source = new PGPoolingDataSource();
                source.setUrl(propertyResolver.getProperty("url"));
                source.setDataSourceName("jdbc/tootaja");
                source.setUser(propertyResolver.getProperty("username"));
                source.setPassword(propertyResolver.getProperty("password"));
                source.setMaxConnections(10);
                return source;
            }
        } catch (Exception e) {
            log.error("dataSource: msg=" + e.getMessage(), e);
            throw new ApplicationContextException("Database connection pool creation resulted in error");
        }
    }

    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }

    @Bean
    public JPAAnnotationMetadataUtil getMetadataUtil() {
        log.info("New instance of " + JPAAnnotationMetadataUtil.class);
        return new JPAAnnotationMetadataUtil();
    }

    @Bean
    public JPASearchProcessor getJPASearchProcessor(JPAAnnotationMetadataUtil jpaAnnotationMetadataUtil) {
        log.info("New instance of " + JPASearchProcessor.class);
        return new JPASearchProcessor(jpaAnnotationMetadataUtil);
    }

    @Bean
    public ProjectDAO getProjectDAO() {
        return new ProjectDAO();
    }
}
