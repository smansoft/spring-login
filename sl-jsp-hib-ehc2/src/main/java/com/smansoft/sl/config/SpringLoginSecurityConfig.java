/**
 * 
 */
package com.smansoft.sl.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.smansoft.sl.bl.services.impl.SpringLoginUserDetailsServiceImpl;

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
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceBean).passwordEncoder(passwordEncoder);
	}

	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http
				.cors()
			.and()
				.authorizeRequests()
				.antMatchers(
						"/",							
						"/*.htm",
						"/resources/**"
						).permitAll()
				.antMatchers(
						"/user/**").hasRole("USER")
				.antMatchers(
						"/users/**").access("hasRole('USER') and hasRole('ADMIN')")
				.anyRequest().denyAll()
			.and()
				.formLogin().usernameParameter("login").passwordParameter("password")
				.loginPage("/login.htm")
				.loginProcessingUrl("/login.htm")
				.failureForwardUrl("/login.htm")
				.defaultSuccessUrl("/user/info.htm")
				.permitAll()
			.and()
				.logout().logoutUrl("/logout.htm").logoutSuccessUrl("/login.htm").permitAll()
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
		return super.authenticationManagerBean();
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
}
