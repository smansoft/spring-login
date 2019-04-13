/**
 * 
 */
package com.smansoft.sl.web.converters;

import org.springframework.core.convert.converter.Converter;

import com.smansoft.sl.persistence.types.AuthorityType;

/**
 * @author SMan
 *
 */
public class AuthorityTypeToStringConverter implements Converter<AuthorityType, String> {
	
	/**
	 * 
	 */
	@Override
	public String convert(AuthorityType authorityType) {
		return authorityType.toString();
	}

}
