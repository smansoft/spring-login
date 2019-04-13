/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.UserRoleType;

/**
 * @author SMan
 *
 */
@Component(value = UserEntityToUserVOConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserEntityToUserVOConverter implements BaseConverter<UserEntity, UserVO> {

	
	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "userEntityToUserVOConverterBean";

	
	/**
	 * 
	 */
	@Override
	public UserVO apply(UserEntity userEntity) {
		UserVO userVO = new UserVO();

		userVO.setLogin(userEntity.getUserLogin());
		userVO.setEmail(userEntity.getEmail());
		userVO.setName(userEntity.getUserName());
		userVO.setAddress(userEntity.getUserAddress());
		userVO.setSex(userEntity.getSex().toString());
		userVO.setPassword(userEntity.getPassword());
		userVO.setEnabled(userEntity.getEnabled());

		List<UserRoleEntity> userRoleEntities = userEntity.getUserRoles();
		List<String> userRoles = new ArrayList<>();

		for (UserRoleEntity userRoleEntity : userRoleEntities) {
			UserRoleType userRole = userRoleEntity.getUserRoleType();
			userRoles.add(userRole.toString());
		}

		userVO.setUserRoles(userRoles);

		return userVO;
	}

}
