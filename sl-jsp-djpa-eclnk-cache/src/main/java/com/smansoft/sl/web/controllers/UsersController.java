/**
 * 
 */
package com.smansoft.sl.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.PageRequestVO;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;

import com.smansoft.sl.config.SpringLoginBreadCrumb;
import com.smansoft.sl.persistence.types.UserRoleType;
import com.smansoft.sl.tools.common.UserRoleTools;
import com.smansoft.sl.persistence.types.SexType;
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
	@Qualifier(SpringLoginBreadCrumb.DEF_BEAN_NAME)
	private SpringLoginBreadCrumb springLoginBreadCrumbBean;

	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	public static class JsonResponce {
		public String responce;
	}


	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Throwable
	 */
	//GET "/"
	//GET "/index.htm"
	@GetMapping(value = { DEF_ROOT_HTM, DEF_INDEX_HTM })
	public ModelAndView getUserRoot(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Throwable {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView;
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		if(login == null || login.length() == 0 || "anonymousUser".equals(login)) {
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_LOGIN_HTM, modelMap);
		} else {
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_INFO_HTM, modelMap);
		}

		springLoginBreadCrumbBean.pushPath("path1_1/path1_2/path1_3/path1_4");
		springLoginBreadCrumbBean.pushPath("path2_1/path2_2/path2_3");
		springLoginBreadCrumbBean.pushPath("path3_1/path3_2/path3_3/path3_4/path3_5");
		springLoginBreadCrumbBean.pushPath("path4_1/path4_2/path4_3/path4_4/path4_5/path4_6/path4_7");
		springLoginBreadCrumbBean.pushPath("path5_1/path5_2");
		springLoginBreadCrumbBean.pushPath("path6_1/path6_2/path6_3/path6_4/path6_5/path6_6");
		springLoginBreadCrumbBean.pushPath("path7_1");
		springLoginBreadCrumbBean.pushPath("path8_1/path8_2/path8_3/path8_4/path8_5/path8_6/path8_7/path8_8");

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
			modelMap.put("sexList", SexType.values());
			modelMap.put("userVO", userVO);
			printToolStr.debug(sessionId, userVO);
			modelAndView = new ModelAndView("info", modelMap);
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_NOT_FOUND_HTM, modelMap);
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
	 * @throws Exception 
	 */
	//GET "/user/delete.htm"
	//GET "/users/{login}/delete.htm"
	@GetMapping(value = { DEF_USER_DEL_HTM, DEF_USERS_LOGIN_DEL_HTM })
	public ModelAndView getUserDeleteView(HttpServletRequest request, HttpServletResponse response, @PathVariable (required=false) String login, ModelMap modelMap) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		UserVO userVO = null;
		String currUserName = UserRoleTools.getCurrentUserName();
		if(login == null || login.length() == 0) {
			login = currUserName;
		}
		userVO = userServiceBean.getUserByUserLogin(login);
		ModelAndView modelAndView = null;
		if(userVO != null) {
			boolean isRootAdmin = UserRoleTools.checkUserRole(UserRoleType.ROLE_ROOT_ADMIN, userVO.getUserRoles());
			if(isRootAdmin) {
				throw new Exception("Root Admin user can't be removed");
			}
			userVO = userServiceBean.deleteUserByUserLogin(login);
			modelMap.put("login", userVO.getLogin());
			modelMap.put("email", userVO.getEmail());
			if(currUserName.equals(login)) {
				SecurityContextHolder.getContext().setAuthentication(null);				
				modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_DELETED_HTM, modelMap);
			} else {
				modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_DELETED_HTM, modelMap);
			}
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_NOT_FOUND_HTM, modelMap);
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
		ModelAndView modelAndView = new ModelAndView("deleted", modelMap);
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
		ModelAndView modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USERS_LIST_HTM, modelMap);
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
	//GET "/deleted.htm"
	@GetMapping(value = DEF_DELETED_HTM)
	public ModelAndView getDeletedView(HttpServletRequest request, HttpServletResponse response,
			@RequestParam (required=true) String login,	@RequestParam (required=true) String email,
			@ModelAttribute UserVO userVO, BindingResult errors,
			ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		modelMap.put("login", login);
		modelMap.put("email", email);
		ModelAndView modelAndView = new ModelAndView("deleted_login", modelMap);
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
	 * @throws Exception 
	 */
	//GET "/user/edit.htm"
	//GET "/users/{login}/edit.htm"
	@GetMapping(value = {DEF_USER_EDIT_HTM, DEF_USERS_LOGIN_EDIT_HTM})
	public ModelAndView getUserEditView(HttpServletRequest request, HttpServletResponse response,
			@PathVariable (required=false) String login, ModelMap modelMap) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		RegisterVO registerVO = null;
		String currUserName = UserRoleTools.getCurrentUserName();
		if(login == null || login.length() == 0) {
			login = currUserName;
		}
		registerVO = userServiceBean.getRegisterByUserLogin(login);
		ModelAndView modelAndView = null;
		if(registerVO != null) {
			boolean isCurrRootAdmin = UserRoleTools.isCurrentRole(UserRoleType.ROLE_ROOT_ADMIN);
			boolean isCurrAdmin = UserRoleTools.isCurrentRole(UserRoleType.ROLE_ADMIN);
			boolean isRootAdmin = UserRoleTools.checkUserRole(UserRoleType.ROLE_ROOT_ADMIN, registerVO.getUserRoles());
			UserRoleType [] userRoles;
			boolean isEditRoles;
			if(isRootAdmin) {
				userRoles = UserRoleType.values();
			} else {
				userRoles = UserRoleTools.valuesNoRootAdmin();
			}
			if((isCurrRootAdmin || isCurrAdmin) && !isRootAdmin) {
				isEditRoles = true;
			} else {
				isEditRoles = false;
			}
			modelMap.put("sexList", SexType.values());
			modelMap.put("userRolesList", userRoles);
			modelMap.put("isEditRoles", isEditRoles);
			modelMap.put("registerVO", registerVO);
			modelAndView = new ModelAndView("edit", modelMap);
		} else {
			modelMap.put("login", login);
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_NOT_FOUND_HTM, modelMap);
		}
		
		String path;

		springLoginBreadCrumbBean.setPopCount(1);
		path = springLoginBreadCrumbBean.popPath();

		printToolStr.debug(sessionId, "path = " + path);

		springLoginBreadCrumbBean.setPopCount(3);
		path = springLoginBreadCrumbBean.popPath();

		printToolStr.debug(sessionId, "path = " + path);

		springLoginBreadCrumbBean.setPopCount(4);
		path = springLoginBreadCrumbBean.popPath();

		printToolStr.debug(sessionId, "path = " + path);

		springLoginBreadCrumbBean.setPopCount(1);
		path = springLoginBreadCrumbBean.popPath();

		printToolStr.debug(sessionId, "path = " + path);
		
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
			@ModelAttribute RegisterVO registerVO, BindingResult errors, ModelMap modelMap)  throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, registerVO);
		registerVO = userServiceBean.preProcessPassword(registerVO);
		UserVO userVO = userServiceBean.updateUser(registerVO);
		ModelAndView modelAndView = null;
		modelMap = new ModelMap();
		modelMap.put("login",registerVO.getLogin());
		if(userVO != null) {
			printToolStr.debug(sessionId, userVO);
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_INFO_HTM, modelMap);
		} else {
			modelAndView = new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX+DEF_USER_ERROR_UPDATE_HTM, modelMap);
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
	 * @throws Exception 
	 */
	//GET "/users/list.htm"
	@GetMapping(value = DEF_USERS_LIST_HTM)
	public ModelAndView getUsersListView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		List<UserVO> userVOs = userServiceBean.getAllUsers();

		List<UserVO> resUserVOs = new ArrayList<>();

		List<String> resUserRoles = new ArrayList<>();
		resUserRoles.add("ROLE_USER");
		resUserRoles.add("ROLE_USER_EXT_1");
		resUserRoles.add("ROLE_USER_EXT_2");
		resUserRoles.add("ROLE_USER_EXT_3");

		resUserVOs.addAll(userVOs);
		for(int i = 0; i < 30; i++) {
			UserVO userVO = new UserVO();
			userVO.setLogin(String.format("sman_%d", i));
			userVO.setEmail(String.format("sman_%d@smansoft.com", i));
			userVO.setName(String.format("SMan_%d", i));
			userVO.setAddress(String.format("Some Address SMan %d", i));
			if((i % 2) == 0) {
				userVO.setSex("Male");
			}
			else {
				userVO.setSex("Female");
			}
			userVO.setPassword(passwordEncoder.encode("12345"));
			userVO.setEnabled(true);
			userVO.setUserRoles(resUserRoles);
			resUserVOs.add(userVO);
		}
		modelMap.put("pageSize", DEF_PAGE_SIZE);
		modelMap.put("userVOs", resUserVOs);
		ModelAndView modelAndView = new ModelAndView("list", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = DEF_USERS_LIST_JSON)
	public @ResponseBody List<UserVO> getListUsesJson(HttpServletRequest request, HttpServletResponse response,
										@RequestParam (value = "pageNumber", required=false) Integer pageNumber) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		printToolStr.debug(sessionId, "pageNumber = " + pageNumber);

		List<UserVO> userVOs = userServiceBean.getAllUsers();

		List<UserVO> resUserVOs = new ArrayList<>();

		List<String> resUserRoles = new ArrayList<>();
		resUserRoles.add("ROLE_USER");
		resUserRoles.add("ROLE_USER_EXT_1");
		resUserRoles.add("ROLE_USER_EXT_2");
		resUserRoles.add("ROLE_USER_EXT_3");

		resUserVOs.addAll(userVOs);
		for(int i = 0; i < 30; i++) {
			UserVO userVO = new UserVO();
			userVO.setLogin(String.format("sman_%d_%d", pageNumber, i));
			userVO.setEmail(String.format("sman_%d_%d@smansoft.com", pageNumber, i));
			userVO.setName(String.format("SMan_%d_%d", pageNumber, i));
			userVO.setAddress(String.format("Some Address SMan %d %d", pageNumber, i));
			if((i % 2) == 0) {
				userVO.setSex("Male");
			}
			else {
				userVO.setSex("Female");
			}
			userVO.setPassword(passwordEncoder.encode("12345"));
			userVO.setEnabled(true);
			userVO.setUserRoles(resUserRoles);
			resUserVOs.add(userVO);
		}
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return resUserVOs;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = DEF_USERS_LIST_JSON)
	public @ResponseBody ResponseEntity<List<UserVO>> postListUsesJson(HttpServletRequest request, HttpServletResponse response,
			@RequestBody (required=false) PageRequestVO pageRequestVO, BindingResult errors) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		if(pageRequestVO != null) {
			printToolStr.debug(sessionId, pageRequestVO);
		}
		List<UserVO> userVOs = userServiceBean.getAllUsers(pageRequestVO);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return ResponseEntity.ok(userVOs);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/users/install-data.json")
	public @ResponseBody ResponseEntity<JsonResponce> postInstallDataJson(HttpServletRequest request, HttpServletResponse response,
			@RequestBody (required=false) String emptyData, BindingResult errors) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		
		List<String> resUserRoles = new ArrayList<>();
		resUserRoles.add("ROLE_USER");
		resUserRoles.add("ROLE_USER_EXT_1");
		resUserRoles.add("ROLE_USER_EXT_2");
		resUserRoles.add("ROLE_USER_EXT_3");

		for(int i = 0; i < 350; i++) {
			RegisterVO registerVO = new RegisterVO();
			registerVO.setLogin(String.format("demo_login_%d", i));
			registerVO.setEmail(String.format("demo_login_%d@some.empty.email.com", i));
			registerVO.setName(String.format("SMan_%d", i));
			registerVO.setAddress(String.format("Some Address SMan %d", i));
			if((i % 2) == 0) {
				registerVO.setSex("Male");
			}
			else {
				registerVO.setSex("Female");
			}
			String encodedPassword = passwordEncoder.encode("12345");
			registerVO.setPassword(encodedPassword);
			registerVO.setPasswordConfirm(encodedPassword);
			registerVO.setEnabled(true);
			registerVO.setUserRoles(resUserRoles);
			userServiceBean.createUser(registerVO);
		}
		
		JsonResponce responce = new JsonResponce();
		responce.responce = "ready"; 

		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return ResponseEntity.ok(responce);
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
		ModelAndView modelAndView = new ModelAndView("user_not_found", modelMap);
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
		ModelAndView modelAndView = new ModelAndView("user_error_update", modelMap);
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

	/**
	 * 
	 * @param request
	 * @param response
	 * @param login
	 * @param modelMap
	 * @return
	 */
	//GET "/table_demo.htm"
	@GetMapping(value =  "/table_demo.htm")
	public ModelAndView getTableDemoView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView = new ModelAndView("table_demo", modelMap);
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
	//GET "/menu_demo.htm"
	@GetMapping(value =  "/menu_demo.htm")
	public ModelAndView getMenuDemoView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView = new ModelAndView("menu_demo", modelMap);
		
		springLoginBreadCrumbBean.setPopCount(1);
		String path = springLoginBreadCrumbBean.popPath();

		printToolStr.debug(sessionId, "path = " + path);
		
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
	//GET "/login_demo.htm"
	@GetMapping(value =  "/login_demo.htm")
	public ModelAndView getLoginDemoView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String sessionId = request.getSession().getId();
		printToolStr.debug(sessionId, PrintSfx.SFX_IN);
		ModelAndView modelAndView = new ModelAndView("login_demo", modelMap);
		printToolStr.debug(sessionId, PrintSfx.SFX_OUT);
		return modelAndView;
	}

}
