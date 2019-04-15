/**
 * 
 */
package com.smansoft.tools.print.vo;

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
public abstract class BaseVO implements IPrintable  {

	/**
	 * 
	 */
	public BaseVO() {
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
	public	void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintToolStr printToolStr) {
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
