/**
 * 
 */
package com.smansoft.sl.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.AuthenticationException;

import com.smansoft.sl.exceptions.ControllersException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@ControllerAdvice
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ExceptionControllerAdvice extends BaseErrorController {
	
	@SuppressWarnings("unused")
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(ExceptionControllerAdvice.class));

	/**
	 * 
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = { ServicesException.class })
	public ModelAndView servicesExceptionHandler(HttpServletRequest req, ServicesException e)  {
		return resolveErrorView(req, HttpStatus.INTERNAL_SERVER_ERROR, new ModelMap());
	}
	
	/**
	 * 
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = { ControllersException.class })
	public ModelAndView controllersExceptionHandler(HttpServletRequest req, ControllersException e)  {
		return resolveErrorView(req, HttpStatus.INTERNAL_SERVER_ERROR, new ModelMap());
	}
	
	/**
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { AuthenticationException.class })
	public ModelAndView defaultErrorHandler_2(HttpServletRequest req, AuthenticationException e)  {
		return resolveErrorView(req, HttpStatus.UNAUTHORIZED, new ModelMap());
	}

}
