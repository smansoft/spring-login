/**
 * 
 */
package com.smansoft.sl.persistence.dao;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Repository(IUserRepositoryPaged.DEF_BEAN_NAME)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
public interface IUserRepositoryPaged extends PagingAndSortingRepository<UserEntity, Long> {

	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "userRepositoryPagedBean";

	/**
	 * 
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = true)
	Page<UserEntity> findAll(Pageable pageable);
}
