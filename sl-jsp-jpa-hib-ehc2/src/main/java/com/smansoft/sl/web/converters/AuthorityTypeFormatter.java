/**
 * 
 */
package com.smansoft.sl.web.converters;

import java.text.ParseException;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import com.smansoft.sl.exceptions.InitException;
import com.smansoft.sl.persistence.types.AuthorityType;
import com.smansoft.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
public class AuthorityTypeFormatter implements Formatter<AuthorityType> {

	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(AuthorityTypeFormatter.class));

	/**
	 * 
	 */
	@Override
	public String print(AuthorityType object, Locale locale) {
		return object.toString();
	}

	/**
	 * 
	 */
	@Override
	public AuthorityType parse(String text, Locale locale) throws ParseException {
		AuthorityType res = null;
		try {
			res = AuthorityType.getAuthorityType(text);
		} catch (InitException e) {
			printToolStr.error(ExceptionTools.stackTraceToString(e), e);
		}
		return res;
	}

}
