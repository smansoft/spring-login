/**
 * 
 */
package com.smansoft.sl.bl.services.api;

import java.util.List;

import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;

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
	 * @param registerVO
	 * @return
	 * @throws Throwable
	 */
	UserVO createUser(RegisterVO registerVO) throws Throwable;

	/**
	 * 
	 * @param registerVO
	 * @return
	 */
	UserVO updateUser(RegisterVO registerVO);

	/**
	 * 
	 * @return
	 */
	List<UserVO> getAllUsers();
	

	/**
	 * 
	 * @return
	 */
	List<RegisterVO> getAllRegisters();

	/**
	 * 
	 * @param id
	 * @return
	 */
	UserVO getUserById(Long id);

	/**
	 * @param userLogin
	 * @return
	 */
	UserVO getUserByUserLogin(String userLogin);

	/**
	 * @param userLogin
	 * @return
	 */
	RegisterVO getRegisterByUserLogin(String userLogin);

	/**
	 * 
	 * @param email
	 * @return
	 */
	UserVO getUserByEmail(String email);

	/**
	 * 
	 * @param userName
	 * @return
	 */
	List<UserVO> getUsersByUserName(String userName);

	/**
	 * 
	 * @param id
	 * @return
	 */
	UserVO deleteUserById(Long id);

	/**
	 * 
	 * @param userLogin
	 * @return
	 */
	UserVO deleteUserByUserLogin(String userLogin);

	/**
	 * 
	 * @param email
	 * @return
	 */
	UserVO deleteUserByEmail(String email);
}
