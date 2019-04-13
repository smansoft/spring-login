/**
 * 
 */
package com.smansoft.sl.persistence.dao.api;

import java.util.List;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
public interface IUserEntityDao extends IEntityDao<UserEntity, Long> {

	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "userEntityDaoBean";

	/**
	 * 
	 * @param login
	 * @return
	 */
	UserEntity findByUserLogin(String userLogin) throws DaoException;

	/**
	 * 
	 * @param email
	 * @return
	 */
	UserEntity findByEmail(String email) throws DaoException;

	/**
	 * 
	 * @param userName
	 * @return
	 */
	List<UserEntity> findByUserName(String userName) throws DaoException;

	/**
	 * 
	 * @param login
	 * @return
	 */
	void deleteByUserLogin(String userLogin) throws DaoException;

	/**
	 * 
	 * @param email
	 * @return
	 */
	void deleteByEmail(String email) throws DaoException;

}
