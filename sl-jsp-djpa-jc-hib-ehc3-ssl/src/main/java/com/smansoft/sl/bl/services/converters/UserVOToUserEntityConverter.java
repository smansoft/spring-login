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
@Component(value = UserVOToUserEntityConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserVOToUserEntityConverter implements BaseConverter<UserVO, UserEntity> {

	@SuppressWarnings("unused")
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
		if(userVO != null) {
			userEntity.setUserLogin(userVO.getLogin());
			userEntity.setEmail(userVO.getEmail());
			userEntity.setUserName(userVO.getName());
			userEntity.setUserAddress(userVO.getAddress());
			userEntity.setSex(SexType.valueOf(userVO.getSex()));
			userEntity.setPassword(passwordEncoder.encode(userVO.getPassword()));
			userEntity.setEnabled(userVO.getEnabled());

			List<String> userRoles = userVO.getUserRoles();
			List<UserRoleEntity> userRoleEntities = new ArrayList<>();

			for (String userRole : userRoles) {
				UserRoleEntity userRoleEntity = new UserRoleEntity();
				userRoleEntity.setUserRoleType(UserRoleType.valueOf(userRole));
				userRoleEntity.setUser(userEntity);
				userRoleEntities.add(userRoleEntity);
			}

			userEntity.setUserRoles(userRoleEntities);
		}
		return userEntity;
	}

}
