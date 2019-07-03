package com.smansoft.sl.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Scope;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * 
 * @author SMan
 *
 */
@Configuration
@Scope(BeanDefinition.SCOPE_SINGLETON)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class SpringLoginMBeanConfig {
	

}
