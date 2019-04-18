/**
 * 
 */
package com.smansoft.sl.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.smansoft.sl.exceptions.LoginException;
import com.smansoft.sl.web.controllers.BaseController;

/**
 * @author SMan
 *
 */
@Component(SpringLoginAuthenticationFailureHandler.DEF_BEAN_NAME)
public class SpringLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = " authenticationFailureHandler";		
	
	@Override
	public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			AuthenticationException exception) throws IOException {

		String serverName = request.getServerName();
		String remoteUser = request.getRemoteUser();
		
///        response.setStatus(HttpStatus.UNAUTHORIZED.value());
///    	response.sendRedirect(BaseController.DEF_ERROR_HTM);		
		
///		response.setStatus(HttpStatus.UNAUTHORIZED.value());
///    	response.sendRedirect(BaseController.DEF_ERROR_HTM);
///		return;		
	}

/*	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		return null;
	}
*/
}

