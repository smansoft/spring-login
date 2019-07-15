/**
 * 
 */
package com.smansoft.sl.bl.services.vo;

import java.io.Serializable;

import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.IPrintable;
import com.smansoft.tools.print.api.types.PrintCtx;
import com.smansoft.tools.print.api.types.PrintLevel;
import com.smansoft.tools.print.api.types.PrintSfx;


/**
 * @author SMan
 *
 */
public class BaseUserVO implements Serializable, Cloneable, IPrintable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3577281227717463893L;

	private String login;

	private String email;

	private String name;

	private String password;

	private Boolean enabled = true;

	/**
	 * 
	 */
	public BaseUserVO() {
	}

	/**
	 * 
	 * @return
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * 
	 * @param login
	 * @return
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
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
	public String getPassword() {
		return this.password;
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
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return getLogin() + "," + getEmail();
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, IPrintTool printTool) {
		printTool.error(PrintSfx.SFX_IN);
		printTool.error("Function isn't implemented");
		printTool.error(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, PrintCtx printCtx, IPrintTool printTool) {
		printTool.error(PrintSfx.SFX_IN);
		printTool.error("Function isn't implemented");
		printTool.error(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, IPrintToolStr printToolStr) {
		printToolStr.error(startMessage, PrintSfx.SFX_IN);
		printToolStr.error(startMessage, "Function isn't implemented");
		printToolStr.error(startMessage, PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintToolStr printToolStr) {
		printToolStr.error(startMessage, PrintSfx.SFX_IN);
		printToolStr.error(startMessage, "Function isn't implemented");
		printToolStr.error(startMessage, PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@Override
	public boolean usePrintService() {
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}
		if(object == null) {
			return false;
		}
		if(getClass() != object.getClass()) {
			return false;			
		}
		BaseUserVO registerVO = (BaseUserVO) object;
		if((this.getLogin() != null || registerVO.getLogin() != null) &&   
				(!this.getLogin().equals(registerVO.getLogin()))) {
			return false;
		}
		if((this.getEmail() != null || registerVO.getEmail() != null) &&   
				(!this.getEmail().equalsIgnoreCase(registerVO.getEmail()))) {
			return false;
		}
		if((this.getName() != null || registerVO.getName() != null) &&   
				(!this.getName().equalsIgnoreCase(registerVO.getName()))) {
			return false;
		}
		if((this.getPassword() != null || registerVO.getPassword() != null) &&   
				(!this.getPassword().equalsIgnoreCase(registerVO.getPassword()))) {
			return false;
		}
		if(this.getEnabled() != registerVO.getEnabled()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	@Override	
	public BaseUserVO clone() throws CloneNotSupportedException {  
		return (BaseUserVO)super.clone();  
	}  	
}
