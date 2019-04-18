/**
 * 
 */
package com.smansoft.sl.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author SMan
 *
 */
@Component(value = SpringLoginHandlerExceptionResolver.DEF_BEAN_NAME)
public class SpringLoginHandlerExceptionResolver implements HandlerExceptionResolver {
	
	public static final String DEF_BEAN_NAME = "slHandlerExceptionResolver";

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		int status = response.getStatus();
		
		return null;
	}

}
