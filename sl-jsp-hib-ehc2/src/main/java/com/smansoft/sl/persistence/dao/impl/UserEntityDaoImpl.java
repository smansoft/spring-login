/**
 * 
 */
package com.smansoft.sl.persistence.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.dao.api.IUserEntityDao;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.tools.common.ExceptionTools;

/**
 * @author SMan
 *
 */
@Repository(value = IUserEntityDao.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserEntityDaoImpl extends EntityDaoImpl<UserEntity, Long> implements IUserEntityDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -897498033249041069L;

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public UserEntity findByUserLogin(String userLogin) throws DaoException {
		try {
			Query<UserEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(UserEntity.DEF_FIND_USER_BY_USER_LOGIN, UserEntity.class);
			query.setParameter("userLogin", userLogin);
			return query.setCacheable(true).uniqueResult();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public UserEntity findByEmail(String email) throws DaoException {
		try {
			Query<UserEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(UserEntity.DEF_FIND_USER_BY_EMAIL, UserEntity.class);
			query.setParameter("email", email);
			return query.setCacheable(true).uniqueResult();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public List<UserEntity> findByUserName(String userName) throws DaoException {
		try {
			Query<UserEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(UserEntity.DEF_FIND_USER_BY_USER_NAME, UserEntity.class);
			query.setParameter("userName", userName);
			return query.setCacheable(true).list();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * @throws DaoException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void deleteByUserLogin(String userLogin) throws DaoException {
		try {
			Query<UserEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(UserEntity.DEF_DELETE_USER_BY_USER_LOGIN).setCacheable(true);
			query.setParameter("userLogin", userLogin);
			query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * @throws DaoException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void deleteByEmail(String email) throws DaoException {
		try {
			Query<UserEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(UserEntity.DEF_DELETE_USER_BY_EMAIL).setCacheable(true);
			query.setParameter("email", email);
			query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}
}
