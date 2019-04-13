/**
 * 
 */
package com.smansoft.sl.persistence.dao.api;

import java.util.List;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
public interface IAuthorityEntityDao extends IEntityDao<AuthorityEntity, Long> {
	
	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "authorityEntityDaoBean";

	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	List<AuthorityEntity> findByUser(UserEntity userEntity) throws DaoException;

}
