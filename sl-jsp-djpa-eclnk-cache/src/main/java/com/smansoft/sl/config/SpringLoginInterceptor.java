/**
 * 
 */
package com.smansoft.sl.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
public class SpringLoginInterceptor extends HandlerInterceptorAdapter {

	private static final IPrintToolStr printToolStr = PrintToolStr.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginInterceptor.class));

	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void	afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String sessionId = request.getSession().getId();
		
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.info(sessionId,"afterCompletion >>");

		printToolStr.info(sessionId,"request.getServerName()  = " + request.getServerName());
		printToolStr.info(sessionId,"request.getServletPath() = " + request.getServletPath());
		printToolStr.info(sessionId,"request.getContextPath() = " + request.getContextPath());
		printToolStr.info(sessionId,"request.getRequestURI()  = " + request.getRequestURI());

		super.afterCompletion(request,response,handler,ex);

		printToolStr.info(sessionId,"afterCompletion <<");
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		
		return;
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void	afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.info(sessionId,"afterConcurrentHandlingStarted >>");

		printToolStr.info(sessionId,"request.getServerName()  = " + request.getServerName());
		printToolStr.info(sessionId,"request.getServletPath() = " + request.getServletPath());
		printToolStr.info(sessionId,"request.getContextPath() = " + request.getContextPath());
		printToolStr.info(sessionId,"request.getRequestURI()  = " + request.getRequestURI());

		super.afterConcurrentHandlingStarted(request,response,handler);

		printToolStr.info(sessionId,"afterConcurrentHandlingStarted <<");
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);

		return;
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void	postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.info(sessionId,"postHandle >>");

		printToolStr.info(sessionId,"request.getServerName()  = " + request.getServerName());
		printToolStr.info(sessionId,"request.getServletPath() = " + request.getServletPath());
		printToolStr.info(sessionId,"request.getContextPath() = " + request.getContextPath());
		printToolStr.info(sessionId,"request.getRequestURI()  = " + request.getRequestURI());

		super.postHandle(request,response,handler,modelAndView);
		
		printToolStr.info(sessionId,"postHandle <<");
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);

		return;
	}
	
 
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public boolean	preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.info(sessionId,"preHandle >>");

		printToolStr.info(sessionId,"request.getServerName()  = " + request.getServerName());
		printToolStr.info(sessionId,"request.getServletPath() = " + request.getServletPath());
		printToolStr.info(sessionId,"request.getContextPath() = " + request.getContextPath());
		printToolStr.info(sessionId,"request.getRequestURI()  = " + request.getRequestURI());

		boolean res = super.preHandle(request,response,handler);
		
		printToolStr.info(sessionId,"preHandle <<");
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);

		return res;
	}

}
