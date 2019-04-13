/**
 * 
 */
package com.smansoft.sl.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class LoginRegisterController extends BaseController {

	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(LoginRegisterController.class));

	/**
	 * 
	 */
	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;

	/**
	 * 
	 */
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManagerBean; // specific for Spring Security

	/**
	 * 
	 */
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//GET "/login.htm"
	@GetMapping(value = DEF_USER_LOGIN_HTM)
	public ModelAndView getUserLoginView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		UserVO userVO = new UserVO();
		printToolStr.debug(sessionId, userVO);
		modelMap.put("userVO", userVO);
		ModelAndView modelAndView = new ModelAndView("login_register/login", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param userVO
	 * @param errors
	 * @param modelMap
	 * @return
	 */
	//POST "/login.htm"
	@PostMapping(value = DEF_USER_LOGIN_HTM)
	public ModelAndView postUserLoginView(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute UserVO userVO, BindingResult errors, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, userVO);

		UserVO userVOSaved = userServiceBean.getUserByUserLogin(userVO.getLogin());

		boolean matches = passwordEncoder.matches(userVO.getPassword(), userVOSaved.getPassword());

		printToolStr.debug(sessionId, userVO);
		printToolStr.debug(sessionId, userVOSaved);
		printToolStr.debug(sessionId, "userVO.getPassword() (encoded) = " + passwordEncoder.encode(userVO.getPassword()));
		printToolStr.debug(sessionId, matches);
		
		try
		{	Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(userVO.getLogin(), userVO.getPassword()));

			boolean isAuthenticated = authentication.isAuthenticated();
			printToolStr.debug(sessionId, "isAuthenticated = " + isAuthenticated);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch (AuthenticationException e) {
			printToolStr.error(ExceptionTools.stackTraceToString(e), e);
			throw e;
		}
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USER_INFO_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//GET "/logout.htm"
	@GetMapping(value = DEF_USER_LOGOUT_HTM)
	public ModelAndView getUserLogoutView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		SecurityContextHolder.getContext().setAuthentication(null);
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USER_LOGIN_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//POST "/logout.htm"
	@PostMapping(value = DEF_USER_LOGOUT_HTM)
	public ModelAndView postUserLogoutView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		SecurityContextHolder.getContext().setAuthentication(null);
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USER_LOGIN_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//GET "/register.htm"
	@GetMapping(value = DEF_USER_REGISTER_HTM)
	public ModelAndView getUserRegisterView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, "request.getContextPath() = " + request.getContextPath());
		printToolStr.debug(sessionId, "request.getServletPath() = " + request.getServletPath());
		RegisterVO registerVO = new RegisterVO();
		printToolStr.debug(sessionId, registerVO);
		modelMap.put("registerVO", registerVO);
		ModelAndView modelAndView = new ModelAndView("login_register/register", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param registerVO
	 * @param errors
	 * @param modelMap
	 * @return
	 * @throws Throwable
	 */
	//POST "/register.htm"
	@PostMapping(value = DEF_USER_REGISTER_HTM)
	public ModelAndView postUserRegisterView(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute RegisterVO registerVO, BindingResult errors, ModelMap modelMap) throws Throwable {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, registerVO);
		UserVO userVO = userServiceBean.createUser(registerVO);
		modelMap.put("login", userVO.getLogin());
		modelMap.put("email", userVO.getEmail());
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USER_REGISTERED_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//GET "/registered.htm"
	@GetMapping(value = DEF_USER_REGISTERED_HTM)
	public ModelAndView getUserRegisteredView(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (required=true) String login,	@RequestParam (required=true) String email,
			@ModelAttribute RegisterVO registerVO, BindingResult errors,
			ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		modelMap.put("login", login);
		modelMap.put("email", email);
		ModelAndView modelAndView = new ModelAndView("login_register/registered", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	//POST "/registered.htm"
	@PostMapping(value = DEF_USER_REGISTERED_HTM)
	public ModelAndView postUserRegisteredView(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USER_LOGIN_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

}
