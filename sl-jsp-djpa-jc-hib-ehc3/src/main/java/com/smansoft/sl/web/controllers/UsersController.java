/**
 * 
 */
package com.smansoft.sl.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UsersController extends BaseController {

	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(UsersController.class));

	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager transactionManager;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/user/info.htm"
	//GET "/users/{login}/info.htm"
	@GetMapping(value = { DEF_USER_INFO_HTM, DEF_USERS_LOGIN_INFO_HTM })
	public ModelAndView getUserInfoView(HttpServletRequest request, HttpServletResponse response, @PathVariable (name = "login", required=false) String loginPath,
			@RequestParam (name = "login", required=false) String loginParam, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		UserVO userVO = null;
		String login = null; 
		if(loginPath != null && loginPath.length() > 0) {
			login = loginPath;
		} else if(loginParam != null && loginParam.length() > 0) {
			login = loginParam;
		} else {
			login = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		userVO = userServiceBean.getUserByUserLogin(login);
		ModelAndView modelAndView = null;
		if(userVO != null) {
			modelMap.put("userVO", userVO);
			printToolStr.debug(sessionId, userVO);
			modelAndView = new ModelAndView("user/info", modelMap);
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView("redirect:"+DEF_USER_NOT_FOUND_HTM, modelMap);
		}		
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/user/delete.htm"
	//GET "/users/{login}/delete.htm"
	@GetMapping(value = { DEF_USER_DEL_HTM, DEF_USERS_LOGIN_DEL_HTM })
	public ModelAndView getUserDeleteView(HttpServletRequest request, HttpServletResponse response, @PathVariable (required=false) String login, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		UserVO userVO = null;
		if(login == null || login.length() == 0) {
			login = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		userVO = userServiceBean.getUserByUserLogin(login);
		ModelAndView modelAndView = null;
		if(userVO != null) {
			userVO = userServiceBean.deleteUserByUserLogin(login);
			modelMap.put("login", userVO.getLogin());
			modelMap.put("email", userVO.getEmail());
			modelAndView = new ModelAndView("redirect:"+DEF_USER_DELETED_HTM, modelMap);
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView("redirect:"+DEF_USER_NOT_FOUND_HTM, modelMap);
		}
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param userVO
	 * @param errors
	 * @param modelMap
	 * @return
	 */
	//GET "/user/deleted.htm"
	@GetMapping(value = DEF_USER_DELETED_HTM)
	public ModelAndView getUserDeletedView(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (required=true) String login,	@RequestParam (required=true) String email,
			@ModelAttribute UserVO userVO, BindingResult errors,
			ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		modelMap.put("login", login);
		modelMap.put("email", email);
		ModelAndView modelAndView = new ModelAndView("user/deleted", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param userVO
	 * @param errors
	 * @param modelMap
	 * @return
	 */
	//POST "/user/deleted.htm"
	@PostMapping(value = DEF_USER_DELETED_HTM)
	public ModelAndView postUserDeletedView(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute UserVO userVO, BindingResult errors, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView = new ModelAndView("redirect:"+DEF_USERS_LIST_HTM, modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/user/edit.htm"
	//GET "/users/{login}/edit.htm"
	@GetMapping(value = {DEF_USER_EDIT_HTM, DEF_USERS_LOGIN_EDIT_HTM})
	public ModelAndView getUserEditView(HttpServletRequest request, HttpServletResponse response, @PathVariable (required=false) String login, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		RegisterVO registerVO = null;
		if(login == null || login.length() == 0) {
			login = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		registerVO = userServiceBean.getRegisterByUserLogin(login);
		ModelAndView modelAndView = null;
		if(registerVO != null) {
			modelMap.put("registerVO", registerVO);
			modelAndView = new ModelAndView("user/edit", modelMap);
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView("redirect:"+DEF_USER_NOT_FOUND_HTM, modelMap);
		}
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//POST "/user/edit.htm"
	@PostMapping(value = DEF_USER_EDIT_HTM)
	public ModelAndView postUserEditView(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute RegisterVO registerVO, BindingResult errors, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, registerVO);
		UserVO userVO = userServiceBean.updateUser(registerVO);
		ModelAndView modelAndView = null;
		modelMap = new ModelMap();
		modelMap.put("login",registerVO.getLogin());
		if(userVO != null) {
			printToolStr.debug(sessionId, userVO);
			modelAndView = new ModelAndView("redirect:"+DEF_USER_INFO_HTM, modelMap);
		} else {
			modelAndView = new ModelAndView("redirect:"+DEF_USER_ERROR_UPDATE_HTM, modelMap);
		}
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
	//GET "/users/list.htm"
	@GetMapping(value = DEF_USERS_LIST_HTM)
	public ModelAndView getUsersListView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		List<UserVO> userVOs = userServiceBean.getAllUsers();
		modelMap.put("userVOs", userVOs);
		ModelAndView modelAndView = new ModelAndView("users/list", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/user/user_not_found.htm"
	@GetMapping(value =  DEF_USER_NOT_FOUND_HTM)
	public ModelAndView getUserNotFoundView(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable (required=true) String login, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		modelMap.put("login",login);
		ModelAndView modelAndView = new ModelAndView("users/user_not_found", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/user/user_error_update.htm"
	@GetMapping(value =  DEF_USER_ERROR_UPDATE_HTM)
	public ModelAndView getUserErrorUpdateView(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable (required=true) String login, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		modelMap.put("login",login);
		ModelAndView modelAndView = new ModelAndView("users/user_error_update", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}	

	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Throwable
	 */
	//GET "/user/info_error.htm"
	@GetMapping(value = DEF_USER_INFO_ERROR_HTM)
	public ModelAndView getUserErrorView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Throwable {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		try {
			throw new Throwable("Exception from mapper of user_error.htm");
		} finally {
			printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		}
	}

}
