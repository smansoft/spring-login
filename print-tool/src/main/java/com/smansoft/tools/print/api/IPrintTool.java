/**
 * 
 */
package com.smansoft.tools.print.api;

import com.smansoft.tools.print.api.types.PrintLevel;
import com.smansoft.tools.print.api.types.PrintSfx;

/**
 * @author SMan
 *
 */
public interface IPrintTool extends IPrintToolBase {

	/***********************************************************************************************/	
	/**
	 * 
	 * @param printLevel
	 * @param object
	 */
	void log(PrintLevel printLevel, Object object);

	/**
	 * 
	 * @param printLevel
	 * @param message
	 * @param ex
	 */
	void log(PrintLevel printLevel, Object message, Throwable ex);

	/**
	 * 
	 * @param printLevel
	 * @param printable
	 */
	void log(PrintLevel printLevel, IPrintable printable);
	
	/**
	 * 
	 * @param printLevel
	 * @param printSfx
	 */
	void log(PrintLevel printLevel, PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param message
	 */
	void error(Object message);

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	void error(Object message, Throwable ex);

	/**
	 * 
	 * @param printable
	 */
	void error(IPrintable printable);

	/**
	 * 
	 * @param printSfx
	 */
	void error(PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param message
	 */
	void warn(Object message);

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	void warn(Object message, Throwable ex);

	/**
	 * 
	 * @param printable
	 */
	void warn(IPrintable printable);

	/**
	 * 
	 * @param printSfx
	 */
	void warn(PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param message
	 */
	void info(Object message);

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	void info(Object message, Throwable ex);

	/**
	 * 
	 * @param printable
	 */
	void info(IPrintable printable);

	/**
	 * 
	 * @param printSfx
	 */
	void info(PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param message
	 */
	void debug(Object message);

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	void debug(Object message, Throwable ex);

	/**
	 * 
	 * @param printable
	 */
	void debug(IPrintable printable);

	/**
	 * 
	 * @param printSfx
	 */
	void debug(PrintSfx printSfx);
	/***********************************************************************************************/	
	/**
	 * 
	 * @param message
	 */
	void trace(Object message);

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	void trace(Object message, Throwable ex);

	/**
	 * 
	 * @param printable
	 */
	void trace(IPrintable printable);

	/**
	 * 
	 * @param printSfx
	 */
	void trace(PrintSfx printSfx);
	/***********************************************************************************************/
}
