/**
 * 
 */
package com.smansoft.sl.persistence.dao.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.entities.BaseEntity;

/**
 * @author SMan
 * 
 */
public interface IEntityDao<T extends BaseEntity, ID  extends Serializable> extends Serializable {

	SessionFactory getSessionFactory();
	
	Boolean 	isPersist(T entity);
	void		flush();													//	Flushes all pending changes to the database.

	void		deleteAllInBatch() throws DaoException;						//	Deletes all entities in a batch call.
	void		deleteInBatch(Iterable<T> entities) throws DaoException;	//	Deletes the given entities in a batch which means it will create a single Query.
	
	void		deleteAll() throws DaoException;							//	Deletes all entities managed by the repository.
	void		deleteAll(Iterable<T> entities) throws DaoException;		//	Deletes the given entities.
	void		deleteById(ID id) throws DaoException;						//	Deletes the entity with the given id.
	
	void		delete(T entity) throws DaoException;						//	Deletes a given entity.

	Long		count() throws DaoException;								//	Returns the number of entities available.

	Boolean		existsById(ID id) throws DaoException;						//	Returns whether an entity with the given id exists.

	List<T>		findAll() throws DaoException;
	List<T>		findAllById(Iterable<ID> ids) throws DaoException;			//
	Optional<T>	findById(ID id) throws DaoException;						// Retrieves an entity by its id.

	T			getOne(ID id) throws DaoException;							//	Returns a reference to the entity with the given identifier.

	List<T>		saveAll(Iterable<T> entities) throws DaoException;
	T			saveAndFlush(T entity) throws DaoException;					//	Saves an entity and flushes changes instantly.
	T			save(T entity) throws DaoException;							//	Saves a given entity.

	//---------------------------------------------------------------------
}
