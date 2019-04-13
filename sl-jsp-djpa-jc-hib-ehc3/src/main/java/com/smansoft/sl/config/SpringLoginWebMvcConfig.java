/**
 * 
 */
package com.smansoft.sl.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.smansoft.sl.web.converters.AuthorityTypeFormatter;
import com.smansoft.sl.web.converters.AuthorityTypeToStringConverter;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@Configuration
@EnableWebMvc
public class SpringLoginWebMvcConfig implements WebMvcConfigurer {
	
	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginWebMvcConfig.class));

	@Value(value = "${spring.thymeleaf.enabled}")
	private String thymeleafEnabled;

	/**
	 * 
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		Boolean thEnabled = Boolean.valueOf(thymeleafEnabled);
		printTool.info("configureViewResolvers(): thEnabled = " + thEnabled);
		if (thEnabled) {
			ThymeleafViewResolver viewResolver = thymeleafViewResolver();
			registry.viewResolver(viewResolver);
		} else {
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			viewResolver.setPrefix("/WEB-INF/views/jsp/");
			viewResolver.setSuffix(".jsp");
			viewResolver.setViewClass(JstlView.class);
			registry.viewResolver(viewResolver);
		}
	}

	/**
	 * 
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/login.htm");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	/**
	 * 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new PathResourceResolver());
		registry.addResourceHandler("favicon.ico").addResourceLocations("/resources/imgs/favicon.ico")
				.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
	}

	/**
	 * 
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(authorityTypeToStringConverter());
		registry.addFormatter(authorityTypeFormatter());
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(thymeleafTemplateResolver());
		return templateEngine;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/thymeleaf/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public AuthorityTypeToStringConverter authorityTypeToStringConverter() {
		AuthorityTypeToStringConverter converter = new AuthorityTypeToStringConverter();
		return converter;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public AuthorityTypeFormatter authorityTypeFormatter() {
		AuthorityTypeFormatter formatter = new AuthorityTypeFormatter();
		return formatter;
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};	

}
