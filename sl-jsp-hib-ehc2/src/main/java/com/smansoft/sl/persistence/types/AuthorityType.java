/**
 * 
 */
package com.smansoft.sl.persistence.types;

import com.smansoft.sl.exceptions.InitException;

/**
 * @author SMan
 *
 */
public enum AuthorityType {

	RoleAdmin(0), RoleUser(1);

	/**
	 * 
	 */
	private int authorityTypeValue;

	/**
	 * 
	 * @param authorityType
	 */
	AuthorityType(int authorityTypeValue) {
		this.authorityTypeValue = authorityTypeValue;
	}

	/**
	 * 
	 * @return
	 */
	public int getAuthorityTypeValue() {
		return this.authorityTypeValue;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		String res = null;
		switch (authorityTypeValue) {
		case 0:
			res = "ROLE_ADMIN";
			break;
		case 1:
			res = "ROLE_USER";
			break;
		}
		return res;
	}

	/**
	 * 
	 * @param authorityTypeStr
	 * @return
	 * @throws InitException
	 */
	public static AuthorityType getAuthorityType(String authorityTypeStr) throws InitException {
		AuthorityType authorityType = null;
		String authorityTypeUp = authorityTypeStr.toUpperCase();
		switch (authorityTypeUp) {
		case "ROLE_ADMIN": {
			authorityType = RoleAdmin;
			break;
		}
		case "ROLE_USER": {
			authorityType = RoleUser;
			break;
		}
		default:
			throw new InitException(String.format("Wrong authorityTypeStr value : %s", authorityTypeStr));
		}
		return authorityType;
	}

	/**
	 * 
	 * @param authorityTypeValue
	 * @return
	 * @throws InitException
	 */
	public static AuthorityType valueOf(int authorityTypeValue) throws InitException {
		AuthorityType authorityType = null;
		switch (authorityTypeValue) {
		case 0:
			authorityType = RoleAdmin;
			break;
		case 1:
			authorityType = RoleUser;
			break;
		default:
			throw new InitException(String.format("Wrong authorityType value : %d", authorityTypeValue));
		}
		return authorityType;
	}

}
