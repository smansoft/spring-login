/**
 * 
 */
package com.smansoft.sl.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.smansoft.sl.tools.common.IpTools;

/**
 * @author SMan
 *
 */
@Entity
@Cacheable(true)
@Cache(region = "com.smansoft.sl.persistence.entities.UserEntity", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = UserEntity.DEF_TABLE_NAME, indexes = {
		@Index(name = UserEntity.DEF_TABLE_NAME + "_id", columnList = "id"),
		@Index(name = UserEntity.DEF_TABLE_NAME + "_user_login", columnList = "user_login"),
		@Index(name = UserEntity.DEF_TABLE_NAME + "_email", columnList = "email") }, uniqueConstraints = {
				@UniqueConstraint(columnNames = { "id", "user_login", "email" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserEntity extends BaseEntity {

	public static final String DEF_TABLE_NAME = "users";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3558350097666834896L;

	@Column(name = "user_login", length = 255, nullable = false, unique = true, insertable = true, updatable = false)
	@NotNull
	@NotEmpty
	private String userLogin;

	@Column(name = "email", length = 255, nullable = false, unique = true, insertable = true, updatable = false)
	@NotNull
	@NotEmpty
	@Pattern(regexp = IpTools.DEF_EMAIL_PATTERN)
	private String email;

	@Column(name = "user_name", length = 255, nullable = false, unique = false, insertable = true, updatable = true)
	@NotNull
	@NotEmpty
	private String userName;

	@Column(name = "password", length = 255, nullable = false, unique = false, insertable = true, updatable = true)
	@NotNull
	@NotEmpty
	private String password;

	@Column(name = "enabled", nullable = false, unique = false, insertable = true, updatable = true)
	@NotNull
	private Boolean enabled;


	@Cache(region = "com.smansoft.sl.persistence.entities.AuthorityEntity", usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
	private List<AuthorityEntity> authorities = new ArrayList<>();

	/**
	 * 
	 * @return
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * 
	 * @param userLogin
	 * @return
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * 
	 * @param enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 
	 * @return
	 */
	public List<AuthorityEntity> getAuthorities() {
		return authorities;
	}

	/**
	 * 
	 * @param authorities
	 */
	public void setAuthorities(List<AuthorityEntity> authorities) {
		this.authorities = authorities;
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
