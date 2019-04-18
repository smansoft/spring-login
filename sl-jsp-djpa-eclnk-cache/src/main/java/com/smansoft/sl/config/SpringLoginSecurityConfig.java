/**
 * 
 */
package com.smansoft.sl.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smansoft.sl.bl.services.impl.SpringLoginUserDetailsServiceImpl;
import com.smansoft.sl.web.controllers.BaseController;

/**
 * @author SMan
 *
 */
@Configuration
@EnableWebSecurity
public class SpringLoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(SpringLoginUserDetailsServiceImpl.DEF_BEAN_NAME)
	private SpringLoginUserDetailsServiceImpl userServiceBean;

	@Autowired
	@Qualifier(SpringLoginDaoAuthenticationProvider.DEF_BEAN_NAME)
	private SpringLoginDaoAuthenticationProvider daoAuthenticationProviderBean;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier(SpringLoginAuthenticationFailureHandler.DEF_BEAN_NAME)
	private SpringLoginAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	@Qualifier(SpringLoginHandlerExceptionResolver.DEF_BEAN_NAME)
	private SpringLoginHandlerExceptionResolver slHandlerExceptionResolver;

	
	private final ObjectMapper objectMapper;
	  	
	/**
	 * 
	 * @param userServiceBean
	 * @param objectMapper
	 */
	public SpringLoginSecurityConfig(SpringLoginUserDetailsServiceImpl userServiceBean, ObjectMapper objectMapper) {
        this.userServiceBean = userServiceBean;
        this.objectMapper = objectMapper;
    }

	/**
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceBean).passwordEncoder(passwordEncoder);
		auth.authenticationProvider(daoAuthenticationProviderBean);
	}

	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		SpringLoginAuthenticationFilter filter = new SpringLoginAuthenticationFilter();	
		filter.setAuthenticationFailureHandler(new SpringLoginAuthenticationFailureHandler());
		
		httpSecurity
///			.addFilterBefore(filter, 
///			        UsernamePasswordAuthenticationFilter.class)
	  		.cors()
		  	.and()
				.authorizeRequests()
				.antMatchers(
						"/",
						"/*.htm",
						"/resources/**"
							).permitAll()
					.antMatchers("/user/**")		.access("hasRole('USER') or hasRole('ADMIN')")
					.antMatchers("/user_ext_1/**")	.access("hasRole('USER_EXT_1') or hasRole('ADMIN')")
					.antMatchers("/user_ext_2/**")	.access("hasRole('USER_EXT_2') or hasRole('ADMIN')")
					.antMatchers("/user_ext_3/**")	.access("hasRole('USER_EXT_3') or hasRole('ADMIN')")
					.antMatchers("/users/**")		.hasRole("ADMIN")
					.antMatchers("/users/list.json").hasRole("ADMIN")
					.anyRequest().denyAll()
			.and()
				.formLogin()
					.usernameParameter("login")
					.passwordParameter("password")
					.loginPage("/login.htm")
					.loginProcessingUrl("/login.htm")
					.failureForwardUrl("/login.htm")
					.defaultSuccessUrl("/user/info.htm")
///					.failureHandler(authenticationFailureHandler)
///			        .successHandler(this::loginSuccessHandler)
///					.failureHandler(this::loginFailureHandler)					
					.permitAll()
			.and()
				.logout()
					.logoutUrl("/logout.htm")
					.logoutSuccessUrl("/login.htm")
					.deleteCookies("JSESSIONID")
///			        .logoutSuccessHandler(this::logoutSuccessHandler)
					.permitAll()
			.and()
				.exceptionHandling()
			.and()					
				.csrf()
			.and()
				.headers()
					.frameOptions().deny()
			.and()
				.headers()
						.xssProtection().block(true).xssProtectionEnabled(true)
					.and()
						.contentTypeOptions()
					.and()
						.defaultsDisabled().disable()
				.headers()
					.cacheControl();
	}
	
	/**
	 * 
	 * @param auth
	 * @throws Exception
	 */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    }

	/**
	 * 
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		AuthenticationManager authManager = super.authenticationManagerBean();
		return authManager;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));

		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", configuration);

		return corsConfigurationSource;
	}
	

    private void loginSuccessHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
 
        response.setStatus(HttpStatus.OK.value());
        ///objectMapper.writeValue(response.getWriter(), "Yayy you logged in!");
    }
 
    private void loginFailureHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    	response.sendRedirect(BaseController.DEF_ERROR_HTM);
        ///sobjectMapper.writeValue(response.getWriter(), "Nopity nop!");
    }
 
    private void logoutSuccessHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
 
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "Bye!");
    }	
}
