/**
 * 
 */
package com.smansoft.sl.persistence.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

///import org.hibernate.annotations.Cache;
///import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.smansoft.sl.persistence.types.UserRoleType;

/**
 * @author SMan
 *
 */
@Entity
@Cacheable(true)
@Cache(
		type=CacheType.FULL, // Cache everything until the JVM decides memory is low.
		refreshOnlyIfNewer=true,
		isolation=CacheIsolationType.SHARED,
		size=64000,  // Use 64,000 as the initial cache size.
		expiry=3600000,  // 60 minutes
		coordinationType=CacheCoordinationType.SEND_OBJECT_CHANGES
		)
@Table(name = UserRoleEntity.DEF_TABLE_NAME, indexes = {
		@Index(name = UserRoleEntity.DEF_TABLE_NAME + "_id", columnList = "id"),
		@Index(name = UserRoleEntity.DEF_TABLE_NAME + "_user_id", columnList = "user_id") }, uniqueConstraints = {
				@UniqueConstraint(columnNames = { "id" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserRoleEntity extends BaseEntity {

	public static final String DEF_TABLE_NAME = "user_roles";

	/**
	 * 
	 */
	private static final long serialVersionUID = 198372122574329507L;

	@Column(name = "user_role_type", nullable = false, unique = false, updatable = false)
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private UserRoleType userRoleType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	/**
	 * 
	 * @return
	 */
	public UserRoleType getUserRoleType() {
		return userRoleType;
	}

	/**
	 * 
	 * @param userRoleType
	 */
	public void setUserRoleType(UserRoleType userRoleType) {
		this.userRoleType = userRoleType;
	}

	/**
	 * 
	 * @return
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 *
	 * @param user
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean usePrintService() {
		return true;
	}
}
