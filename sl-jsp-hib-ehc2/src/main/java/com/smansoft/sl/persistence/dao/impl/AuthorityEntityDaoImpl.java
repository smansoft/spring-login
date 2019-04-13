/**
 * 
 */
package com.smansoft.sl.persistence.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.dao.api.IAuthorityEntityDao;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.tools.common.ExceptionTools;

/**
 * @author SMan
 *
 */
@Repository(value = IAuthorityEntityDao.DEF_BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class AuthorityEntityDaoImpl extends EntityDaoImpl<AuthorityEntity, Long> implements IAuthorityEntityDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4848577649438014014L;

	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public List<AuthorityEntity> findByUser(UserEntity userEntity) throws DaoException {
		try {
			Query<AuthorityEntity> query = sessionFactory.getCurrentSession()
					.createNamedQuery(AuthorityEntity.DEF_FIND_AUTHORITIES_BY_USER,AuthorityEntity.class);
			query.setParameter("user", userEntity);
			return query.setCacheable(true).list();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

}
