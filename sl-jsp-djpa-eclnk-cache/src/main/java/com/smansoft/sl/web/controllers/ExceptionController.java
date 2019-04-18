/**
 * 
 */
package com.smansoft.sl.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.AuthenticationException;

import com.smansoft.sl.config.SpringLoginSessionInfo;
import com.smansoft.sl.exceptions.ControllersException;
import com.smansoft.sl.exceptions.LoginException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@ControllerAdvice
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ExceptionController {
	
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(ExceptionController.class));
	
	@Autowired
	@Qualifier(SpringLoginSessionInfo.DEF_BEAN_NAME)	
	private SpringLoginSessionInfo sessionInfoBean;		

	/**
	 * 
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = { ServicesException.class, ControllersException.class })
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e)  {
		return resolveErrorView(req, HttpStatus.UNAUTHORIZED, new ModelMap());
	}
	
	/**
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { LoginException.class })
	public ModelAndView defaultErrorHandler_1(HttpServletRequest req, LoginException e)  {
		return resolveErrorView(req, HttpStatus.UNAUTHORIZED, new ModelMap());
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
	
	
		
	
	/**
	 * 
	 * @param request
	 * @param status
	 * @param modelMap
	 * @return
	 */
	private ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);

		String remoteUser = null;
		
		Authentication authentication = sessionInfoBean.getAuthentication();
		if(authentication != null) {
			remoteUser = authentication.getName();
		}
		
		String errorMsgExt = "";
		String errorMsg = "";
		Integer errorCode = 0;
		if (status != null) {
			errorCode = status.value();
		} else {
			Object attribute;
			attribute = request.getAttribute("javax.servlet.error.status_code");
			if (attribute != null) {
				errorCode = (Integer) attribute;
			}
		}
		switch (errorCode) {
		case 400: {
			errorMsg = "Http Error Code: 400. Bad Request";
			break;
		}
		case 401: {
			errorMsg = "Http Error Code: 401. Unauthorized";
			break;
		}
		case 403: {
			errorMsg = "Http Error Code: 403. Forbidden";
			break;
		}		
		case 404: {
			errorMsg = "Http Error Code: 404. Resource not found";
			break;
		}
		case 500: {
			errorMsg = "Http Error Code: 500. Internal Server Error";
			break;
		}
		default:
			errorMsg = String.format("Http Error Code: %d.",errorCode);
		}
		
		errorMsgExt = (String)request.getAttribute("javax.servlet.error.message");
		printToolStr.debug(sessionId, " errorCode = " + errorCode + " errorMsg = " + errorMsg + " errorMsgExt = " + errorMsgExt);
		
		modelMap.put("errorCode", errorCode);
		modelMap.put("errorMsg", errorMsg);
		modelMap.put("errorMsgExt", errorMsgExt);
		
		ModelAndView errorPage = null;
		
		if(remoteUser == null || (remoteUser != null && "anonymousUser".equals(remoteUser))) {
			errorPage = new ModelAndView("error_login", modelMap);						
		} else {
			errorPage = new ModelAndView("error", modelMap);			
		}
		
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
	}

}
