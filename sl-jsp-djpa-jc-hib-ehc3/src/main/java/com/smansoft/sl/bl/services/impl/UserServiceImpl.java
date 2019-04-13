/**
 * 
 */
package com.smansoft.sl.bl.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.converters.RegisterVOToUserEntityConverter;
import com.smansoft.sl.bl.services.converters.UserEntityToRegisterVOConverter;
import com.smansoft.sl.bl.services.converters.UserEntityToUserVOConverter;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.persistence.dao.IAuthorityRepository;
import com.smansoft.sl.persistence.dao.IUserRepository;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.types.AuthorityType;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Service(value = IUserService.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserServiceImpl implements IUserService {
	
	@SuppressWarnings("unused")
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(UserServiceImpl.class));

	@Autowired
	@Qualifier(IUserRepository.DEF_BEAN_NAME)
	private IUserRepository userRepository;

	@Autowired
	@Qualifier(IAuthorityRepository.DEF_BEAN_NAME)
	private IAuthorityRepository authorityRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	@Qualifier(RegisterVOToUserEntityConverter.DEF_BEAN_NAME)
	private RegisterVOToUserEntityConverter registerVOToUserEntityConverterBean;

	@Autowired
	@Qualifier(UserEntityToUserVOConverter.DEF_BEAN_NAME)
	private UserEntityToUserVOConverter userEntityToUserVOConverterBean;

	@Autowired
	@Qualifier(UserEntityToRegisterVOConverter.DEF_BEAN_NAME)
	private UserEntityToRegisterVOConverter userEntityToRegisterVOConverterBean;

	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager transactionManager;

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO createUser(RegisterVO registerVO) {
		UserEntity userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		userEntity = userRepository.saveAndFlush(userEntity);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO updateUser(RegisterVO registerVO) {
		UserEntity userEntity = userRepository.findByUserLogin(registerVO.getLogin());
		userEntity.setUserName(registerVO.getName());
		userEntity.setEnabled(registerVO.getEnabled());
		String password = registerVO.getPassword();
		if(password != null && password.length() > 0) {
			userEntity.setPassword(passwordEncoder.encode(password));
		}
		List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
		if(authorityEntities != null && authorityEntities.size() > 0) {
			boolean adminFound = false;
			for(int i = (authorityEntities.size() - 1); i >= 0; i--) {
				AuthorityEntity authorityEntity = authorityEntities.get(i);
				if(AuthorityType.RoleAdmin == authorityEntity.getAuthorityType()) {
					if(!registerVO.getIsAdmin()) {
						authorityEntities.remove(i);
					}
					adminFound = true;
					break;
				}
			}
			if(registerVO.getIsAdmin() && !adminFound) {
				AuthorityEntity authorityEntity = new AuthorityEntity();
				authorityEntity.setAuthorityType(AuthorityType.RoleAdmin);
				authorityEntity.setUser(userEntity);
				authorityEntities.add(authorityEntity);
			}
		} else {
			authorityEntities = new ArrayList<AuthorityEntity>();
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
		}
		userEntity = userRepository.saveAndFlush(userEntity);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<UserVO> getAllUsers() {
		List<UserVO> userVOs = null;
		List<UserEntity> userEntities = userRepository.findAll();
		if(userEntities != null) {
			userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		}
		return userVOs;
	}

	/**
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<RegisterVO> getAllRegisters() {
		List<RegisterVO> registerVOs = null;
		List<UserEntity> userEntities = userRepository.findAll();
		if(userEntities != null) {
			registerVOs = userEntityToRegisterVOConverterBean.convertToList(userEntities);
		}
		return registerVOs;
	}

	/**
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserById(Long id) {
		UserEntity userEntity = userRepository.getOne(id);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @param userLogin
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserByUserLogin(String userLogin) {
		UserEntity userEntity = userRepository.findByUserLogin(userLogin);
		userEntity = userRepository.findByUserLogin(userLogin);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @param userLogin
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public RegisterVO getRegisterByUserLogin(String userLogin) {
		UserEntity userEntity = userRepository.findByUserLogin(userLogin);
		RegisterVO registerVO = userEntityToRegisterVOConverterBean.apply(userEntity);
		return registerVO;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<UserVO> getUsersByUserName(String userName) {
		List<UserEntity> userEntities = userRepository.findByUserName(userName);
		List<UserVO> userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		return userVOs;
	}

	/**
	 * 
	 * @param id
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserById(Long id) {
		UserEntity userEntity = userRepository.getOne(id);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		userRepository.deleteById(id);
		return userVO;
	}

	/**
	 * 
	 * @param userLogin
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserByUserLogin(String userLogin) {
		UserEntity userEntity = userRepository.findByUserLogin(userLogin);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		userRepository.deleteByUserLogin(userLogin);
		return userVO;
	}

	/**
	 * 
	 * @param email
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		userRepository.deleteByEmail(email);
		return userVO;
	}

	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean getUserAdmin(UserEntity userEntity) {
		boolean isAdmin = false;
		List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
		for(AuthorityEntity authorityEntity : authorityEntities) {
			if(AuthorityType.RoleAdmin == authorityEntity.getAuthorityType()) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}
}
