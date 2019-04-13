/**
 * 
 */
package com.smansoft.sl.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author SMan
 *
 */
@Configuration
@EnableTransactionManagement
@EntityScan("com.smansoft.sl.persistence.entities")
public class SpringLoginPersistenceConfig {

	@Autowired
	private Environment env;

	/**
	 * 
	 */
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/**
	 * 
	 */
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> properties = new HashMap<>();
		return properties;
	}
	
	/**
	 * 
	 * @return
	 */
    @Bean("transactionManager")
    public PlatformTransactionManager getTransactionManager() {
    	return new HibernateTransactionManager(getSessionFactory().getObject());
    }

    /**
     * 
     * @return
     */
    @Bean("dataSource")
    public DataSource getDataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setUrl				(env.getProperty("spring.datasource.url"));
    	dataSource.setUsername			(env.getProperty("spring.datasource.username"));
    	dataSource.setPassword			(env.getProperty("spring.datasource.password"));
    	dataSource.setDriverClassName	(env.getProperty("spring.datasource.driver-class-name"));
    	return dataSource;
    }    

    /**
     * 
     * @return
     */
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean getSessionFactory() {

		Properties properties = new Properties();

		properties.setProperty("hibernate.dialect",								env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.setProperty("hibernate.hbm2ddl.auto",						env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.show_sql",							env.getProperty("spring.jpa.properties.hibernate.show_sql"));
		properties.setProperty("hibernate.format_sql",							env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.setProperty("hibernate.use_sql_comments",					env.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
		properties.setProperty("hibernate.generate_statistics",					env.getProperty("spring.jpa.properties.hibernate.generate_statistics"));
		properties.setProperty("hibernate.connection.autocommit",				env.getProperty("spring.jpa.properties.hibernate.connection.autocommit"));
		properties.setProperty("hibernate.current_session_context_class",		env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
		properties.setProperty("hibernate.max_fetch_depth",						env.getProperty("spring.jpa.properties.hibernate.max_fetch_depth"));
		properties.setProperty("hibernate.connection.autocommit",				env.getProperty("spring.jpa.properties.hibernate.connection.autocommit"));

		properties.setProperty("hibernate.c3p0.min_size",						env.getProperty("spring.jpa.properties.hibernate.c3p0.min_size"));
		properties.setProperty("hibernate.c3p0.max_size",						env.getProperty("spring.jpa.properties.hibernate.c3p0.max_size"));
		properties.setProperty("hibernate.c3p0.timeout",						env.getProperty("spring.jpa.properties.hibernate.c3p0.timeout"));
		properties.setProperty("hibernate.c3p0.max_statements",					env.getProperty("spring.jpa.properties.hibernate.c3p0.max_statements"));
		properties.setProperty("hibernate.c3p0.idle_test_period",				env.getProperty("spring.jpa.properties.hibernate.c3p0.idle_test_period"));
		properties.setProperty("hibernate.c3p0.acquire_increment",				env.getProperty("spring.jpa.properties.hibernate.c3p0.acquire_increment"));
		
		properties.setProperty("hibernate.cache.use_second_level_cache",		env.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
		properties.setProperty("hibernate.cache.use_query_cache",				env.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
		properties.setProperty("hibernate.cache.region.factory_class",			env.getProperty("spring.jpa.properties.hibernate.cache.region.factory_class"));

		properties.setProperty("net.sf.ehcache.configurationResourceName",		env.getProperty("spring.jpa.properties.net.sf.ehcache.configurationResourceName"));
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan("com.smansoft.sl.persistence.entities");
		sessionFactory.setHibernateProperties(properties);

		return sessionFactory;
	}

}
