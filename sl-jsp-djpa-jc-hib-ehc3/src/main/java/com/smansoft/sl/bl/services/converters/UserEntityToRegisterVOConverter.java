/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.AuthorityType;

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
		registerVO.setLogin(userEntity.getUserLogin());
		registerVO.setEmail(userEntity.getEmail());
		registerVO.setName(userEntity.getUserName());
		registerVO.setPassword(userEntity.getPassword());
		registerVO.setPasswordConfirm(userEntity.getPassword());
		registerVO.setEnabled(userEntity.getEnabled());
		List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
		registerVO.setIsAdmin(false);
		for(AuthorityEntity authorityEntity : authorityEntities) {
			if(authorityEntity.getAuthorityType() == AuthorityType.RoleAdmin) {
				registerVO.setIsAdmin(true);
				break;
			}
		}
		return registerVO;
	}

}
