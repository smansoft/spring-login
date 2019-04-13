/**
 * 
 */
package com.smansoft.sl.web.converters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.tools.common.ExceptionTools;

/**
 * @author SMan
 *
 */
public class UserVOFormatter implements Formatter<UserVO> {

	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;

	/**
	 * 
	 */
	@Override
	public String print(UserVO userVO, Locale locale) {
		return userVO.toString();
	}

	/**
	 * 
	 */
	@Override
	public UserVO parse(String text, Locale locale) throws ParseException {
		String[] userRecords = text.split(",");
		if(userRecords ==  null || userRecords.length == 0) {
			throw new ParseException("wrong value of User record in text format", 0);
		}
		UserVO userVO = null;
		try {
			userVO = userServiceBean.getUserByUserLogin(userRecords[0]);
		} catch (ServicesException e) {
			throw new ParseException(ExceptionTools.stackTraceToString(e), 0);			
		}
		return userVO;
	}

}
