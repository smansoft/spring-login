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
public class StringToSexTypeConverter implements Converter<String,SexType> {

	/**
	 * 
	 */
	@Override
	public SexType convert(String sexTypeStr) {
		return SexType.valueOf(sexTypeStr);
	}

}
