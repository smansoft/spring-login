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
	@QueryHints(value = { @QueryHint(name = "javax.persistence.cache.storeMode", value = "USE"),
			@QueryHint(name = "javax.persistence.cache.retrieveMode", value = "USE"),
			@QueryHint(name = "eclipselink.query-results-cache.type", value = "FULL"),
			@QueryHint(name = "eclipselink.query-results-cache", value = "true"),
			@QueryHint(name = "eclipselink.query-results-cache.size", value = "64000"),
			@QueryHint(name = "eclipselink.query-results-cache.expiry", value = "3600000"),
			@QueryHint(name = "eclipselink.cache-usage", value = "CheckCacheThenDatabase"),
			@QueryHint(name = "eclipselink.query-type", value = "ReadAll")
		},forCounting = true)
	Page<UserEntity> findAll(Pageable pageable);
}
