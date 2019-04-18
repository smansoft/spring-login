/**
 * 
 */
package com.smansoft.sl.config;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author SMan
 *
 */
@Component(SpringLoginSessionInfo.DEF_BEAN_NAME)
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpringLoginSessionInfo {
	
	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "slSessionInfoBean";	
	
	/**
	 * 
	 */
	private Authentication authentication;
	
	/**
	 *
	 * @param authentication
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	/**
	 * 
	 * @return
	 */
	public Authentication getAuthentication() {
		return this.authentication;
	}
	

}
