/**
 * 
 */
package com.smansoft.sl.persistence.dao.api;

import java.io.Serializable;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.entities.BaseEntity;


/**
 * @author SMan
 *
 */
public interface IEntitiesFactory extends Serializable {
	
	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "entitiesFactoryBean";

	/**
	 * 
	 * @param daoObject
	 * @return
	 * @throws ControllersException 
	 */
	BaseEntity getEntityByType(IEntityDao<?,?> daoObject) throws DaoException;

	/**
	 * 
	 * @param daoObject
	 * @return
	 * @throws DaoException
	 */
	Class<? extends BaseEntity> getEntityClassByType(IEntityDao<?,?> daoObject) throws DaoException;


}
