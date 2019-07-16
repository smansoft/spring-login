/**
 * 
 */
package com.smansoft.sl.bl.services.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.sl.persistence.dao.api.IAuthorityEntityDao;
import com.smansoft.sl.persistence.dao.api.IUserEntityDao;

/**
 * @author SMan
 *
 */
public interface IUserService {

	/**
	 * 
	 */
	static final String DEF_BEAN_NAME = "userServiceBean";
	
	/**
	 * 
	 * @param authorityEntityDaoBean
	 */
	void setAuthorityEntityDaoBean(IAuthorityEntityDao authorityEntityDaoBean);
	
	/**
	 * 
	 * @return
	 */
	IAuthorityEntityDao getAuthorityEntityDaoBean();

	/**
	 * 
	 * @param userEntityDaoBean
	 */
	void setUserEntityDaoBean(IUserEntityDao userEntityDaoBean);
	
	/**
	 * 
	 * @return
	 */
	IUserEntityDao getUserEntityDaoBean();	

	/**
	 * 
	 * @param registerVO
	 * @return
	 * @throws Throwable
	 */
	UserVO createUser(RegisterVO registerVO) throws ServicesException;

	/**
	 * 
	 * @param registerVO
	 * @return
	 * @throws ServicesException 
	 */
	UserVO updateUser(RegisterVO registerVO) throws ServicesException;

	/**
	 * 
	 * @return
	 * @throws ServicesException 
	 */
	List<UserVO> getAllUsers() throws ServicesException;

	/**
	 * 
	 * @return
	 * @throws ServicesException 
	 */
	List<RegisterVO> getAllRegisters() throws ServicesException;

	/**
	 * @param userLogin
	 * @return
	 * @throws ServicesException 
	 */
	UserVO getUserByUserLogin(String userLogin) throws ServicesException;

	/**
	 * @param userLogin
	 * @return
	 * @throws ServicesException 
	 */
	RegisterVO getRegisterByUserLogin(String userLogin) throws ServicesException;

	/**
	 * 
	 * @param email
	 * @return
	 * @throws ServicesException 
	 */
	UserVO getUserByEmail(String email) throws ServicesException;

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws ServicesException 
	 */
	List<UserVO> getUsersByUserName(String userName) throws ServicesException;

	/**
	 * 
	 * @param userLogin
	 * @return
	 * @throws ServicesException 
	 */
	UserVO deleteUserByUserLogin(String userLogin) throws ServicesException;

	/**
	 * 
	 * @param email
	 * @return
	 * @throws ServicesException 
	 */
	UserVO deleteUserByEmail(String email) throws ServicesException;

}
