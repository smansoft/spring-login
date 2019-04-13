/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.InitException;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.AuthorityType;
import com.smansoft.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Component(value = UserVOToUserEntityConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserVOToUserEntityConverter implements BaseConverter<UserVO, UserEntity> {

	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(UserVOToUserEntityConverter.class));

	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "userVOToUserEntityConverterBean";
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Override
	public UserEntity apply(UserVO userVO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserLogin(userVO.getLogin());
		userEntity.setEmail(userVO.getEmail());
		userEntity.setUserName(userVO.getName());
		userEntity.setPassword(passwordEncoder.encode(userVO.getPassword()));
		userEntity.setEnabled(userVO.getEnabled());
		List<String> authorities = userVO.getAuthorities();
		List<AuthorityEntity> authorityEntities = new ArrayList<>();
		for (String authority : authorities) {
			AuthorityEntity authorityEntity = new AuthorityEntity();
			try {
				authorityEntity.setAuthorityType(AuthorityType.getAuthorityType(authority));
			} catch (InitException e) {
				printToolStr.error(ExceptionTools.stackTraceToString(e), e);
			}
			authorityEntity.setUser(userEntity);
		}
		userEntity.setAuthorities(authorityEntities);
		return userEntity;
	}

}
