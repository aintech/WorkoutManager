package ru.aintech.workoutmanager.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Configuration
@EnableWebMvc
@ComponentScan("ru.aintech.workoutmanager.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
    
    @Bean
    public ViewResolver viewResolver (SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
    
    @Bean
    public TemplateEngine templateEngine (TemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }
    
    @Bean
    public TemplateResolver templateResolver () {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
    
    @Bean
    public TilesConfigurer tilesConfigurer () {
        TilesConfigurer tiles = new TilesConfigurer();
        tiles.setDefinitions(new String[]{"/WEB-INF/layout/tiles.xml"});
        tiles.setCheckRefresh(true);
        return tiles;
    }
    
    @Bean
    public DataSource dataSource () {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:initial_schema.sql")
                .addScript("classpath:initial_data.sql")
                .build();
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate (DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory (DataSource dataSource) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("ru.aintech.workoutmanager.persistence");
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
        bean.setHibernateProperties(props);
        return bean;
    }
    
    @Bean
    public BeanPostProcessor persistenceTranslation () {
        return new PersistenceExceptionTranslationPostProcessor();
    }
//    @Bean
//    public MultipartResolver multipartResolver () throws IOException {
//        return new StandardServletMultipartResolver();
//    }
}