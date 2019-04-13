/**
 * 
 */
package com.smansoft.sl.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * 
 * @author SMan
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.smansoft.sl.persistence.dao")
@EntityScan("com.smansoft.sl.persistence.entities")
public class SpringLoginPersistenceConfig extends JpaBaseConfiguration {
	
	@SuppressWarnings("unused")
	@Autowired
	private Environment env;

	/**
	 * 
	 * @param dataSource
	 * @param properties
	 * @param jtaTransactionManager
	 * @param transactionManagerCustomizers
	 */
	public SpringLoginPersistenceConfig(DataSource dataSource, JpaProperties properties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
	}

	/**
	 * 
	 */
	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/**
	 * 
	 */
	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> properties = new HashMap<>();
		return properties;
	}

	/**
	 * 
	 */
	@Override
	public JtaTransactionManager getJtaTransactionManager() {
		return null;
	}

	/**
	 * 
	 */
	@Bean("entityManagerFactory")
	@ConditionalOnMissingBean(value={org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.class,javax.persistence.EntityManagerFactory.class})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
		return super.entityManagerFactory(factoryBuilder);
	}

	/**
	 * 
	 */
	@Bean("entityManagerFactoryBuilder")
	@ConditionalOnMissingBean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter,	ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
		return super.entityManagerFactoryBuilder(jpaVendorAdapter, persistenceUnitManager);
	}

	/**
	 * 
	 * @return
	 */
    @Bean("transactionManager")
    public PlatformTransactionManager getJpaTransactionManager() {
    	return super.transactionManager();
    }
    
}
