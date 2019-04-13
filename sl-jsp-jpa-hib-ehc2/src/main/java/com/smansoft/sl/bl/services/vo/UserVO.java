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
	
}
