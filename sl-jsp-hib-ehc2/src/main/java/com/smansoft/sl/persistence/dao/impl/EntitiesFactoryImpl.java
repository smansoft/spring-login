/**
 * 
 */
package com.smansoft.sl.persistence.dao.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.dao.api.IEntitiesFactory;
import com.smansoft.sl.persistence.dao.api.IEntityDao;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.BaseEntity;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Repository(value = IEntitiesFactory.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class EntitiesFactoryImpl implements IEntitiesFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8377716849610874256L;

	/**
	 * 
	 */
	@Override
	public BaseEntity getEntityByType(IEntityDao<?,?> daoObject) throws DaoException {
		Class<? extends BaseEntity> entityClass = getEntityClassByType(daoObject);
		if(entityClass == null) {
			throw new DaoException(String.format("Error of creating of instance of entity - class not found: %s", daoObject.getClass().getName()));
		}
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new DaoException("InstantiationException", e);
		} catch (IllegalAccessException e) {
			throw new DaoException("IllegalAccessException", e);
		}
	}
	
	/**
	 * 
	 * @param daoObject
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Class<? extends BaseEntity> getEntityClassByType(IEntityDao<?,?> daoObject) throws DaoException {
		if (AuthorityEntityDaoImpl.class == daoObject.getClass()) {
			return AuthorityEntity.class;
		} else if (UserEntityDaoImpl.class == daoObject.getClass()) {
			return UserEntity.class;
		} else {
			throw new DaoException(String.format("DAO of type : %s hasn't been found...", daoObject.getClass().getName()));
		}
	}
}
