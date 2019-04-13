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

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.annotations.CacheCoordinationType;

import com.smansoft.sl.persistence.types.SexType;
import com.smansoft.sl.tools.common.IpTools;


/**
 * @author SMan
 *
 */
@Entity
@Cacheable(true)
@Cache( 
		type=CacheType.FULL, // Cache everything until the JVM decides memory is low.
		isolation=CacheIsolationType.SHARED,
		refreshOnlyIfNewer=true,
		size=64000,  // Use 64,000 as the initial cache size.
		expiry=3600000,  // 60 minutes
		coordinationType=CacheCoordinationType.SEND_OBJECT_CHANGES
		)
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

	@Column(name = "user_address", length = 255, nullable = false, unique = false, insertable = true, updatable = true)
	private String userAddress;

	@Column(name = "sex", length = 255, nullable = false, unique = false, insertable = true, updatable = true)
	private SexType sex;

	@Column(name = "password", length = 255, nullable = false, unique = false, insertable = true, updatable = true)
	@NotNull
	@NotEmpty
	private String password;

	@Column(name = "enabled", nullable = false, unique = false, insertable = true, updatable = true)
	@NotNull
	private Boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
	private List<UserRoleEntity> userRoles = new ArrayList<>();

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
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * 
	 * @return
	 */
	public SexType getSex() {
		return sex;
	}

	/**
	 * 
	 * @param sex
	 */
	public void setSex(SexType sex) {
		this.sex = sex;
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
	public List<UserRoleEntity> getUserRoles() {
		return userRoles;
	}

	/**
	 * 
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRoleEntity> userRoles) {
		this.userRoles = userRoles;
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
