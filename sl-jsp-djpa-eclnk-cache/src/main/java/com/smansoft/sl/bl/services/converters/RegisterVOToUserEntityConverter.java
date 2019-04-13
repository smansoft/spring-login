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

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.UserRoleType;
import com.smansoft.sl.persistence.types.SexType;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Component(value = RegisterVOToUserEntityConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class RegisterVOToUserEntityConverter implements BaseConverter<RegisterVO, UserEntity> {
	
	@SuppressWarnings("unused")
	private static final IPrintToolStr printTool = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(RegisterVOToUserEntityConverter.class));

	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "registerVOToUserEntityConverterBean";
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * kl9
	 */
	@Override
	public UserEntity apply(RegisterVO registerVO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserLogin(registerVO.getLogin());
		userEntity.setEmail(registerVO.getEmail());
		userEntity.setUserName(registerVO.getName());
		userEntity.setUserAddress(registerVO.getAddress());
		userEntity.setSex(SexType.valueOf(registerVO.getSex()));
		userEntity.setPassword(registerVO.getPassword());
		userEntity.setEnabled(registerVO.getEnabled());

		List<UserRoleEntity> userRoleEntities = new ArrayList<>();
		List<String> userRoles = registerVO.getUserRoles();
		for(String authority : userRoles) {
			UserRoleEntity userRoleEntity = new UserRoleEntity();
			userRoleEntity.setUser(userEntity);
			userRoleEntity.setUserRoleType(UserRoleType.valueOf(authority));
			userRoleEntities.add(userRoleEntity);
		}
		userEntity.setUserRoles(userRoleEntities);

		return userEntity;
	}

}
