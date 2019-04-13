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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Repository(IUserRepository.DEF_BEAN_NAME)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "userRepositoryBean";

	/**
	 * 
	 * @param login
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
	UserEntity findByUserLogin(String userLogin);

	/**
	 * 
	 * @param email
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
	UserEntity findByEmail(String email);

	/**
	 * 
	 * @param userName
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
	List<UserEntity> findByUserName(String userName);

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
		},forCounting = false)
	List<UserEntity> findAll();

	/**
	 * 
	 */
	@QueryHints(value = { @QueryHint(name = "javax.persistence.cache.storeMode", value = "USE"), 
			@QueryHint(name = "javax.persistence.cache.retrieveMode", value = "USE"),
			@QueryHint(name = "eclipselink.query-results-cache", value = "true"),
			@QueryHint(name = "eclipselink.query-results-cache.size", value = "64000"),
			@QueryHint(name = "eclipselink.query-results-cache.expiry", value = "3600000"),
			@QueryHint(name = "eclipselink.cache-usage", value = "CheckCacheThenDatabase"),
			@QueryHint(name = "eclipselink.query-type", value = "Auto")
	},forCounting = false)
	UserEntity getOne(Long id);

	/**
	 * 
	 * @param login
	 * @return
	 */
	@Modifying
	void deleteByUserLogin(String userLogin);

	/**
	 * 
	 * @param email
	 * @return
	 */
	@Modifying
	void deleteByEmail(String email);

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
	UserEntity	saveAndFlush(UserEntity entity);

}
