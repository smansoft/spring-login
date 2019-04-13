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

import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;

/**
 * @author SMan
 *
 */
@Repository(IAuthorityRepository.DEF_BEAN_NAME)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
public interface IAuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
	
	/**
	 * 
	 */
	final static String DEF_BEAN_NAME = "authorityRepositoryBean";
	
	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	List<AuthorityEntity> findByUser(UserEntity userEntity);
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Modifying
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	AuthorityEntity	saveAndFlush(AuthorityEntity entity);
}
