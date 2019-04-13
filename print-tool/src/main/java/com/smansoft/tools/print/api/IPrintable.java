/**
 * 
 */
package com.smansoft.tools.print.api;

import com.smansoft.tools.print.api.types.PrintCtx;
import com.smansoft.tools.print.api.types.PrintLevel;

/**
 * @author SMan
 *
 */
public interface IPrintable {

	/**
	 * 
	 * @param printLevel
	 * @param printTool
	 */
	void log(PrintLevel printLevel, IPrintTool printTool);
	
	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param printTool
	 */
	void log(PrintLevel printLevel, PrintCtx printCtx, IPrintTool printTool);

	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param printToolStr
	 */
	void log(PrintLevel printLevel, String startMessage, IPrintToolStr printToolStr);

	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param startMessage
	 * @param printToolStr
	 */
	void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintToolStr printToolStr);
	
	/**
	 * 
	 * @return
	 */
	boolean usePrintService();
	
}
