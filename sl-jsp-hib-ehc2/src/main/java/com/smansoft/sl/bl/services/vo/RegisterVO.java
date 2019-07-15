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
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		if(!super.equals(object)) {
			return false;
		}		
		RegisterVO registerVO = (RegisterVO) object;
		if((this.getPasswordConfirm() != null || registerVO.getPasswordConfirm() != null) &&   
				(!this.getPasswordConfirm().equalsIgnoreCase(registerVO.getPasswordConfirm()))) {
			return false;
		}
		if(this.getIsAdmin() != registerVO.getIsAdmin()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	@Override	
	public RegisterVO clone() throws CloneNotSupportedException {  
		return (RegisterVO)super.clone();  
	}  	
	
}
