/**
 * 
 */
package com.smansoft.sl.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.smansoft.sl.persistence.types.AuthorityType;

/**
 * @author SMan
 *
 */
@Entity
@Cache(region = "com.smansoft.sl.persistence.entities.AuthorityEntity", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = AuthorityEntity.DEF_TABLE_NAME, indexes = {
		@Index(name = AuthorityEntity.DEF_TABLE_NAME + "_id", columnList = "id"),
		@Index(name = AuthorityEntity.DEF_TABLE_NAME + "_user_id", columnList = "user_id") }, uniqueConstraints = {
				@UniqueConstraint(columnNames = { "id" }) })
@NamedQueries({
	@NamedQuery(name = AuthorityEntity.DEF_FIND_AUTHORITIES_BY_USER,  query = "SELECT g FROM AuthorityEntity g WHERE g.user = :user")
	})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AuthorityEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 198372122574329507L;

	public static final String DEF_TABLE_NAME = "authorities";

	public static final String DEF_FIND_AUTHORITIES_BY_USER = "findAuthorities_ByUser";

	@Column(name = "authority_type", nullable = false, unique = false, updatable = false)
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private AuthorityType authorityType;

	@ManyToOne
	@Cache(region = "com.smansoft.sl.persistence.entities.UserEntity", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	/**
	 * 
	 * @return
	 */
	public AuthorityType getAuthorityType() {
		return authorityType;
	}

	/**
	 * 
	 * @param authority
	 */
	public void setAuthorityType(AuthorityType authorityType) {
		this.authorityType = authorityType;
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
