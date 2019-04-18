/**
 * 
 */
package com.smansoft.sl.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smansoft.sl.exceptions.LoginException;

/**
 * @author SMan
 *
 */
public class SpringLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/**
	 * 
	 */
	@Override 
	public void	doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		String serverName = ((HttpServletRequest)req).getServerName();
		String remoteUser = ((HttpServletRequest)req).getRemoteUser();		
		
		try {
			super.doFilter(req, res, chain);
		}
		catch (IOException ex) {
			throw new LoginException ("Authentication is Failed...", ex);
		}
		catch (ServletException ex) {
			throw new LoginException ("Authentication is Failed...", ex);
		}
		catch (Throwable ex) {
			throw new LoginException ("Authentication is Failed...", ex);			
		}
		return;
	}

}
