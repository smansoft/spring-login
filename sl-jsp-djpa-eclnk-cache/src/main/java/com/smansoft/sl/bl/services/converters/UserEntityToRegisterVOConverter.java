/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Component(value = UserEntityToRegisterVOConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserEntityToRegisterVOConverter implements BaseConverter<UserEntity, RegisterVO> {

	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "userEntityToRegisterVOConverterBean";

	/**
	 * 
	 */
	@Override
	public RegisterVO apply(UserEntity userEntity) {
		RegisterVO registerVO = new RegisterVO();
		if(userEntity != null) {
			registerVO.setLogin(userEntity.getUserLogin());
			registerVO.setEmail(userEntity.getEmail());
			registerVO.setName(userEntity.getUserName());
			registerVO.setSex(userEntity.getSex().toString());
			registerVO.setAddress(userEntity.getUserAddress());
			registerVO.setPassword(userEntity.getPassword());
			registerVO.setPasswordConfirm(userEntity.getPassword());
			registerVO.setEnabled(userEntity.getEnabled());

			List<String> userRoles = registerVO.getUserRoles();
			List<UserRoleEntity> userRoleEntities = userEntity.getUserRoles();

			for(UserRoleEntity userRoleEntity : userRoleEntities) {
				userRoles.add(userRoleEntity.getUserRoleType().toString());
			}
		}
		return registerVO;
	}

}
