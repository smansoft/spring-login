/**
 * 
 */
package com.smansoft.sl.web.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;


/**
 * @author SMan
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class BaseController {

	@SuppressWarnings("unused")
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(BaseController.class));

	public static final String DEF_USER_LOGIN_HTM = "/login.htm";

	public static final String DEF_USER_LOGOUT_HTM = "/logout.htm";

	public static final String DEF_USER_REGISTER_HTM = "/register.htm";

	public static final String DEF_USER_REGISTERED_HTM = "/registered.htm";


	public static final String DEF_USER_INFO_HTM = "/user/info.htm";
	
	public static final String DEF_USER_EDIT_HTM = "/user/edit.htm";
	
	public static final String DEF_USER_DEL_HTM = "/user/delete.htm";

	public static final String DEF_USER_DELETED_HTM = "/user/deleted.htm";
	
	public static final String DEF_USER_INFO_ERROR_HTM = "/user/info_error.htm";


	public static final String DEF_USERS_LIST_HTM = "/users/list.htm";

	public static final String DEF_USERS_LOGIN_INFO_HTM = "/users/{login}/info.htm";

	public static final String DEF_USERS_LOGIN_EDIT_HTM = "/users/{login}/edit.htm";

	public static final String DEF_USERS_LOGIN_DEL_HTM = "/users/{login}/delete.htm";


	public static final String DEF_USER_NOT_FOUND_HTM = "/user/user_not_found.htm";

	public static final String DEF_USER_ERROR_UPDATE_HTM = "/user/user_error_update.htm";

	public static final String DEF_ERROR_HTM = "/error.htm";

}
