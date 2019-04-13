/**
 * 
 */
package com.smansoft.sl.bl.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.smansoft.sl.bl.services.vo.PageRequestVO;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.sl.persistence.dao.IUserRoleRepository;
import com.smansoft.sl.persistence.dao.IUserRepository;
import com.smansoft.sl.persistence.dao.IUserRepositoryPaged;
import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
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
	@Qualifier(IUserRepositoryPaged.DEF_BEAN_NAME)
	private IUserRepositoryPaged userRepositoryPaged;
	

	@Autowired
	@Qualifier(IUserRoleRepository.DEF_BEAN_NAME)
	private IUserRoleRepository userRoleRepository;

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
		UserEntity userEntityTmp = registerVOToUserEntityConverterBean.apply(registerVO);

		userEntity.setUserLogin		(userEntityTmp.getUserLogin());
		userEntity.setUserName		(userEntityTmp.getUserName());
		userEntity.setEmail			(userEntityTmp.getEmail());
		userEntity.setUserAddress	(userEntityTmp.getUserAddress());
		userEntity.setSex			(userEntityTmp.getSex());

		List<UserRoleEntity> userRoleEntities = userEntityTmp.getUserRoles();
		if(userRoleEntities.size() > 0) {
			userEntity.setUserRoles	(userRoleEntities);
			for(UserRoleEntity userRoleEntitiy : userRoleEntities) {
				userRoleEntitiy.setUser(userEntity);
			}
		}

		userEntity.setEnabled		(userEntityTmp.getEnabled());

		String password = registerVO.getPassword();
		if(password != null && password.length() > 0) {
			userEntity.setPassword		(registerVO.getPassword());
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
		userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		return userVOs;
	}

	/**
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<UserVO> getAllUsers(PageRequestVO  pageRequest) {
		List<UserVO> userVOs = null;
		Page<UserEntity> userEntitiesPage = userRepositoryPaged.findAll(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize()));
		List<UserEntity> userEntities = new ArrayList<>(); 
		for(UserEntity userEntity : userEntitiesPage) {
			userEntities.add(userEntity);
		}
		userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		return userVOs;
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
	 * @throws ServicesException
	 * 
	 */
	@Override
	public RegisterVO preProcessPassword(RegisterVO registerVO) throws ServicesException {
		String password = registerVO.getPassword();
		String passwordConfirm = registerVO.getPasswordConfirm();
		if(password != null && password.length() > 0 && passwordConfirm != null && passwordConfirm.length() > 0) {
			if(!password.equals(passwordConfirm)) {
				throw new ServicesException("Password and Password Confirm values aren't equal");
			}
			String encryptedPassword = passwordEncoder.encode(password);
			registerVO.setPassword(encryptedPassword);
			registerVO.setPasswordConfirm(encryptedPassword);
		}
		return registerVO;
	}

}
