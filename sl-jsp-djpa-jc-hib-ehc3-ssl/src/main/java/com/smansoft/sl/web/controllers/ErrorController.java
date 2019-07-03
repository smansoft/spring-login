/**
 * 
 */
package com.smansoft.sl.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;

import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ErrorController extends BaseErrorController implements ErrorViewResolver {

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
		ModelAndView errorPage = resolveErrorView(request, HttpStatus.valueOf(response.getStatus()), modelMap);
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
		ModelAndView errorPage = resolveErrorView(request, HttpStatus.valueOf(response.getStatus()), modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return errorPage;
	}

}
