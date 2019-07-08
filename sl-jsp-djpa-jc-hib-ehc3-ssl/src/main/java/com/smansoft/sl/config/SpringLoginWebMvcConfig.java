/**
 * 
 */
package com.smansoft.sl.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.smansoft.sl.web.converters.UserRoleTypeFormatter;
import com.smansoft.sl.web.converters.UserRoleTypeToStringConverter;
import com.smansoft.sl.web.converters.SexTypeFormatter;
import com.smansoft.sl.web.converters.SexTypeToStringConverter;
import com.smansoft.sl.web.converters.StringToSexTypeConverter;
import com.smansoft.sl.web.converters.StringToUserRoleTypeConverter;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@Configuration
@EnableWebMvc
public class SpringLoginWebMvcConfig implements WebMvcConfigurer {

	@SuppressWarnings("unused")
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginWebMvcConfig.class));

	/**
	 * 
	 */
	@Autowired
	@Qualifier(SpringLoginInterceptor.DEF_BEAN_NAME)
	private HandlerInterceptorAdapter springLoginInterceptorBean;

	/**
	 * 
	 */
	@Autowired
	private UserRoleTypeToStringConverter userRoleTypeToStringConverter;

	/**
	 * 
	 */
	@Autowired
	private StringToUserRoleTypeConverter stringToUserRoleConverter;

	/**
	 * 
	 */
	@Autowired
	private SexTypeToStringConverter sexTypeToStringConverter;

	/**
	 * 
	 */
	@Autowired
	private StringToSexTypeConverter stringToSexTypeConverter;

	/**
	 * 
	 */
	@Autowired
	private UserRoleTypeFormatter userRoleTypeFormatter;

	/**
	 * 
	 */
	@Autowired
	private SexTypeFormatter sexTypeFormatter;

	/**
	 * 
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
	}

	/**
	 * 
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
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
		registry.addConverter(userRoleTypeToStringConverter);
		registry.addConverter(stringToUserRoleConverter);
		registry.addConverter(sexTypeToStringConverter);
		registry.addConverter(stringToSexTypeConverter);
		registry.addFormatter(userRoleTypeFormatter);
		registry.addFormatter(sexTypeFormatter);
	}

	/**
	 * 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(springLoginInterceptorBean);
	}

	/**
	 * 
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("origin", "content-type", "accept", "authorization")
				.allowedOrigins("*").allowCredentials(true)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD");
		return;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles/sl-tiles.xml" });
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	/**
	 * 
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public UserRoleTypeToStringConverter userRoleTypeToStringConverter() {
		UserRoleTypeToStringConverter converter = new UserRoleTypeToStringConverter();
		return converter;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public StringToUserRoleTypeConverter stringToUserRoleConverter() {
		StringToUserRoleTypeConverter converter = new StringToUserRoleTypeConverter();
		return converter;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public SexTypeToStringConverter sexTypeToStringConverter() {
		SexTypeToStringConverter converter = new SexTypeToStringConverter();
		return converter;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public StringToSexTypeConverter stringToSexTypeConverter() {
		StringToSexTypeConverter converter = new StringToSexTypeConverter();
		return converter;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public UserRoleTypeFormatter userRoleTypeFormatter() {
		UserRoleTypeFormatter formatter = new UserRoleTypeFormatter();
		return formatter;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public SexTypeFormatter sexTypeFormatter() {
		SexTypeFormatter formatter = new SexTypeFormatter();
		return formatter;
	}

}
