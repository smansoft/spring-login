/**
 * 
 */
package com.smansoft.sl.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

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
@MappedSuperclass
public abstract class BaseEntity implements Serializable, IPrintable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -513453694307823163L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	protected Long id;

	/**
	 * 
	 * @return
	 */
	@Transient
	public String getNamedQueryFindByIds() {
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @param printLevel
	 * @param printTool
	 */
	public void log(PrintLevel printLevel, IPrintTool printTool) {
		printTool.error(PrintSfx.SFX_IN);
		printTool.error("Function isn't implemented");
		printTool.error(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param printTool
	 */
	public void log(PrintLevel printLevel, PrintCtx printCtx, IPrintTool printTool) {
		printTool.error(PrintSfx.SFX_IN);
		printTool.error("Function isn't implemented");
		printTool.error(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param printTool
	 */
	public void log(PrintLevel printLevel, String startMessage, IPrintToolStr printToolStr) {
		printToolStr.error(startMessage, PrintSfx.SFX_IN);
		printToolStr.error(startMessage, "Function isn't implemented");
		printToolStr.error(startMessage, PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param startMessage
	 * @param printTool
	 */
	public void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintToolStr printToolStr) {
		printToolStr.error(startMessage, PrintSfx.SFX_IN);
		printToolStr.error(startMessage, "Function isn't implemented");
		printToolStr.error(startMessage, PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 * @return
	 */
	public abstract boolean usePrintService();
}
