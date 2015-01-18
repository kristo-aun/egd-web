package ee.esutoniagodesu.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.googlecode.genericdao.search.jpa.JPAAnnotationMetadataUtil;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import com.jc.hibernate.ProjectDAO;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SpringSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "ee.esutoniagodesu.repository.domain")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring Datasource");

        if (propertyResolver.getProperty("url") == null && propertyResolver.getProperty("jndi") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        try {
            String jndi = propertyResolver.getProperty("jndi");

            if (jndi != null) {
                log.debug("Getting datasource from JNDI global resource link");
                InitialContext ctx = new InitialContext();
                return (DataSource) ctx.lookup(jndi);
            } else {
                log.debug("Initializing PGPoolingDataSource");
                PGPoolingDataSource source = new PGPoolingDataSource();
                source.setUrl(propertyResolver.getProperty("url"));
                source.setDataSourceName("jdbc/egd");
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
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        log.debug("jdbcTemplate");
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Hibernate4Module hibernate4Module() {
        log.debug("hibernate4Module");
        return new Hibernate4Module();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        log.debug("entityManagerFactory");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ee.esutoniagodesu.domain");
        emf.setJpaProperties(buildHibernateProperties());
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {{
            setDatabase(Database.POSTGRESQL);
        }});
        //emf.setPersistenceUnitName("mainPersistenceUnit");
        //emf.afterPropertiesSet();
        return emf;
    }

    protected Properties buildHibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.current_session_context_class", SpringSessionContext.class.getName());
        //properties.setProperty("hibernate.ejb.entitymanager_factory_name", "entitymanager_factory_name");
        properties.setProperty("connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("javax.persistence.validation.mode", "none");

        //Audit History flags
        properties.setProperty("org.hibernate.envers.store_data_at_delete", "true");
        properties.setProperty("org.hibernate.envers.global_with_modified_flag", "true");

        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        log.debug("getTransactionManager");
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(dataSource());
        tm.setEntityManagerFactory(emf);
        return tm;
    }

    /*
    @Bean
    public TransactionTemplate transactionTemplate() {
        log.debug("getTransactionTemplate");
        return new TransactionTemplate(transactionManager());
    }
    //*/

    @Bean
    public JPASearchProcessor getJPASearchProcessor() {
        log.debug("getJPASearchProcessor");
        return new JPASearchProcessor(getMetadataUtil());
    }

    /*
    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        log.debug("getPersistenceAnnotationBeanPostProcessor");
        return new PersistenceAnnotationBeanPostProcessor();
    }
    //*/

    @Bean
    public JPAAnnotationMetadataUtil getMetadataUtil() {
        log.debug("getMetadataUtil");
        return new JPAAnnotationMetadataUtil();
    }

    @Bean
    public ProjectDAO getProjectDAO() {
        log.debug("getProjectDAO");
        return new ProjectDAO();
    }
}
