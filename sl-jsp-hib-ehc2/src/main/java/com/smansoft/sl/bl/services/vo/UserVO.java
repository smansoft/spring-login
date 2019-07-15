/**
 * 
 */
package com.smansoft.sl.bl.services.vo;

import java.util.List;

/**
 * @author SMan
 *
 */
public class UserVO extends BaseUserVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7470120159315204678L;

	/**
	 * 
	 */
	private List<String> authorities;

	/**
	 * 
	 */
	public UserVO() {
	}

	/**
	 * 
	 * @param authorities
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAuthorities() {
		return this.authorities;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		if(!super.equals(object)) {
			return false;
		}
		UserVO userVO = (UserVO)object;
		if(authorities.size() != userVO.getAuthorities().size()) {
			return false;
		}
		List<String> authoritiesVO = userVO.getAuthorities();		
		int n = authorities.size();
		int nVO = authoritiesVO.size();
		if(n != nVO) {
			return false;
		}
		boolean isEquals = true;
		for(int i = 0; isEquals && i < n; i++) {
			String authority = authorities.get(i);
			if(authority == null) {
				continue;
			}
			isEquals = false;
			for(int j = 0; j < nVO; j++) {
				String authorityVO = authoritiesVO.get(j);
				if(authority == null && authorityVO == null || authority.equals(authorityVO)) {
					isEquals = true;
					break;
				}
			}
		}
		return isEquals;
	}
	
	/**
	 * 
	 */
	@Override	
	public UserVO clone() throws CloneNotSupportedException {  
		return (UserVO)super.clone();  
	}  	
	
}
