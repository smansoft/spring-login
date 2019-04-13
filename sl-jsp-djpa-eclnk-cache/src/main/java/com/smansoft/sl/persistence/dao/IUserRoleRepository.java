/**
 * 
 */
package com.smansoft.sl.persistence.dao;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Repository(IUserRoleRepository.DEF_BEAN_NAME)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
public interface IUserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
	
	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "userRoleBean";

	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	@QueryHints(value = { @QueryHint(name = "javax.persistence.cache.storeMode", value = "USE"), 
			@QueryHint(name = "javax.persistence.cache.retrieveMode", value = "USE"),
			@QueryHint(name = "eclipselink.query-results-cache", value = "true"),
			@QueryHint(name = "eclipselink.query-results-cache.size", value = "64000"),
			@QueryHint(name = "eclipselink.query-results-cache.expiry", value = "3600000"),
			@QueryHint(name = "eclipselink.cache-usage", value = "CheckCacheThenDatabase"),
			@QueryHint(name = "eclipselink.query-type", value = "Auto")
	},forCounting = false)
	List<UserRoleEntity> findByUser(UserEntity userEntity);
	
	/**
	 * 
	 */
	@QueryHints(value = { @QueryHint(name = "javax.persistence.cache.storeMode", value = "REFRESH"),
			@QueryHint(name = "javax.persistence.cache.retrieveMode", value = "USE"),
			@QueryHint(name = "eclipselink.query-results-cache", value = "true"),
			@QueryHint(name = "eclipselink.query-results-cache.size", value = "64000"),
			@QueryHint(name = "eclipselink.query-results-cache.expiry", value = "3600000"),
			@QueryHint(name = "eclipselink.cache-usage", value = "CheckCacheThenDatabase"),
			@QueryHint(name = "eclipselink.query-type", value = "Auto")
	},forCounting = false)
	List<UserRoleEntity> findAll();

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Modifying
	@QueryHints(value = { @QueryHint(name = "javax.persistence.cache.storeMode", value = "USE"), 
			@QueryHint(name = "javax.persistence.cache.retrieveMode", value = "USE"),
			@QueryHint(name = "eclipselink.query-results-cache", value = "true"),
			@QueryHint(name = "eclipselink.query-results-cache.size", value = "64000"),
			@QueryHint(name = "eclipselink.query-results-cache.expiry", value = "3600000"),
			@QueryHint(name = "eclipselink.cache-usage", value = "CheckCacheThenDatabase"),
			@QueryHint(name = "eclipselink.query-type", value = "Auto")
	},forCounting = false)
	UserRoleEntity	saveAndFlush(UserRoleEntity entity);

	/**
	 * 
	 * @param email
	 * @return
	 */
	@Modifying
	void deleteByUser(UserEntity user);
}
