/**
 * 
 */
package com.smansoft.sl.persistence.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.dao.api.IEntitiesFactory;
import com.smansoft.sl.persistence.dao.api.IEntityDao;
import com.smansoft.sl.persistence.entities.BaseEntity;
import com.smansoft.tools.common.ExceptionTools;

/**
 * @author SMan
 *
 */
public abstract class EntityDaoImpl<T extends BaseEntity,ID  extends Serializable> implements IEntityDao<T,ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6088508000900596934L;

	/**
	 * 
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 
	 */
	@Autowired
	@Qualifier(IEntitiesFactory.DEF_BEAN_NAME)
	protected IEntitiesFactory entitiesFactorytBean;
	

	/**
	 * 
	 * @return
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * 	//Flushes all pending changes to the database.
	 */
	@Override
	public void	flush() {
		entityManager.flush();
	}

	/**
	 * 
	 */
	@Override
	public Boolean isPersist(T entity) {
		return entityManager.contains(entity);
	}

	///---------------------------------------------------------------------
	/// org.springframework.data.jpa.repository.JpaRepository<T,ID>
	/// org.springframework.data.repository.Interface CrudRepository<T,ID>
	
	/**
	 * //Deletes all entities in a batch call.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	deleteAllInBatch() throws DaoException {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
			@SuppressWarnings("unused")
			Root<T> root = criteriaDelete.from(entityClass);
			entityManager.createQuery(criteriaDelete).setHint("org.hibernate.cacheable", true).executeUpdate();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * //Deletes the given entities in a batch which means it will create a single Query.
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	deleteInBatch(Iterable<T> entities) throws DaoException {
		try {
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
			Root<T> root = criteriaDelete.from(entityClass);
			Path<ID> idPath = root.get("id");
			List<ID> idList = new ArrayList<ID>();
			for(T entity : entities) {
				idList.add((ID)entity.getId());
			}
			Predicate inPredicate = idPath.in(idList.toArray());
			criteriaDelete.where(inPredicate);
			entityManager.createQuery(criteriaDelete).setHint("org.hibernate.cacheable", true).executeUpdate();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}		
	}

	/**
	 * //Deletes a given entity.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	delete(T entity) throws DaoException {
		try {
			entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * //Deletes all entities managed by the repository.
	 * @throws DaoException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	deleteAll() throws DaoException {
		List<T> entities = findAll();
		for(T entity : entities) {
			delete(entity);
		}
	}

	/**
	 * //Deletes the given entities.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	deleteAll(Iterable<T> entities) throws DaoException {
		for (T entity : entities) {
			delete(entity);
		}
	}
	
	/**
	 * //Deletes the entity with the given id.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public void	deleteById(ID id) throws DaoException {
		delete(findById(id).get());
	}
	
	/**
	 * //Returns the number of entities available.
	 * @throws DaoException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public Long	count() throws DaoException {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
			return entityManager.createQuery(criteriaQuery).setHint("org.hibernate.cacheable", true).getSingleResult();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * //Returns whether an entity with the given id exists.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public Boolean	existsById(ID id) throws DaoException {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<T> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
			criteriaQuery.where(criteriaBuilder.equal(root.get("id"),id));
			return (entityManager.createQuery(criteriaQuery).setHint("org.hibernate.cacheable", true).getSingleResult() > 0);
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * @throws DaoException 
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@SuppressWarnings("unchecked")
	@Override
	public List<T>	findAll() throws DaoException {
		try {
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass);
			Root<T> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(root);
			return entityManager.createQuery(criteriaQuery).setHint("org.hibernate.cacheable", true).getResultList();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * @throws DaoException 
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public List<T>	findAllById(Iterable<ID> ids) throws DaoException {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) entitiesFactorytBean.getEntityClassByType(this);
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(root);
			Path<ID> idPath = root.get("id");
			List<ID> idList = new ArrayList<ID>();
			for(ID id : ids) {
				idList.add(id);
			}
			Predicate inPredicate = idPath.in(idList.toArray());
			criteriaQuery.where(inPredicate);
			return entityManager.createQuery(criteriaQuery).setHint("org.hibernate.cacheable", true).getResultList();
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * //Retrieves an entity by its id.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@SuppressWarnings("unchecked")
	@Override
	public Optional<T>	findById(ID id) throws DaoException {
		try {
			return Optional.ofNullable((T)entityManager.find(entitiesFactorytBean.getEntityClassByType(this), id));
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * 	//Returns a reference to the entity with the given identifier.
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public T getOne(ID id) throws DaoException {
		T entity = null;
		try {
			entity = (T)entityManager.find(entitiesFactorytBean.getEntityClassByType(this), id);
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
		if(entity == null) {
			throw new DaoException(String.format("Entity with id = %s hasn't been found",id));
		}
		return entity;
	}

	/**
	 * @throws DaoException
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public	List<T>	saveAll(Iterable<T> entities) throws DaoException {
		List<T> result = new ArrayList<T>();
		for (T entity : entities) {
			result.add(save(entity));
		}
		return result;
	}

	/**
	 * 	//Saves an entity and flushes changes instantly.
	 * @throws DaoException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public T saveAndFlush(T entity) throws DaoException {
		try {
			entity = save(entity);
			entityManager.flush();
			return entity;
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

	/**
	 * //Saves a given entity.
	 * @throws DaoException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = DaoException.class)
	@Override
	public T save(T entity) throws DaoException {
		try {
			if(!entityManager.contains(entity)) {
				entityManager.persist(entity);
			} else {
				entity = (T)entityManager.merge(entity);
			}
			return entity;
		} catch (Exception e) {
			throw new DaoException(ExceptionTools.stackTraceToString(e), e);
		}
	}

}
