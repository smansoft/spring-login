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
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	List<UserRoleEntity> findByUser(UserEntity userEntity);
	
	/**
	 * 
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	List<UserRoleEntity> findAll();

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Modifying
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	UserRoleEntity	saveAndFlush(UserRoleEntity entity);

	/**
	 * 
	 * @param email
	 * @return
	 */
	@Modifying
	void deleteByUser(UserEntity user);
}
