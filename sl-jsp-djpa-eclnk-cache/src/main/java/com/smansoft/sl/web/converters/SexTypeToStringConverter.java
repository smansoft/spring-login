/**
 * 
 */
package com.smansoft.sl.web.converters;

import org.springframework.core.convert.converter.Converter;

import com.smansoft.sl.persistence.types.SexType;

/**
 * @author SMan
 *
 */
public class SexTypeToStringConverter implements Converter<SexType, String> {

	/**
	 * 
	 */
	@Override
	public String convert(SexType sexType) {
		return sexType.toString();
	}

}
