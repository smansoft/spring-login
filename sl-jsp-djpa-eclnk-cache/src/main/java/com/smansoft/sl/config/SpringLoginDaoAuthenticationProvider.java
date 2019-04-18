/**
 * 
 */
package com.smansoft.sl.config;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.impl.SpringLoginUserDetailsServiceImpl;
import com.smansoft.sl.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@Component(SpringLoginDaoAuthenticationProvider.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SpringLoginDaoAuthenticationProvider extends DaoAuthenticationProvider {
	
	/**
	 * 
	 */
	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginDaoAuthenticationProvider.class));
	
	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "daoAuthenticationProviderBean";		

	/**
	 * 
	 */
	@Autowired
	@Qualifier(SpringLoginUserDetailsServiceImpl.DEF_BEAN_NAME)
	public SpringLoginUserDetailsServiceImpl userDetailsService;
	
	/**
	 * 
	 */
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;	

	/**
	 * 
	 */
	@Autowired
	@Qualifier(SpringLoginSessionInfo.DEF_BEAN_NAME)	
	private SpringLoginSessionInfo sessionInfoBean;
	
	/**
	 * 
	 */
	@PostConstruct 	
	public void postConstruct(){
		setUserDetailsService(userDetailsService);
		setPasswordEncoder(passwordEncoder);
		setHideUserNotFoundExceptions(false);
	}	
	
	/**
	 * 
	 */
	public Authentication authenticate(Authentication authentication) {
		printTool.debug(PrintSfx.SFX_IN);		
		Authentication resAuthentication = null;
		try {
			resAuthentication = super.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(resAuthentication);
			sessionInfoBean.setAuthentication(resAuthentication);
			printTool.info("authenticate user: " + resAuthentication.getName() + " is Ok...");			
		}
		catch(AuthenticationException ex) {
			SecurityContextHolder.getContext().setAuthentication(null);			
			sessionInfoBean.setAuthentication(null);
			printTool.info("authenticate user: " + authentication.getName() + " Failed...");			
			throw ex;
		}
		catch(Throwable ex) {
			SecurityContextHolder.getContext().setAuthentication(null);			
			sessionInfoBean.setAuthentication(null);
			printTool.info("authenticate user: " + authentication.getName() + " Failed...");			
			throw new BadCredentialsException(ExceptionTools.stackTraceToString(ex), ex);
		}
/*		
		if(resAuthentication == null) {
			resAuthentication = authentication;
		}
*/		
		printTool.debug(PrintSfx.SFX_OUT);		
		return resAuthentication;
	}

}
