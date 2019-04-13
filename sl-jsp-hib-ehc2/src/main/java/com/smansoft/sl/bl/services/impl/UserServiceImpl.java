/**
 * 
 */
package com.smansoft.sl.bl.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.sl.persistence.dao.api.IAuthorityEntityDao;
import com.smansoft.sl.persistence.dao.api.IUserEntityDao;
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
@Service(value = IUserService.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserServiceImpl implements IUserService {
	
	@SuppressWarnings("unused")
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(UserServiceImpl.class));

	@Autowired
	@Qualifier(IAuthorityEntityDao.DEF_BEAN_NAME)
	private IAuthorityEntityDao authorityEntityDaoBean;

	@Autowired
	@Qualifier(IUserEntityDao.DEF_BEAN_NAME)
	private IUserEntityDao userEntityDaoBean;

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

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO createUser(RegisterVO registerVO) throws ServicesException {
		UserEntity userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		try {
			userEntityDaoBean.save(userEntity);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO updateUser(RegisterVO registerVO) throws ServicesException {
		UserEntity userEntity = null;
		try {
			userEntity = userEntityDaoBean.findByUserLogin(registerVO.getLogin());
		} catch (DaoException e) {
			e.printStackTrace();
		}
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
		try {
			userEntityDaoBean.save(userEntity);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);			
		}
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<UserVO> getAllUsers() throws ServicesException {
		List<UserVO> userVOs = null;
		List<UserEntity> userEntities = null;
		try {
			userEntities = userEntityDaoBean.findAll();
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		if(userEntities != null) {
			userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		}
		return userVOs;
	}

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<RegisterVO> getAllRegisters() throws ServicesException {
		List<RegisterVO> registerVOs = null;
		List<UserEntity> userEntities = null;
		try {
			userEntities = userEntityDaoBean.findAll();
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);			
		}
		if(userEntities != null) {
			registerVOs = userEntityToRegisterVOConverterBean.convertToList(userEntities);
		}
		return registerVOs;
	}

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserById(Long id) throws ServicesException {
		UserEntity userEntity = null;
		try {
			userEntity = userEntityDaoBean.findById(id).get();
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @param userLogin
	 * @return
	 * @throws ServicesException 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserByUserLogin(String userLogin) throws ServicesException {
		
		UserEntity userEntity = null;
		try {
			userEntity = userEntityDaoBean.findByUserLogin(userLogin);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);			
		}
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @param userLogin
	 * @return
	 * @throws ServicesException 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public RegisterVO getRegisterByUserLogin(String userLogin) throws ServicesException {
		UserEntity userEntity = null;
		try {
			userEntity = userEntityDaoBean.findByUserLogin(userLogin);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		RegisterVO registerVO = userEntityToRegisterVOConverterBean.apply(userEntity);
		return registerVO;
	}

	/**
	 * 
	 * @param email
	 * @return
	 * @throws ServicesException 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO getUserByEmail(String email) throws ServicesException {
		UserEntity userEntity = null;
		try {
			userEntity = userEntityDaoBean.findByEmail(email);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);			
		}
		UserVO userVO = userEntityToUserVOConverterBean.apply(userEntity);
		return userVO;
	}

	/**
	 * @throws ServicesException 
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public List<UserVO> getUsersByUserName(String userName) throws ServicesException {
		
		List<UserEntity> userEntities = null;
		try {
			userEntities = userEntityDaoBean.findByUserName(userName);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);			
		}
		List<UserVO> userVOs = userEntityToUserVOConverterBean.convertToList(userEntities);
		return userVOs;
	}

	/**
	 * 
	 * @param id
	 * @throws ServicesException 
	 * @throws DaoException 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserById(Long id) throws ServicesException {
		UserEntity userEntity = null;
		UserVO userVO = null;
		try {
			userEntity = userEntityDaoBean.findById(id).get();
			userVO = userEntityToUserVOConverterBean.apply(userEntity);
			userEntityDaoBean.deleteById(id);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}		
		return userVO;
	}

	/**
	 * 
	 * @param userLogin
	 * @throws ServicesException 
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserByUserLogin(String userLogin) throws ServicesException {
		UserEntity userEntity = null;
		UserVO userVO = null;
		try {
			userEntity = userEntityDaoBean.findByUserLogin(userLogin);
			userVO = userEntityToUserVOConverterBean.apply(userEntity);
			userEntityDaoBean.deleteByUserLogin(userLogin);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}			
		return userVO;
	}

	/**
	 * 
	 * @param email
	 * @throws ServicesException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserVO deleteUserByEmail(String email) throws ServicesException {
		UserEntity userEntity = null;
		UserVO userVO = null;
		try {
			userEntity = userEntityDaoBean.findByEmail(email);
			userVO = userEntityToUserVOConverterBean.apply(userEntity);
		} catch (DaoException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}			
		return userVO;
	}

}
