/**
 * 
 */
package com.smansoft.sl.persistence.types;

import com.smansoft.sl.exceptions.InitException;

/**
 * @author SMan
 *
 */
public enum UserRoleType {

	ROLE_ROOT_ADMIN(0), ROLE_ADMIN(1), ROLE_USER(2), ROLE_USER_EXT_1(3), ROLE_USER_EXT_2(4), ROLE_USER_EXT_3(5);

	/**
	 * 
	 */
	private int userRoleTypeValue;

	/**
	 * 
	 * @param userRoleTypeValue
	 */
	UserRoleType(int userRoleTypeValue) {
		this.userRoleTypeValue = userRoleTypeValue;
	}

	/**
	 * 
	 * @return
	 */
	public int getRoleTypeValue() {
		return this.userRoleTypeValue;
	}

	/**
	 * 
	 * @param userRoleTypeValue
	 * @return
	 * @throws InitException
	 */
	public static UserRoleType valueOf(int userRoleTypeValue) throws InitException {
		UserRoleType userRoleType = null;
		switch (userRoleTypeValue) {
		case 0:
			userRoleType = ROLE_ROOT_ADMIN;
			break;
		case 1:
			userRoleType = ROLE_ADMIN;
			break;
		case 2:
			userRoleType = ROLE_USER;
			break;
		case 3:
			userRoleType = ROLE_USER_EXT_1;
			break;
		case 4:
			userRoleType = ROLE_USER_EXT_2;
			break;
		case 5:
			userRoleType = ROLE_USER_EXT_3;
			break;
		default:
			throw new InitException(String.format("Wrong userRoleType value : %d", userRoleTypeValue));
		}
		return userRoleType;
	}

}
