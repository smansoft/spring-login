/**
 * 
 */
package com.smansoft.sl.web.converters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import com.smansoft.sl.persistence.types.SexType;

/**
 * @author SMan
 *
 */
public class SexTypeFormatter implements Formatter<SexType> {

	public static final String DEF_NOT_DEFINED = "Not defined";

	public static final String DEF_MALE = "Male";

	public static final String DEF_FEMALE = "Female";

	/**
	 * 
	 */
	@Override
	public String print(SexType sexType, Locale locale) {
		String res = null;
		switch (sexType) {
		case NotDefined:
			res = DEF_NOT_DEFINED;
			break;
		case Male:
			res = DEF_MALE;
			break;
		case Female:
			res = DEF_FEMALE;
			break;
		}
		return res;
	}

	/**
	 * 
	 */
	@Override
	public SexType parse(String text, Locale locale) throws ParseException {
		SexType sexType;
		switch(text) {
			case DEF_NOT_DEFINED:
				sexType = SexType.NotDefined;
				break;
			case DEF_MALE:
				sexType = SexType.Male;
				break;
			case DEF_FEMALE:
				sexType = SexType.Female;
				break;
			default:
				throw new ParseException(String.format("Error of parsing value: %s", text), 0); 
		}
		return sexType;
	}

}
