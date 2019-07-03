/**
 * 
 */
package com.smansoft.sl.web.converters;

import org.springframework.core.convert.converter.Converter;

import com.smansoft.sl.persistence.types.UserRoleType;

/**
 * @author SMan
 *
 */
public class UserRoleTypeToStringConverter implements Converter<UserRoleType, String> {

	/**
	 * 
	 */
	@Override
	public String convert(UserRoleType userRoleType) {
		return userRoleType.toString();
	}

}
