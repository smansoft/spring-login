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
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	UserEntity findByUserLogin(String userLogin);

	/**
	 * 
	 * @param email
	 * @return
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	UserEntity findByEmail(String email);

	/**
	 * 
	 * @param userName
	 * @return
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	List<UserEntity> findByUserName(String userName);

	/**
	 * 
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	List<UserEntity> findAll();

	/**
	 * 
	 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
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
	@QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"), forCounting = false)
	UserEntity	saveAndFlush(UserEntity entity);

}
