package ee.esutoniagodesu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.MultipartConfigElement;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class MultipartConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MultipartConfigurer.class);

    /*
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() throws IOException {
        log.debug("New instance of " + CommonsMultipartResolver.class);
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolver.setUploadTempDir(new FileSystemResource(tempUploadFolder));
        resolver.setMaxUploadSize(128 * 1024 * 1024);
        return resolver;
    }
    //*/

    @Bean(name = "multipartConfigElement")
    public MultipartConfigElement multipartConfigElement() {
        log.debug("New instance of " + MultipartConfigFactory.class);
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128MB");
        factory.setLocation(System.getProperty("java.io.tmpdir"));
        factory.setMaxRequestSize("128MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public FilterRegistrationBean multipartFilterRegistrationBean() {
        log.debug("New instance of " + FilterRegistrationBean.class);
        final MultipartFilter multipartFilter = new MultipartFilter();
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
        filterRegistrationBean.addInitParameter("multipartResolverBeanName", "multipartResolver");
        return filterRegistrationBean;
    }
}
