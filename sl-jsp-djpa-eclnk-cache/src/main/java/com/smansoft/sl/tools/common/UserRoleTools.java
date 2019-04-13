/**
 * 
 */
package com.smansoft.sl.tools.common;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.smansoft.sl.exceptions.InitException;
import com.smansoft.sl.persistence.entities.UserEntity;
import com.smansoft.sl.persistence.entities.UserRoleEntity;
import com.smansoft.sl.persistence.types.UserRoleType;

/**
 * @author SMan
 *
 */
public abstract class UserRoleTools {

	/**
	 * 
	 * @param userEntity
	 * @return
	 */
	public static boolean getUserAdmin(UserEntity userEntity) {
		boolean isAdmin = false;
		List<UserRoleEntity> userRoleEntities = userEntity.getUserRoles();
		for(UserRoleEntity userRoleEntity : userRoleEntities) {
			if(UserRoleType.ROLE_ADMIN == userRoleEntity.getUserRoleType()) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentUserName() {
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated()) {
			currentUserName = authentication.getName();
		}
		return currentUserName;
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 * @throws InitException
	 */
	@SuppressWarnings("unchecked")
	public static boolean isCurrentRole(UserRoleType userRole) throws InitException {
		boolean isCurrentRole = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated()) {
			Collection<SimpleGrantedAuthority> grantedAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(SimpleGrantedAuthority grantedAuthority : grantedAuthorities) {
				String authority = grantedAuthority.getAuthority();
				UserRoleType userRoleGranted = UserRoleType.valueOf(authority);
				if(userRoleGranted == userRole) {
					isCurrentRole = true;
					break;
				}
			}
		}
		return isCurrentRole;
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 * @throws InitException
	 */
	public static boolean checkUserRole(UserRoleType checkedUserRole, List<String> userRoles) throws InitException {
		boolean isRole = false;
		for(String userRoleStr : userRoles) {
			UserRoleType userRole = UserRoleType.valueOf(userRoleStr);
			if(userRole.equals(checkedUserRole)) {
				isRole = true;
				break;
			}
		}
		return isRole;
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 * @throws InitException
	 */
	@SuppressWarnings("unchecked")
	public static boolean isCurrentRoles(UserRoleType [] userRoles) throws InitException {
		int numberFound = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated()) {
			Collection<SimpleGrantedAuthority> grantedAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(SimpleGrantedAuthority grantedAuthority : grantedAuthorities) {
				String authority = grantedAuthority.getAuthority();
				UserRoleType userRoleGranted = UserRoleType.valueOf(authority);
				for(UserRoleType userRole : userRoles) {
					if(userRoleGranted == userRole) {
						numberFound++;
						break;
					}
				}
			}
		}
		return (numberFound == userRoles.length);
	}

	/**
	 * 
	 * @return
	 */
	public static UserRoleType[] valuesNoRootAdmin() {
		UserRoleType[] values = UserRoleType.values();
		int n = values.length - 1;
		int j = 0;
		UserRoleType[] valuesNoRootAdmin = new UserRoleType[n];
		for(int i = 0; i < values.length; i++) {
			UserRoleType value = values[i];
			if(UserRoleType.ROLE_ROOT_ADMIN == value) {
				continue;
			}
			valuesNoRootAdmin[j++] = value;
		}
		return valuesNoRootAdmin;
	}

}
