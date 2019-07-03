/**
 * 
 */
package com.smansoft.sl.web.converters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.smansoft.sl.persistence.types.UserRoleType;

/**
 * @author SMan
 *
 */
public class UserRoleTypeFormatter implements Formatter<UserRoleType> {

	public static final String DEF_ROLE_ROOT_ADMIN = "ROLE_ROOT_ADMIN";

	public static final String DEF_ROLE_ADMIN = "ROLE_ADMIN";

	public static final String DEF_ROLE_USER = "ROLE_USER";

	public static final String DEF_ROLE_USER_EXT_1 = "ROLE_USER_EXT_1";

	public static final String DEF_ROLE_USER_EXT_2 = "ROLE_USER_EXT_2";

	public static final String DEF_ROLE_USER_EXT_3 = "ROLE_USER_EXT_3";

	/**
	 * 
	 */
	@Override
	public String print(UserRoleType userRole, Locale locale) {
		String res = null;
		switch (userRole) {
			case ROLE_ROOT_ADMIN:
				res = DEF_ROLE_ROOT_ADMIN;
				break;
			case ROLE_ADMIN:
				res = DEF_ROLE_ADMIN;
				break;
			case ROLE_USER:
				res = DEF_ROLE_USER;
				break;
			case ROLE_USER_EXT_1:
				res = DEF_ROLE_USER_EXT_1;
				break;
			case ROLE_USER_EXT_2:
				res = DEF_ROLE_USER_EXT_2;
				break;
			case ROLE_USER_EXT_3:
				res = DEF_ROLE_USER_EXT_3;
				break;
			default:
				res = "";
				break;
		}
		return res;
	}

	/**
	 * 
	 */
	@Override
	public UserRoleType parse(String text, Locale locale) throws ParseException {
		UserRoleType userRole;
		switch(text) {
			case DEF_ROLE_ROOT_ADMIN:
				userRole = UserRoleType.ROLE_ROOT_ADMIN;
				break;
			case DEF_ROLE_ADMIN:
				userRole = UserRoleType.ROLE_ADMIN;
				break;
			case DEF_ROLE_USER:
				userRole = UserRoleType.ROLE_USER;
				break;
			case DEF_ROLE_USER_EXT_1:
				userRole = UserRoleType.ROLE_USER_EXT_1;
				break;
			case DEF_ROLE_USER_EXT_2:
				userRole = UserRoleType.ROLE_USER_EXT_2;
				break;
			case DEF_ROLE_USER_EXT_3:
				userRole = UserRoleType.ROLE_USER_EXT_3;
				break;
			default:
				throw new ParseException(String.format("Error of parsing value: %s", text), 0); 
		}
		return userRole;
	}

}
