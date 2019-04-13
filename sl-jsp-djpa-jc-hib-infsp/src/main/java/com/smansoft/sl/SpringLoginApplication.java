package com.smansoft.sl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * @author SMan
 *
 */
@Configuration
@ComponentScan (basePackages="com.smansoft.sl")
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class SpringLoginApplication extends SpringBootServletInitializer {

	/**
	 * 
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringLoginApplication.class);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringLoginApplication.class, args);
	}

}
