/**
 * 
 */
package com.smansoft.sl.bl.services.vo;

/**
 * @author SMan
 *
 */
public class RegisterVO extends BaseUserVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5676511569705584634L;

	private String passwordConfirm;

	private boolean isAdmin = false;

	/**
	 * 
	 */
	public RegisterVO() {
	}

	/**
	 * 
	 * @param password
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	/**
	 * 
	 * @return
	 */
	public String getPasswordConfirm() {
		return this.passwordConfirm;
	}

	/**
	 * 
	 * @param isAdmin
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

}
