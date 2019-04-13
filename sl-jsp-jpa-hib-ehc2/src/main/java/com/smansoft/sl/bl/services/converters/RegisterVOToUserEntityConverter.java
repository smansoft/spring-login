/**
 * 
 */
package com.smansoft.sl.bl.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.AuthorityType;

/**
 * @author SMan
 *
 */
@Component(value = RegisterVOToUserEntityConverter.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class RegisterVOToUserEntityConverter implements BaseConverter<RegisterVO, UserEntity> {

	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "registerVOToUserEntityConverterBean";
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 
	 */
	@Override
	public UserEntity apply(RegisterVO registerVO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserLogin(registerVO.getLogin());
		userEntity.setEmail(registerVO.getEmail());
		userEntity.setUserName(registerVO.getName());

		userEntity.setPassword(passwordEncoder.encode(registerVO.getPassword()));
		userEntity.setEnabled(registerVO.getEnabled());

		List<AuthorityEntity> authorityEntities = new ArrayList<>();

		AuthorityEntity authorityEntity = new AuthorityEntity();
		authorityEntity.setAuthorityType(AuthorityType.RoleUser);
		authorityEntity.setUser(userEntity);
		authorityEntities.add(authorityEntity);
		if (registerVO.getIsAdmin()) {
			authorityEntity = new AuthorityEntity();
			authorityEntity.setAuthorityType(AuthorityType.RoleAdmin);
			authorityEntity.setUser(userEntity);
			authorityEntities.add(authorityEntity);
		}
		userEntity.setAuthorities(authorityEntities);
		return userEntity;
	}

}
