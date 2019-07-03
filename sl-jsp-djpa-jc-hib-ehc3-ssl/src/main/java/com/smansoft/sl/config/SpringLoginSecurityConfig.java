/**
 * 
 */
package com.smansoft.sl.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.smansoft.sl.bl.services.impl.UserDetailsServiceImpl;

/**
 * @author SMan
 *
 */
@Configuration
@Scope(BeanDefinition.SCOPE_SINGLETON)
@EnableWebSecurity
public class SpringLoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(UserDetailsServiceImpl.DEF_BEAN_NAME)
	private UserDetailsService userDetailsServiceBean;

	@Autowired
	@Qualifier(SpringLoginDaoAuthenticationProvider.DEF_BEAN_NAME)
	private DaoAuthenticationProvider springLoginDaoAuthenticationProviderBean;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceBean).passwordEncoder(passwordEncoder);
		auth.authenticationProvider(springLoginDaoAuthenticationProviderBean);
	}

	/**
	 * 
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
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
					.permitAll()
			.and()
				.logout()
					.logoutUrl("/logout.htm")
					.logoutSuccessUrl("/login.htm")
					.deleteCookies("JSESSIONID")
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
	@Scope(BeanDefinition.SCOPE_SINGLETON)
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
	@Scope(BeanDefinition.SCOPE_SINGLETON)
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
