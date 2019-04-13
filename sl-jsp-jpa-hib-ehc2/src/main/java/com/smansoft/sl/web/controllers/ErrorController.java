/**
 * 
 */
package com.smansoft.sl.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;

/**
 * @author SMan
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ErrorController extends BaseController implements ErrorViewResolver {

	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(ErrorController.class));


	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = DEF_ERROR_HTM, method = RequestMethod.GET)
	public ModelAndView getErrorView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView errorPage = resolveErrorView(request, null, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = DEF_ERROR_HTM, method = RequestMethod.POST)
	public ModelAndView postErrorView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView errorPage = resolveErrorView(request, null, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
	}	

	/**
	 * 
	 */
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelMap modelMap = new ModelMap();
		ModelAndView errorPage = resolveErrorView(request, status, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
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
		printToolStr.debug(sessionId, " errorCode = " + errorCode + " errorMsg = " + errorMsg);
		modelMap.put("errorCode", errorCode);
		modelMap.put("errorMsg", errorMsg);
		ModelAndView errorPage = new ModelAndView("error/error", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
	}

}
